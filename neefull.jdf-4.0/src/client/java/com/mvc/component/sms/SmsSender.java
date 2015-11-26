package com.mvc.component.sms;

import java.util.Collection;
import java.util.Set;

public interface SmsSender {
	Collection getSentStates(Long sendId);
	Long sendSms(Long phoneNumber,String message);
	Long[] sendSms(Set<Long> phoneNumbers,String message);

}
