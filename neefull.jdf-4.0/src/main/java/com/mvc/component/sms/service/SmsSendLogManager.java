package com.mvc.component.sms.service;

import java.util.Date;
import java.util.Set;

import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.mvc.component.sms.model.SmsConfig;
import com.mvc.component.sms.model.SmsSendLog;
import com.mvc.framework.service.BaseService;
@Service
public class SmsSendLogManager extends BaseService<SmsSendLog, Long> {

	public void batchSave(Set<Long> userNumbers,SmsConfig smsConfig){
		StatelessSession session = getSession().getSessionFactory().openStatelessSession();
		Transaction tx = session.beginTransaction();
		long record = 1;
		SmsSendLog smsSendLog = new SmsSendLog();
		smsSendLog.setContent(smsConfig.getContent());;
		smsSendLog.setBusinessId(smsConfig.getBusinessId());
		smsSendLog.setSendDate(new Date());
		for(Long mobile:userNumbers) {
			smsSendLog.setMobile(mobile);
			session.insert(smsSendLog);
			if(record%1000==0){
				tx.commit();
				tx = session.beginTransaction();
			}
			record++;
		}
		tx.commit();
		session.close();
	}
}
