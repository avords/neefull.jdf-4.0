package com.mvc.component.sms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.component.sms.SmsManager;
import com.mvc.component.sms.model.SmsConfig;
import com.mvc.component.sms.model.UserNumber;
@Service("smsManager")
public class SmsManagerImpl implements SmsManager {
	@Autowired
	private SmsConfigManager smsConfigManager;
	@Autowired
	private UserNumberManager userNumberManager;

	public void sendSms(Long phoneNumber, String message) {
		Set<Long> set = new HashSet<Long>();
		set.add(phoneNumber);
		sendSms(set, message);
    }

	public void sendSms(Set<Long> phoneNumbers, String message) {
		SmsConfig smsConfig = new SmsConfig();
		smsConfig.setContent(message);
		smsConfig.setBeginTime(new Date());
		smsConfigManager.save(smsConfig);

		Long businessId = smsConfig.getBusinessId();
		List<UserNumber> userNumbers = new ArrayList<UserNumber>(phoneNumbers.size());
		for(Long number : phoneNumbers){
			UserNumber userNumber = new UserNumber();
			userNumber.setBusinessId(businessId);
			userNumber.setUserNumber(number);
			userNumber.setStatus(UserNumber.STATUS_NOT_SEND);
			userNumbers.add(userNumber);
		}
		userNumberManager.save(userNumbers);
    }
}
