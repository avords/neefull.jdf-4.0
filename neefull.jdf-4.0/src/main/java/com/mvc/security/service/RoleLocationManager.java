package com.mvc.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mvc.framework.service.BaseService;
import com.mvc.security.model.RoleLocation;
@Service
public class RoleLocationManager extends BaseService<RoleLocation, Integer> {

	private void saveRoleLocations(List<RoleLocation> roleMenus) {
		for (RoleLocation roleMenu : roleMenus) {
			save(roleMenu);
		}
	}

	public void saveRoleLocations(String[] menuIds, Long roleId) {
		deleteRoleLocationByRoleId(roleId);
		if (menuIds != null && menuIds.length > 0) {
			List<RoleLocation> roleMenus = new ArrayList<RoleLocation>(menuIds.length);
			for (String menuId : menuIds) {
				RoleLocation roleLocation = new RoleLocation();
				roleLocation.setLocationId(Long.valueOf(menuId));
				roleLocation.setRoleId(roleId);
				roleMenus.add(roleLocation);
			}
			saveRoleLocations(roleMenus);
		}
	}

	public int deleteRoleLocationByRoleId(Long roleId) {
		return deleteByWhere("roleId = " + roleId);
	}
}
