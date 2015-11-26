/*
 * (#)OnlineUser.java 1.0 2010-12-2 2010-12-2
 */
package com.mvc.portal.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.framework.service.BaseService;
import com.mvc.portal.model.OnlineUser;
import com.mvc.security.model.LoginLog;
import com.mvc.security.service.LoginLogManager;

@Service
public class OnlineUserManager extends BaseService<OnlineUser, Long>{
	private static final Logger LOGGER = Logger.getLogger(OnlineUserManager.class);
	@Autowired
	private LoginLogManager loginLogManager;
	/**
	 * @param userId
	 * @return
	 */
	public OnlineUser getOnlineByUserId(Long userId,Integer logoutFrom) {
		OnlineUser onlineUser  = null;
		// If need login out, marked status 0
		if(logoutFrom != null&&LoginLog.LOGOUT_BY_FORCED==logoutFrom){
			onlineUser = super.searchByWhere(" userId = " + userId + " AND status = " + OnlineUser.STATUS_ONLINE);
		} else{
			onlineUser = super.searchByWhere(" userId = " + userId + " AND status = " + OnlineUser.STATUS_ONLINE);
		}
		return onlineUser;
	}

	/**
	 * @return
	 */
	public List<OnlineUser> getAllOnlneUser() {
		String sql = "SELECT A FROM " + OnlineUser.class.getName() + " A WHERE A.status = " + OnlineUser.STATUS_ONLINE;
		List<OnlineUser> onlineUsers = super.searchBySql(sql);
		return onlineUsers;
	}

	public void deleteOnlineUser(Long userId , int logoutFrom ) {
		OnlineUser old = getOnlineByUserId(userId,logoutFrom);
		if(old!=null){
			List<LoginLog> list = searchBySql(LoginLog.class,"select A from " + LoginLog.class.getName() + " A where A.userId=? and A.beginDate=?",new Object[]{userId,old.getBeginDate()});
			if(list.size()>0){
				LoginLog loginLog = list.get(0);
				loginLog.setLogoutForm(logoutFrom);
				loginLog.setEndDate(new Date());
				loginLog.setOnlineTime((new Date().getTime()-loginLog.getBeginDate().getTime())/1000);
				loginLogManager.loginOut(loginLog);
				LOGGER.info("User " + old.getUserName() + " login out");
			}
			delete(old);
		}
	}

}
