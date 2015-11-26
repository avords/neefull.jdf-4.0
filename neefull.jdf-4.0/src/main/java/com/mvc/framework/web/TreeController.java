package com.mvc.framework.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mvc.framework.model.BaseTree;
import com.mvc.framework.model.Tree;
import com.mvc.framework.service.PageManager;
import com.mvc.framework.service.TreeManager;
import com.mvc.framework.util.PageSearch;

@Controller
@RequestMapping("tree")
public class TreeController extends PageController<Tree> {
	@Autowired
	private TreeManager treeManager;
	
	@RequestMapping("")
	public String index(HttpServletRequest request) {
		request.setAttribute("root", Tree.ROOT);
		return getFileBasePath() + "tree";
	}
	
	@RequestMapping("{code}")
	public String index(HttpServletRequest request,@PathVariable String code) {
		request.setAttribute("root", treeManager.getTreeByCode(code));
		request.setAttribute("code", code);
		return getFileBasePath() + "tree";
	}
	
	@RequestMapping("getChildren")
	public @ResponseBody String getChildren(Long objectId,String code) throws Exception {
		List<Tree> trees = new ArrayList<Tree>();
		if(objectId==null){
			if(StringUtils.isNotBlank(code)){
				trees.add(treeManager.getTreeByCode(code));
			}else{
				trees.add(Tree.ROOT);
			}
		}
		if(objectId!=null){
			trees = treeManager.getChildren(objectId);
		}
		return parseJsonTree(trees);
	}

	private String parseJsonTree(List<Tree> trees) {
		StringBuilder children = new StringBuilder(140 * trees.size());
		children.append("[");
		int i=0;
		Gson gson = new GsonBuilder().create();
		for ( Tree tree : trees) {
			if(i>0){
				children.append(",");
			}
			children.append(gson.toJson(tree));
			i++;
		}
		children.append("]");
		return children.toString();
	}
	@RequestMapping(value="/getChildren/{code}")
	public String getChildren(HttpServletRequest request, ModelMap modelMap,@PathVariable String code){
		List<Tree> Trees =treeManager.getRecursionChildrenByLayer(code);
		modelMap.addAttribute("Trees",Trees);
		return "jsonView";
	}

	@RequestMapping("treeDelete/{treeId}")
	public String treeDelete(ModelMap modelMap, @PathVariable Long treeId) {
		Tree tree = treeManager.getByObjectId(treeId);
		boolean result = false;
		if (tree != null) {
			treeManager.delete(tree);
			result = true;
		}
		modelMap.addAttribute("status", result);
		return "jsonView";
	}
	
	@RequestMapping(value="/treeSave")
	public String treeSave(ModelMap modelMap, Tree entity) throws Exception {
		if (entity.getObjectId() != null) {
			Tree old = treeManager.getByObjectId(entity.getObjectId());
			entity.setParentId(old.getParentId());
			entity.setLayer(old.getLayer());
			entity.setLevelDeep(old.getLevelDeep());
		}
		treeManager.save(entity);
		modelMap.remove("tree");
		modelMap.addAttribute("result", true);
		modelMap.addAttribute("entity", entity);
		return "jsonView";
	}
	
	@RequestMapping("getRoot")
	public String getRoot(ModelMap modelMap) {
		List<Tree> treeList=treeManager.getChildren(BaseTree.ROOT);
		modelMap.addAttribute("allProvince", treeList);
		return "jsonView";
	}
	
	@RequestMapping("select/{targetId}")
	public String select(HttpServletRequest request, @PathVariable Long targetId) throws Exception {
		PageSearch page  = preparePage(request);
		handlePage(request, page);
		afterPage(request, page,0);
		request.setAttribute("targetId", targetId);
		return getFileBasePath() + "selectTree";
	}
	
	@Override
	public PageManager<Tree> getEntityManager() {
		return treeManager;
	}
	@RequestMapping("/update/{entity}")
	public void update(Tree tree){
		treeManager.save(tree);
	}
	@Override
	public String getFileBasePath() {
		return "framework/";
	}
	
	@RequestMapping("saveOrder")
	public String saveOrder(HttpServletRequest request, ModelMap modelMap){
		String orderStr = request.getParameter("orders");
		if(StringUtils.isNotBlank(orderStr)){
			List<Tree> all = treeManager.getAll();
			String[] orders = orderStr.split(",");
			saveChildOrder(0, orders, BaseTree.ROOT,all);
		}
		modelMap.addAttribute("result", "保存成功");
		return "jsonView";
	}
	
	private int saveChildOrder(int index,String[] orders,long currentParentId,List<Tree> all){
		int orderId = Tree.DEFAULT_ORDER_ID;
		long currentTreeId = 0;
		//Only in the same deep
		for(;index<orders.length;){
			String order = orders[index];
			String[] arr = order.split("_");
			Long parentId = Long.parseLong(arr[0]);
			Long treeId = Long.parseLong(arr[1]);
			//the same deep
			if(parentId==currentParentId){
				Tree tree = getTree(all, treeId);
				if(tree.getOrderId()!=orderId){
					tree.setOrderId(orderId);
					treeManager.save(tree);
				}
				currentTreeId = treeId;
				orderId++;
				index++;
			//child
			} else if (parentId == currentTreeId){
				index = saveChildOrder(index, orders, currentTreeId,all);
			//parent
			} else {
				break;
			}
		}
		return index;
	}
	
	
	private Tree getTree(List<Tree> all,Long treeId){
		for(Tree tree : all){
			if(tree.getObjectId().equals(treeId)){
				return tree;
			}
		}
		return null;
	}
}
