package com.mvc.security.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mvc.framework.service.BaseService;
import com.mvc.security.model.UserRole;

@Service
public class UserRoleManager extends BaseService<UserRole, Long> {
	private static final Logger LOGGER = Logger.getLogger(UserRoleManager.class);

	public void saveUserRolesByUserId(List<UserRole> userRoles,Long userId) {
		deleteUserRoleByUserId(userId);
		if (userRoles.size() > 0) {
			for (UserRole userRole : userRoles) {
				save(userRole);
			}
		}
	}

	public void deleteUserRoleByUserId(Long userId) {
		deleteByWhere("userId = " + userId);
	}

	public  List<UserRole> getUserRolesByUserId(Long userId) {
		return searchBySql("select A from " + UserRole.class.getName() + " A where userId = " + userId);
	}
	
	public List<UserRole> getUserRolesByRoleId(Long roleId) {
		return searchBySql("select A from " + UserRole.class.getName() + " A where roleId = " + roleId);
	}

	public void saveUserRolesByRoleId(String[] userIds,Long roleId) {
		deleteUserRoleByRoleId(roleId);
		if (userIds!=null&&userIds.length > 0) {
			UserRole userRole;
			for (String userId : userIds) {
				try {
					userRole = new UserRole();
					userRole.setUserId(Long.valueOf(userId));
					userRole.setRoleId(roleId);
					save(userRole);
                } catch (NumberFormatException e) {
                	LOGGER.error("saveUserRolesByRoleId",e);
                }
			}
		}
	}

	public int deleteUserRoleByRoleId(Long roleId) {
		return deleteByWhere("roleId = " + roleId);
	}
}
