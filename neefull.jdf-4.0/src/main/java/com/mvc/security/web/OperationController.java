package com.mvc.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.framework.service.PageManager;
import com.mvc.framework.web.PageController;
import com.mvc.security.model.Operation;
import com.mvc.security.service.OperationManager;
@Controller
@RequestMapping("/opera")
public class OperationController extends PageController<Operation>{
	@Autowired
	private OperationManager operationManager;

	
	@RequestMapping("/nameExists")
	public @ResponseBody
	String nameExists(Operation role) throws Exception {
		Operation exist = operationManager.checkIfNameExists(role);
		if (exist != null) {
			return "false";
		} else {
			return "true";
		}
	}
	
	@RequestMapping("/codeExists")
	public @ResponseBody
	String codeExists(Operation role) throws Exception {
		Operation exist = operationManager.checkIfCodeExists(role);
		if (exist != null) {
			return "false";
		} else {
			return "true";
		}
	}
	
	
	@Override
    public PageManager<Operation> getEntityManager() {
	    return operationManager;
    }

	@Override
    public String getFileBasePath() {
	    return "security/";
    }

}
