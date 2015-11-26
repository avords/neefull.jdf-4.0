package com.mvc.component.sms.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.component.sms.model.UserNumber;
import com.mvc.component.sms.service.UserNumberManager;
import com.mvc.framework.service.PageManager;
import com.mvc.framework.web.PageController;
@Controller
@RequestMapping("/userNumber")
public class UserNumberController extends PageController<UserNumber>{
	@Autowired
	private UserNumberManager userNumberManager;

	@Override
    public PageManager<UserNumber> getEntityManager() {
	    return userNumberManager;
    }

	@Override
    public String getFileBasePath() {
	    return "sms/";
    }

}
