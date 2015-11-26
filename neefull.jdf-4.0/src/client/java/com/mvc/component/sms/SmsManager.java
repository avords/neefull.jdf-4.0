package com.mvc.component.sms;

import java.util.Set;

public interface SmsManager {
	void sendSms(Long phoneNumber,String message);
	void sendSms(Set<Long> phoneNumbers,String message);
}
