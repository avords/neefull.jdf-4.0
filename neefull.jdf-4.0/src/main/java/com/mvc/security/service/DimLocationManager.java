package com.mvc.security.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mvc.framework.service.BaseService;
import com.mvc.security.model.DimLocation;
import com.mvc.security.model.RoleLocation;
import com.mvc.security.model.UserRole;
@Service
public class DimLocationManager extends BaseService<DimLocation, Long>{

	public List<DimLocation> getDimLocationByRoleId(Long roleId){
		String sql = "SELECT  A from  " + DimLocation.class.getName() + " A,"
	        + RoleLocation.class.getName() + " D " + "WHERE A.objectId = D.locationId AND D.roleId = " + roleId;
		return searchBySql(sql);
	}

	public List<DimLocation> getUserLocation(Long userId){
		String sql = "SELECT  A from  " + DimLocation.class.getName() + " A,"
	        + RoleLocation.class.getName() + " D " + "WHERE A.objectId = D.locationId AND D.roleId in( select roleId from " + UserRole.class.getName() 
	        	+ " E where E.userId = " +userId + ")";
		return searchBySql(sql);
	}
}
