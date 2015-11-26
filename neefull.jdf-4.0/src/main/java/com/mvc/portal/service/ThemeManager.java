package com.mvc.portal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mvc.framework.service.BaseService;
import com.mvc.portal.model.Theme;
@Service
public class ThemeManager extends BaseService<Theme, Long> {
	public void updateUserSkin(Theme theme){
		Theme old = getThemeByUserId(theme.getUserId());
		if(null!=old){
			old.setSkin(theme.getSkin());
			theme = old;
		}
		super.save(theme);
	}

	public Theme getThemeByUserId(Long userId){
		String sql = "select A from " + Theme.class.getName() + " A where userId=?";
		List<Theme> list =  searchBySql(sql, new Object[] {userId});
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
