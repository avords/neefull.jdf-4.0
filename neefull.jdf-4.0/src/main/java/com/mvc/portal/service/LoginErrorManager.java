/*
 * (#)loginErrorManager.java 1.0 2010-12-2
 */
package com.mvc.portal.service;

import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;

import com.mvc.framework.config.GlobalConfig;
import com.mvc.framework.service.BaseService;
import com.mvc.portal.model.LoginError;

@Service
public class LoginErrorManager extends BaseService<LoginError,Long>{

	/**
	 * @param loginName
	 * @param realIp
	 */
	public int getSuccessionLoginErrorCount(String loginName, String realIp) {
		String sql = "SELECT A FROM " + LoginError.class.getName() +" A WHERE localIp = ? AND loginName = ?" ;
		List<LoginError> errors = super.searchBySql(sql,new Object[]{realIp,loginName});
		return errors.size();
	}

	/**
	 * @param loginName
	 * @param realIp
	 * @return
	 */
	public Boolean isOutOfRestrictTime(String loginName, String realIp) {
		String sql = "SELECT MAX(A.login_Date) b FROM  F_LOGIN_ERROR  A WHERE A.local_Ip = ? AND A.login_Name = ?" ;
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter(0, realIp).setParameter(1, loginName);
		query.addScalar("b", new org.hibernate.type.TimestampType());
		List children = query.list();
		Date loginDate = (Date)children.iterator().next();
		Long space = new Date().getTime() - loginDate.getTime();
			if(space>= (GlobalConfig.getLoginErrorWaitTime()*60*1000L) ){
				return true;
			}
		return false;
	}
	
	public void updateLoginErrorStatus(String loginName){
		super.deleteByWhere("loginName='" + loginName + "'");
	}
}
