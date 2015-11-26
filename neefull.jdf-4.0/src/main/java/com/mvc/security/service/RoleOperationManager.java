package com.mvc.security.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mvc.framework.service.BaseService;
import com.mvc.security.model.RoleOperation;
@Service
public class RoleOperationManager extends BaseService<RoleOperation, Long>{
	private static final Logger LOGGER = Logger.getLogger(RoleOperationManager.class);

	public void saveUserRolesByUserId(List<RoleOperation> roleOperations,Long roleId) {
		deleteRoleOperationByRoleId(roleId);
		if (roleOperations.size() > 0) {
			for (RoleOperation roleOperation : roleOperations) {
				save(roleOperation);
			}
		}
	}

	public int deleteRoleOperationByRoleId(Long roleId) {
		return deleteByWhere("roleId = " + roleId);
	}
	
	public int deleteRoleOperationByOperationId(Long operationId) {
		return deleteByWhere("operationId = " + operationId);
	}

	public void saveRoleOperationByRoleId(String[] operationIds,Long roleId) {
		deleteRoleOperationByRoleId(roleId);
		if (null!=operationIds&&operationIds.length > 0) {
			RoleOperation roleOperation;
			for (String operationId : operationIds) {
				try {
					roleOperation = new RoleOperation();
					roleOperation.setOperationId(Long.valueOf(operationId));
					roleOperation.setRoleId(roleId);
					save(roleOperation);
                } catch (NumberFormatException e) {
                	LOGGER.error("saveRoleOperationByRoleId",e);
                }
			}
		}
	}
}
