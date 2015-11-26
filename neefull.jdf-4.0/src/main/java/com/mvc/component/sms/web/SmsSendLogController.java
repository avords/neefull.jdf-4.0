package com.mvc.component.sms.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.component.sms.model.SmsSendLog;
import com.mvc.component.sms.service.SmsSendLogManager;
import com.mvc.framework.service.PageManager;
import com.mvc.framework.web.PageController;
@Controller
@RequestMapping("/smsSendLog")
public class SmsSendLogController extends PageController<SmsSendLog>{
	@Autowired
	private SmsSendLogManager smsSendLogManager;

	@Override
    public PageManager<SmsSendLog> getEntityManager() {
	    return smsSendLogManager;
    }

	@Override
    public String getFileBasePath() {
	    return "sms/";
    }

}
