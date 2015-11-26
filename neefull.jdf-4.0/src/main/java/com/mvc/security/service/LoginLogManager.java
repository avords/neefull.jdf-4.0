package com.mvc.security.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;

import com.mvc.framework.service.BaseService;
import com.mvc.security.model.LoginLog;
@Service
public class LoginLogManager extends BaseService<LoginLog, Long> {

	/**
     * @param userId
     * @param beginDate
     * @return
     */
    public LoginLog getLoginLogByUserIdAndBeginDate(Long userId, Date beginDate) {
    	StringBuilder sql = new StringBuilder(40);
		sql.append("SELECT A FROM ").append(LoginLog.class.getName());
		sql.append(" A WHERE  A.userId = ? AND beginDate = ?");
		Query query = getSession().createQuery(sql.toString()).setParameter(0, userId).setParameter(1, beginDate);
		List<LoginLog> list = query.list();
		if(list.size()>0){
			return list.get(0);
		}
	    return null;
    }

	/**
     * @param userId
     * @return
     */
    public Date getMaxVisiteByUserId(Long userId) {
    	String sql = "SELECT MAX(A.begin_date) b FROM  F_LOGIN_LOG A WHERE A.user_id = " + userId;
		SQLQuery query = getSession().createSQLQuery(sql);
		query.addScalar("b", new org.hibernate.type.TimestampType());
		List children = query.list();
	    return (Date)children.iterator().next();
    }

    public void loginOut(LoginLog loginLog){
    	StringBuilder sql = new StringBuilder(40);
		sql.append("UPDATE ").append(LoginLog.class.getName());
		sql.append(" A SET A.logoutForm = ?,endDate = ?,onlineTime=?,A.status=? WHERE A.userId=? AND A.beginDate=?");
		Query query = getSession().createQuery(sql.toString()).setParameter(0, loginLog.getLogoutForm()).setParameter(1, loginLog.getEndDate())
					 .setParameter(2, loginLog.getOnlineTime()).setParameter(3, LoginLog.STATUS_LOGOUT).setParameter(4, loginLog.getUserId()).setParameter(5, loginLog.getBeginDate());
		query.executeUpdate();
    }

}
