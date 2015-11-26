package com.mvc.component.sms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.component.sms.model.SmsConfig;
import com.mvc.component.sms.model.UserNumber;
import com.mvc.framework.service.BaseService;
import com.mvc.framework.util.DateUtils;
@Service
public class SmsConfigManager extends BaseService<SmsConfig, Long> {
	@Autowired
	private UserNumberManager userNumberManager;

	public void save(SmsConfig entity) {
		Long businessId = entity.getBusinessId();
		if(null==businessId){
			businessId = createSystemId().longValue();
			entity.setBusinessId(businessId);
			entity.setStatus(SmsConfig.SMS_STATUS_WAIT_SEND);
		}else{
			//not update the status
			SmsConfig old = getSmsConfigByBusinessId(businessId);
			entity.setStatus(old.getStatus());
			userNumberManager.deleteUserNumberByBusinessId(businessId);
		}
		//Default to now
		if(null==entity.getBeginTime()){
			entity.setBeginTime(new Date());
		}
		if(null==entity.getEndTime()){
			entity.setEndTime(DateUtils.getEndOfCurrentDay());
		}
		if(null!=entity.getUserNumbers()){
			String[] numbers = entity.getUserNumbers().split(",");
			List<UserNumber> userNumbers = new ArrayList<UserNumber>(numbers.length);
			for(String number : numbers){
				UserNumber userNumber = new UserNumber();
				userNumber.setBusinessId(businessId);
				try {
	                userNumber.setUserNumber(Long.parseLong(number));
                } catch (NumberFormatException e) {
	                e.printStackTrace();
                }
				userNumber.setStatus(UserNumber.STATUS_NOT_SEND);
				userNumbers.add(userNumber);
			}
			userNumberManager.save(userNumbers);
			entity.setTotal(userNumbers.size());
		}
		super.save(entity);
	}

	public void updateSmsConfigStatus(long businessId,int status){
		String hql = "UPDATE " + SmsConfig.class.getName() + " SET status = ? WHERE businessId = ?";
		Query query = getSession().createQuery(hql).setParameter(0, status).setParameter(1, businessId);
		query.executeUpdate();
	}

	public SmsConfig getSmsConfigByBusinessId(Long businessId){
		if(null!=businessId){
			String sql = "select A from " + SmsConfig.class.getName() + " A where businessId=?";
			List<SmsConfig> configs =  searchBySql(sql, new Object[] { businessId});
			if(configs.size()>0){
				return configs.get(0);
			}
		}
		return null;
	}

	public List<SmsConfig> getWaitSendingSmsConfigs(){
		String sql = "select A from " + SmsConfig.class.getName() + " A where status=?";
		List<SmsConfig> configs =  searchBySql(sql, new Object[] {SmsConfig.SMS_STATUS_WAIT_SEND});
		List<SmsConfig> result = new ArrayList<SmsConfig>();
		long current = new Date().getTime();
		for(SmsConfig config : configs) {
			if((null==config.getBeginTime()||current>=config.getBeginTime().getTime())&&(null==config.getEndTime()||current<=config.getEndTime().getTime())){
				result.add(config);
			}
		}
		return result;
	}

	public void delete(SmsConfig entity) {
		super.delete(entity);
		userNumberManager.deleteUserNumberByBusinessId(entity.getBusinessId());
	}

}
