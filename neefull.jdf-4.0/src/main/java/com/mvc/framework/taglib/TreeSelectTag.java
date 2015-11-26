package com.mvc.framework.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.mvc.framework.model.Tree;
import com.mvc.framework.service.TreeManager;

public class TreeSelectTag extends TagSupport {
	private static final Logger LOGGER = Logger.getLogger(TreeSelectTag.class);
	private String codeNo;
	private String rootCodeNo;
	private static TreeManager treeManager;

	public int doStartTag() throws JspException {
		codeNo =(String) ExpressionEvaluatorManager.evaluate("codeNo", codeNo, String.class, this, pageContext);
		rootCodeNo =(String) ExpressionEvaluatorManager.evaluate("rootCodeNo", rootCodeNo, String.class, this, pageContext);
		try {
			if(StringUtils.isNotBlank(rootCodeNo)){
				List<Tree> all = treeManager.getRecursionChildrenByLayer(rootCodeNo);
				pageContext.getOut().write(selectOption(all, codeNo));
			}
		} catch (Exception e) {
			LOGGER.error("doStartTag()", e);
		}
		return EVAL_PAGE;
	}
	
	private String selectOption(List<Tree> trees,String selected) throws JspException, IOException {
		if(trees==null||trees.size()==0){
			return "";
		}
		StringBuilder result = new StringBuilder(trees.size()*35);
		Tree tree =trees.get(0);
		appendChild(trees, tree.getLevelDeep(), tree.getParentId(), result);
		return result.toString();
	}
	
	public void appendChild(List<Tree> trees,int baseDeep,long parentId,StringBuilder result){
		List<Tree> children = getChildren(trees, parentId);
		for(Tree tree : children){
			addTree(trees,baseDeep, result, tree);
		}
	}
	
	private List<Tree> getChildren(List<Tree> trees,long parentId){
		List<Tree> child = new ArrayList<Tree>();
		for(Tree tree : trees){
			if(tree.getParentId() == parentId){
				child.add(tree);
			}
		}
		return child;
	}

	private void addTree(List<Tree> trees, int baseDeep, StringBuilder result, Tree tree) {
	    String blank = "";
	    for (int step = 0; step < tree.getLevelDeep()- baseDeep; step++) {
	    	blank += "&nbsp;&nbsp;&nbsp;&nbsp;";
	    }
	    blank += "├-";
	    result.append("<option value='").append(tree.getLayer()).append("'");
	    if(tree.getLayer().equals(codeNo)){
	    	result.append(" selected ");
	    }
	    result.append(">").append(blank).append(tree.getName()).append("</option>");
	    appendChild(trees, baseDeep, tree.getObjectId(), result);
    }

	
	public static TreeManager getTreeManager() {
		return treeManager;
	}

	public void setTreeManager(TreeManager treeManager) {
		TreeSelectTag.treeManager = treeManager;
	}

	public String getCodeNo() {
    	return codeNo;
    }

	public void setCodeNo(String codeNo) {
    	this.codeNo = codeNo;
    }

	public String getRootCodeNo() {
    	return rootCodeNo;
    }

	public void setRootCodeNo(String rootCodeNo) {
    	this.rootCodeNo = rootCodeNo;
    }
}
