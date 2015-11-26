package com.mvc.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.framework.service.BaseService;
import com.mvc.security.model.Role;
import com.mvc.security.model.UserRole;

@Service
public class RoleManager extends BaseService<Role, Long> {
	
	@Autowired
	private UserRoleManager userRoleManager;
	@Autowired
	private RoleOperationManager roleOperationManager;
	@Autowired
	private RoleMenuManager roleMenuManager;
	@Autowired
	private RoleLocationManager roleLocationManager;

	public Role getRoleByName(String name) {
		String sql = "select A from " + Role.class.getName() + " A where name=?";
		List<Role> list = searchBySql(sql, new Object[] { name });
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public void delete(Role entity) {
		super.delete(entity);
		userRoleManager.deleteUserRoleByRoleId(entity.getObjectId());
		roleOperationManager.deleteRoleOperationByRoleId(entity.getObjectId());
		roleMenuManager.deleteRoleMenuByRoleId(entity.getObjectId());
		roleLocationManager.deleteRoleLocationByRoleId(entity.getObjectId());
	}


	public List<Role> getRolesByUserId(Long userId) {
		String sql = "SELECT A FROM " + Role.class.getName() + " A, " + UserRole.class.getName()
				+ " B where A.objectId = B.roleId AND B.userId = ?";
		return searchBySql(sql, new Object[] { userId });
	}

	public Role checkIfExistsRole(Role role) {
		String sql = "select A from " + Role.class.getName() + " A where A.name = ?";
		Object[] parameters = new Object[] { role.getName() };
		Long objectId = role.getObjectId();
		if (objectId != null && objectId != 0) {
			sql += " and A.objectId != ?";
			parameters = new Object[] { role.getName(), objectId };
		}
		List<Role> roleList = searchBySql(sql, parameters);
		if (roleList.size() > 0) {
			return roleList.get(0);
		}
		return null;
	}

}
