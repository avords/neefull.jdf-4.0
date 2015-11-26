package com.mvc.security.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mvc.framework.service.BaseService;
import com.mvc.security.model.MenuLink;

@Service
public class MenuLinkManager extends BaseService<MenuLink, Long> {

	public List<MenuLink> getMenuLinksByMenuId(Long menuId) {
		String sql = "select A from " + MenuLink.class.getName() + " A where A.menuId = ?";
		return searchBySql(sql, new Object[] {menuId });
	}
}
