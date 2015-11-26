package com.mvc.component.sms.service;


import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.mvc.component.sms.model.UserNumber;
import com.mvc.framework.service.BaseService;
@Service
public class UserNumberManager extends BaseService<UserNumber, Long>{

	public void deleteUserNumberByBusinessId(Long businessId){
		deleteByWhere("businessId=" + businessId);
	}

	public Set<Long> getUserNumbersBusinessId(Long businessId){
		if(null!=businessId){
			String sql = "select A from " + UserNumber.class.getName() + " A where businessId=?";
			List<UserNumber> userNumbers =  searchBySql(sql, new Object[] {businessId});
			Set<Long> numbers = new TreeSet<Long>();
			for(UserNumber number : userNumbers){
				numbers.add(number.getUserNumber());
			}
			return numbers;
		}
		return null;
	}

	public void save(List<UserNumber> userNumbers){
		StatelessSession session = getSession().getSessionFactory().openStatelessSession();
		Transaction tx = session.beginTransaction();
		long record = 1;
		for(UserNumber entity:userNumbers) {
			session.insert(entity);
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
