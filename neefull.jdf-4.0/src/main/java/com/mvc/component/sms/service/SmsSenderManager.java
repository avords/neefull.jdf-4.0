package com.mvc.component.sms.service;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.component.sms.SmsSender;
import com.mvc.component.sms.model.SmsConfig;
import com.mvc.framework.web.FrameworkFactory;

@Service
public class SmsSenderManager {
	private static final Logger LOGGER = Logger.getLogger(SmsSenderManager.class);
	@Autowired
	private SmsConfigManager smsConfigManager;
	@Autowired
	private UserNumberManager userNumberManager;
	@Autowired
	private SmsSendLogManager smsSendLogManager;

	public void smsRun() throws Exception {
		SmsSender smsSender = FrameworkFactory.getSmsSender();
		List<SmsConfig> waited = smsConfigManager.getWaitSendingSmsConfigs();
		LOGGER.info("Begin send,Total " + waited.size() + " will be sended");
		for(SmsConfig smsConfig : waited){
			smsConfigManager.updateSmsConfigStatus(smsConfig.getBusinessId(), SmsConfig.SMS_STATUS_SENDING);
			Set<Long> userNumbers = userNumberManager.getUserNumbersBusinessId(smsConfig.getBusinessId());
			if(null!=smsConfig.getContent()&&userNumbers.size()>0){
				smsSender.sendSms(userNumbers, smsConfig.getContent());
			}
			smsSendLogManager.batchSave(userNumbers, smsConfig);
			LOGGER.info("SMS business ID " + smsConfig.getBusinessId() + " have " + userNumbers.size() + " phone numbers");
			smsConfigManager.updateSmsConfigStatus(smsConfig.getBusinessId(), SmsConfig.SMS_STATUS_SENDED_SUCCESS);
		}
	}
}
