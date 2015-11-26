package com.mvc.security.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mvc.framework.service.BaseService;
import com.mvc.security.model.UserDepartment;
@Service
public class UserDepartmentManager  extends BaseService<UserDepartment, Long>  {
	public List<UserDepartment> getUserDepartmentsByUserId(Long userId){
		String sql = "SELECT A FROM " + UserDepartment.class.getName() + " A where A.userId = ?";
		return searchBySql(sql,new Object[]{userId});
	}

	public void saveUserDeapartments(Long userId,List<UserDepartment> userDepartments) {
		deleteUserDepartmentByUserId(userId);
		for (UserDepartment userDepartment : userDepartments) {
			if(null != userDepartment.getDepartmentId()){
				userDepartment.setUserId(userId);
				save(userDepartment);
			}
		}
	}

	public void deleteUserDepartmentByUserId(Long userId) {
		deleteByWhere("userId = " + userId);
	}
}
