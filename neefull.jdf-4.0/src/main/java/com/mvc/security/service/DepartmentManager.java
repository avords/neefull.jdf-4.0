package com.mvc.security.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.mvc.framework.model.BaseTree;
import com.mvc.framework.model.Tree;
import com.mvc.framework.service.BaseService;
import com.mvc.security.model.Department;
import com.mvc.security.model.Menu;

@Service
public class DepartmentManager extends BaseService<Department, Long> {

	public void save(Department entity) {
		boolean updateChildren = false;
		if (null != entity.getObjectId()) {
			updateChildren = true;
		}
		if(entity.getStatus()==null){
			entity.setStatus(1);
		}
		//default
		String parentLayer = "/" +BaseTree.ROOT;
		if(StringUtils.isBlank(entity.getCode())){
			entity.setCode(null);
		}
		if(entity.getParentId()!=null&&entity.getParentId()!=BaseTree.ROOT){
			Department parent = getByObjectId(entity.getParentId());
			if(parent!=null){
				entity.setLevelDeep(parent.getLevelDeep() + Tree.DEFAULT_DEEP_LEVEL_STEP);
				parentLayer = parent.getLayer();
				if(parent.isParent()==null||!parent.isParent()){
					parent.setParent(true);
					super.save(parent);
				}
			}else{
				entity.setLevelDeep(Tree.DEFAULT_DEEP_LEVEL_STEP);
			}
		} else {
			entity.setLevelDeep(Tree.DEFAULT_DEEP_LEVEL);
		}
		//Oracle not null
		entity.setLayer(" ");
		fillFull(entity);
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
		}
		if (updateChildren) {
			updatePathOfDirectChildren(entity.getObjectId(), entity);
		}
	}

	private void fillFull(Department entity) {
		if (null != entity) {
			if (null != entity.getParentId() && !Department.ROOT.getObjectId().equals(entity.getParentId())) {
				Department parentMenu = getParentDepartment(entity.getParentId());
				if (null != parentMenu) {
					entity.setFullName(parentMenu.getFullName() + Department.PATH_SEPARATOR + entity.getName());
					return;
				}
			}
			entity.setFullName(Department.PATH_SEPARATOR + entity.getName());
		}
	}
	
	public int getChildrenCount(long parentId){
		String sql = "select COUNT(*) from " + getActualArgumentType().getName() + " A where parentId = " + parentId ;
		Query query = getSession().createQuery(sql);
		Object object = query.list().get(0);
		int count = object==null?0:((Number)object).intValue(); 
		return count;
	}
	
	public int getChildrenMaxOrderId(long parentId){
		String sql = "select max(orderId) from " + getActualArgumentType().getName() + " A where parentId = " + parentId ;
		Query query = getSession().createQuery(sql);
		Object object = query.list().get(0);
		int count = object==null?0:((Number)object).intValue(); 
		return count;
	}

	public Department getParentDepartment(Long departmentId) {
		Department department = getDepartmentByDepartmentId(departmentId);
		if(department!=null){
			fillFull(department);
		}
		return department;
	}

	public Department getTopDetartmentByDepartmentId(Long departmentId) {
		Department department = getDepartmentByDepartmentId(departmentId);
		if (null == department) {
			return null;
		} else if (Department.ROOT.getObjectId().equals(department.getParentId())) {
			return department;
		} else {
			return getTopDetartmentByDepartmentId(department.getParentId());
		}
	}

	protected void updatePathOfDirectChildren(Long parentId, Department parent) {
		List<Department> needUpdateMenu = getDirectChildren(parentId);
		Session session = getSession();
		for (Department department : needUpdateMenu) {
			department.setFullName(parent.getFullName() + Menu.PATH_SEPARATOR + department.getName());
			department.setLayer(parent.getLayer() + Menu.PATH_SEPARATOR + department.getObjectId());
			updatePathOfDirectChildren(department.getObjectId(), department);
			String hql = "UPDATE " + Department.class.getName() + " SET fullName = ?,LAYER = ? WHERE objectId = ?";
			Query query = session.createQuery(hql).setParameter(0, department.getFullName())
					.setParameter(2, department.getObjectId()).setParameter(1, department.getLayer());
			query.executeUpdate();
		}
	}

	public List<Department> getDirectChildren(Long parentId) {
		if (null != parentId) {
			return searchBySql("select A from " + Department.class.getName() + " A where parentId=?",
					new Object[] { parentId });
		}
		return new ArrayList<Department>(0);
	}
	
	public List<Department> getRecursionChildrenByLayer(String layer){
		if(StringUtils.isNotBlank(layer)){
			String sql="select A from "+ Department.class.getName() + " A where A.layer like ? order by name";
			return searchBySql(sql, layer + "%");
		}else {
			return getAll();
		}
	}
	
	public Department getDepartmentByLayer(String layer){
		if(StringUtils.isNotBlank(layer)){
			String sql="select A from "+ Department.class.getName() + " A where A.layer = ? ";
			return searchObjectBySql(sql, layer);
		}
		return null;
	}


	public Department getDepartmentByDepartmentId(Long departmentId) {
		if (null != departmentId) {
			if (Department.ROOT.getObjectId().equals(departmentId)) {
				return Department.ROOT;
			}
			return getByObjectId(departmentId);
		}
		return null;
	}

	public List<Department> getAll() {
		List<Department> result = super.getAll();
		result.add(Department.ROOT);
		return result;
	}

	public Department getDepartment(long departmentId, List<Department> departments) {
		for (Department department : departments) {
			if (departmentId == department.getObjectId()) {
				return department;
			}
		}
		return null;
	}
	
	public Department getDepartmentByName(String name){
		return searchByWhere("name = ?", name);
	}
	
	public Department checkIfNameExists(Department user) {
		String sql = "select A from " + Department.class.getName() + " A where  A.name = ?";
		Object[] parameters = new Object[] { user.getName() };
		Long objectId = user.getObjectId();
		if (objectId != null && objectId != 0) {
			sql += " and A.objectId != ?";
			parameters = new Object[] { user.getName(), objectId };
		}
		List<Department> userList = searchBySql(sql, parameters);
		if (userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}
}
