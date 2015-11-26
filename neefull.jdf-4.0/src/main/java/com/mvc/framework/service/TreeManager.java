package com.mvc.framework.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.mvc.framework.model.BaseTree;
import com.mvc.framework.model.Tree;
import com.mvc.security.model.Menu;
@Service
public class TreeManager extends BaseService<Tree, Long>{
	
	public void save(Tree entity) {
		//default
		String parentLayer = "/" +BaseTree.ROOT;
		if(StringUtils.isBlank(entity.getCode())){
			entity.setCode(null);
		}
		if(entity.getParentId()!=null&&entity.getParentId()!=BaseTree.ROOT){
			Tree parent = getByObjectId(entity.getParentId());
			if(parent!=null && !parent.isParent()){
				parent.setParent(true);
				super.save(parent);
				entity.setLevelDeep(parent.getLevelDeep() + Tree.DEFAULT_DEEP_LEVEL_STEP);
				parentLayer = parent.getLayer();
			}else{
				entity.setLevelDeep(Tree.DEFAULT_DEEP_LEVEL_STEP);
			}
		} else {
			entity.setLevelDeep(Tree.DEFAULT_DEEP_LEVEL);
		}
		entity.setLayer("");
		if(entity.getObjectId()==null){
			entity.setParent(false);
		}else{
			int childCount = getChildrenCount(entity.getObjectId());
			if(childCount==0){
				entity.setParent(false);
			}else{
				entity.setParent(true);
			}
		}
		if(entity.getOrderId()==null){
			if(entity.getParentId()!=null){
				entity.setOrderId(getChildrenMaxOrderId(entity.getParentId()) + 1);
			}else{
				entity.setOrderId(Tree.DEFAULT_ORDER_ID);
			}
		}
		super.save(entity);
		String newCodeNo = parentLayer + "/" + entity.getObjectId();
		if(!entity.getLayer().equals(newCodeNo)){
			entity.setLayer(newCodeNo);
			super.save(entity);
			updateLayerDirectChildren(entity);
		}
	}
	
	protected void updateLayerDirectChildren(Tree parent) {
		List<Tree> needUpdateMenu = getChildren(parent.getObjectId());
		Session session = getSession();
		for (Tree tree : needUpdateMenu) {
			String layer = parent.getLayer() + Menu.PATH_SEPARATOR + tree.getObjectId();
			updateLayerDirectChildren(tree);
			String hql = "UPDATE " + getActualArgumentType().getName() + " SET layer = ? WHERE objectId = ?";
			Query query = session.createQuery(hql).setParameter(0, layer).setParameter(1, tree.getObjectId());
			query.executeUpdate();
		}
	}
	
	public int getChildrenCount(long parentId){
		String sql = "select COUNT(*) from " + getActualArgumentType().getName() + " A where parentId = " + parentId ;
		Query query = getSession().createQuery(sql);
		Object object = query.list().get(0);
		int count = object==null?0:((Number)object).intValue(); 
		return count;
	}
	/**
	 * Query all child nodes
	 * @param code
	 * @return
	 */
	public List<Tree> getRecursionChildrenByLayer(String layer){
		if(StringUtils.isNotBlank(layer)){
			String sql="select A from "+ getActualArgumentType().getName() + " A where A.layer like ? order by levelDeep,orderId";
			return searchBySql(sql, layer + "%");
		}else {
			return getAll();
		}
	}
	/**
	 * Query the direct child node
	 * @param layer
	 * @return
	 */
	public List<Tree> getChildrenByLayer(String layer){
		String sql="select A from "+ getActualArgumentType().getName() + " A," + getActualArgumentType().getName() + " B where A.parentId = B.objectId " 
					+ " and B.layer = ? order by A.orderId";
		return searchBySql(sql,new Object[]{layer});
	}
	
	public List<Tree> getAll(){
		return searchBySql("select A from " + getActualArgumentType().getName() + " A order by levelDeep,orderId");
	}
	
	public Tree getTreeByCode(String code){
		if(null!=code){
			String sql="select A from " + getActualArgumentType().getName() + " A where A.code = ?";
			List<Tree> list=searchBySql( sql,code);
			if(list.size()>0){
				return list.get(0);
			}
		}
		return null;
	}
	
	public List<Tree> getChildren(long parentId) {
		return searchBySql("select A from " + getActualArgumentType().getName()
				+ " A where A.parentId =" + parentId + " order by orderId");
	}
	
	public int getChildrenMaxOrderId(long parentId){
		String sql = "select max(orderId) from " + Tree.class.getName() + " A where parentId = " + parentId ;
		Query query = getSession().createQuery(sql);
		Object object = query.list().get(0);
		int count = object==null?0:((Number)object).intValue(); 
		return count;
	}
}
