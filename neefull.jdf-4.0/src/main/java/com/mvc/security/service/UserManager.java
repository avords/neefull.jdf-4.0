package com.mvc.security.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.framework.model.PasswordChanged;
import com.mvc.framework.service.BaseService;
import com.mvc.security.model.Department;
import com.mvc.security.model.User;
import com.mvc.security.model.UserRole;
import com.mvc.security.util.SecurityUtils;

@Service
public class UserManager extends BaseService<User, Long> {
	@Autowired
	protected UserDepartmentManager userDepartmentManager;
	@Autowired
	protected DepartmentManager departmentManager;
	@Autowired
	protected UserRoleManager userRoleManager;
	@Autowired
	private PasswordChangedManager passwordChangedManager;

	public boolean authentication(User user) {
		boolean result = false;
		String loginName = user.getLoginName();
		String password = user.getPassword();
		User realUser = null;
		if (loginName != null && loginName.length() > 0) {
			if (password != null && password.length() > 0) {
				realUser = getUserByLoginName(loginName);
			}
		}
		if (null != realUser && null != realUser.getPassword()
		        && realUser.getPassword().equals(SecurityUtils.generatePassword(password))) {
			result = true;
		}
		return result;
	}

	public void delete(User entity) {
		userRoleManager.deleteUserRoleByUserId(entity.getObjectId());
		super.delete(entity);
	}
	
	public void save(User entity) {
		if (null == entity.getObjectId()) {
			entity.setPassword(SecurityUtils.generatePassword(entity.getPassword()));
			entity.setCreateDate(new Date());
		}
		super.save(entity);
	}
	
	/**
	 * include department info
	 */
	public User getUserByUserId(Long userId){
		User user = super.getByObjectId(userId);
		if(user!=null && user.getDepartmentId()!=null){
			user.setDepartment(departmentManager.getByObjectId(user.getDepartmentId()));
		}
		return user;
	}

	public User getUserByLoginName(String loginName) {
		String sql = "select A from " + User.class.getName() + " A where loginName=?";
		List<User> users =  searchBySql(sql, new Object[] { loginName });
		if (users.size() > 0) {
			User user = users.get(0);
			user.setDepartment(departmentManager.getDepartmentByDepartmentId(user.getDepartmentId()));
			return user;
		}
		return null;
	}
	
	public List<User> getUserByUserName(String userName) {
		String sql = "select A from " + User.class.getName() + " A where userName=?";
		return searchBySql(sql, userName);
	}

	public User getUserByLoginNameAndType(String loginName, Integer type) {
		String sql = "select A from " + User.class.getName() + " A where A.type = ? and loginName=?";
		List<User> users =  searchBySql(sql, new Object[] { type, loginName });
		if (users.size() > 0) {
			User user = users.get(0);
			user.setDepartment(departmentManager.getDepartmentByDepartmentId(user.getDepartmentId()));
			return user;
		}
		return null;
	}

	public User getUserByEmail(String email) {
		String sql = "select A from " + User.class.getName() + " A where A.email = ?";
		List<User> users =  searchBySql(sql, new Object[] { email });
		if (users.size() > 0) {
			User user = users.get(0);
			user.setDepartment(departmentManager.getDepartmentByDepartmentId(user.getDepartmentId()));
			return user;
		}
		return null;
	}

	public List<User> getUsersByKeyWords(String keyWords) {
		String sql = "select A from " + User.class.getName() + " A where loginName like '%" + keyWords + "%'";
		Query query = getSession().createQuery(sql);
		List<User> list = query.list();
		return list;
	}

	public User getUserByMobilePhone(String mobilePhone) {
		List<User> users = searchBySql("select A from "
				+ User.class.getName() + " where mobilePhone = ?",
				new Object[] { mobilePhone});;
		if (users.size() > 0) {
			User user = users.get(0);
			user.setDepartment(departmentManager.getDepartmentByDepartmentId(user.getDepartmentId()));
			return user;
		}
		return null;
	}

	public void updatePassword(User user) {
		StringBuilder hql = new StringBuilder();
		hql.append("UPDATE ").append(User.class.getName()).append(" SET password = '").append(user.getPassword())
		        .append("' WHERE ").append("objectId = ").append(user.getObjectId());
		Query query = getSession().createQuery(hql.toString());
		query.executeUpdate();
		PasswordChanged changed = new PasswordChanged();
		changed.setUserId(user.getObjectId());
		changed.setChangeDate(new Date());
		passwordChangedManager.save(changed);
		update(user.getObjectId());
	}

	public List<User> getUsersByRoleId(Long roleId) {
		String sql = "SELECT  A from  " + User.class.getName() + " A," + UserRole.class.getName() + " D "
		        + "WHERE A.objectId = D.userId AND D.roleId = " + roleId;
		return searchBySql(sql);
	}

	public Long getUserCount() {
		String sql = "select count(*) from " + User.class.getName();
		Query query = getSession().createQuery(sql);
		Long result = ((Long) query.iterate().next());
		return result;
	}

	public List<User> getUserByUserType(int userType) {
		String sql = "select A from " + User.class.getName() + " A where A.type = ?";
		return searchBySql(sql, userType);
	}

	public User checkIfExistsUser(User user) {
		String sql = "select A from " + User.class.getName() + " A where  A.loginName = ?";
		Object[] parameters = new Object[] { user.getLoginName() };
		Long objectId = user.getObjectId();
		if (objectId != null) {
			sql += " and A.objectId != ?";
			parameters = new Object[] { user.getLoginName(), objectId };
		}
		List<User> userList = searchBySql(sql, parameters);
		if (userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}
	
	public List<User> getUsersByDepartmentId(Long departmentId){
		String sql = "select A from " + User.class.getName() + " A where A.departmentId = ?";
		return searchBySql(sql, departmentId);
	}
	
	public List<User> getUsersByDepartmentLayer(String layer){
		String sql = "select A from " + User.class.getName() + " A, " + Department.class.getName() +" B where A.departmentId = B.objectId and B.layer = ?";
		return searchBySql(sql, layer);
	}
	
	public List<User> getValidUsers(){
		String sql = "select A from " + User.class.getName() + " A where A.status = ?";
		return searchBySql(sql, User.STATUS_VALID);
	}
}
