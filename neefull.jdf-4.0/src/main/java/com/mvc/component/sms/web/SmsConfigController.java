package com.mvc.component.sms.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.component.sms.model.SmsConfig;
import com.mvc.component.sms.service.SmsConfigManager;
import com.mvc.framework.service.PageManager;
import com.mvc.framework.web.PageController;
@Controller
@RequestMapping("/smsConfig")
public class SmsConfigController extends PageController<SmsConfig>{
	@Autowired
	private SmsConfigManager smsConfigManager;

	@Override
    public PageManager<SmsConfig> getEntityManager() {
	    return smsConfigManager;
    }

	@Override
    public String getFileBasePath() {
	    return "sms/";
    }

	@RequestMapping("/pause/{businessId}")
	public String pause(@PathVariable Long businessId){
		smsConfigManager.updateSmsConfigStatus(businessId,SmsConfig.SMS_STATUS_PAUSE);
		return "redirect:../page" + getMessage("操作成功");
	}

	@RequestMapping("/restart/{businessId}")
	public String restart(@PathVariable Long businessId){
		smsConfigManager.updateSmsConfigStatus(businessId,SmsConfig.SMS_STATUS_WAIT_SEND);
		return "redirect:../page" + getMessage("操作成功");
	}

}
