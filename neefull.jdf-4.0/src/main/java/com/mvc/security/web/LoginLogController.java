package com.mvc.security.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.framework.service.PageManager;
import com.mvc.framework.util.PageSearch;
import com.mvc.framework.web.PageController;
import com.mvc.security.model.LoginLog;
import com.mvc.security.service.LoginLogManager;
import com.mvc.security.service.UserManager;
@Controller
@RequestMapping("loginLog")
public class LoginLogController extends PageController<LoginLog>{
	@Autowired
	private LoginLogManager loginLogManager;
	
	@Autowired
	private UserManager userManager;

	protected String handlePage(HttpServletRequest request, PageSearch page) {
		request.setAttribute("users", userManager.getAll());
		return super.handlePage(request, page);
	}
	
	@Override
	public PageManager<LoginLog> getEntityManager() {
		return loginLogManager;
	}

	@Override
	public String getFileBasePath() {
		return "security/";
	}
	
}
