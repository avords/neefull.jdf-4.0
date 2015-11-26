package com.mvc.security.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mvc.framework.config.ProjectConfig;
import com.mvc.framework.model.PasswordChanged;
import com.mvc.framework.service.BaseService;

@Service
public class PasswordChangedManager extends BaseService<PasswordChanged, Long> {

	public Date getLastChangedDate(Long userId) {	
		String sql = "select A from " + PasswordChanged.class.getName() + " A where A.userId = ?";
		List<PasswordChanged> changeds =searchBySql(sql, new Object[] {userId });
		if (changeds.size() > 0) {
			return changeds.get(0).getChangeDate();
		}
		return null;
	}

	public boolean isNeedChangePassword(Long userId) {
		boolean result = true;
		if (ProjectConfig.getPasswordExpired() < 0) {
			result = false;
		} else {
			Date date = getLastChangedDate(userId);
			if (date != null) {
				int days = (int) (System.currentTimeMillis() - date.getTime()) /(24 * 3600 * 1000);
				if (ProjectConfig.getPasswordExpired() > days) {
					result = false;
				}
			}
		}
		return result;
	}
}
