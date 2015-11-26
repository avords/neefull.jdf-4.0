package com.mvc.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mvc.framework.service.BaseService;
import com.mvc.security.model.RoleMenu;

@Service
public class RoleMenuManager extends BaseService<RoleMenu, Integer> {

	private void saveRoleMenus(List<RoleMenu> roleMenus) {
		for (RoleMenu roleMenu : roleMenus) {
			save(roleMenu);
		}
	}

	public void saveRoleMenus(String[] menuIds, Long roleId) {
		deleteRoleMenuByRoleId(roleId);
		if (menuIds != null && menuIds.length > 0) {
			List<RoleMenu> roleMenus = new ArrayList<RoleMenu>(menuIds.length);
			for (String menuId : menuIds) {
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setMenuId(Long.valueOf(menuId));
				roleMenu.setRoleId(roleId);
				roleMenus.add(roleMenu);
			}
			saveRoleMenus(roleMenus);
		}
	}

	public int deleteRoleMenuByRoleId(Long roleId) {
		return deleteByWhere("roleId = " + roleId);
	}
}
