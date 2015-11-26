package com.mvc.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.framework.service.BaseService;
import com.mvc.security.model.Operation;
import com.mvc.security.model.RoleOperation;
import com.mvc.security.model.UserRole;
@Service
public class OperationManager extends BaseService<Operation, Long>{
	
	@Autowired
	private RoleOperationManager roleOperationManager;

	public List<Operation> getOperationsByRoleId(Long roleId){
		String sql = "SELECT  A from  " + Operation.class.getName() + " A,"
	        + RoleOperation.class.getName() + " D " + "WHERE A.objectId = D.operationId AND D.roleId = " + roleId;
		return searchBySql(sql);
	}

	public List<Operation> getOperationsByUserId(Long userId){
		String sql = "SELECT  A from  " + Operation.class.getName() + " A,"
	        + RoleOperation.class.getName() + " D " + "WHERE A.objectId = D.operationId AND D.roleId in (select roleId from  " + UserRole.class.getName() 
	        + " C where  C.userId = " + userId + ")";
		return searchBySql(sql);
	}
	
	public Operation checkIfNameExists(Operation role) {
		String sql = "select A from " + Operation.class.getName() + " A where A.name = ?";
		Object[] parameters = new Object[] { role.getName() };
		Long objectId = role.getObjectId();
		if (objectId != null && objectId != 0) {
			sql += " and A.objectId != ?";
			parameters = new Object[] { role.getName(), objectId };
		}
		List<Operation> roleList = searchBySql(sql, parameters);
		if (roleList.size() > 0) {
			return roleList.get(0);
		}
		return null;
	}
	
	public Operation checkIfCodeExists(Operation role) {
		String sql = "select A from " + Operation.class.getName() + " A where A.code = ?";
		Object[] parameters = new Object[] { role.getCode() };
		Long objectId = role.getObjectId();
		if (objectId != null && objectId != 0) {
			sql += " and A.objectId != ?";
			parameters = new Object[] { role.getCode(), objectId };
		}
		List<Operation> roleList = searchBySql(sql, parameters);
		if (roleList.size() > 0) {
			return roleList.get(0);
		}
		return null;
	}
	
	public void delete(Operation entity) {
		roleOperationManager.deleteRoleOperationByOperationId(entity.getObjectId());
		super.delete(entity);
	}

}
