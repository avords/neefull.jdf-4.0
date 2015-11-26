package com.mvc.security.service;

import java.util.List;

import com.mvc.security.model.Menu;
import com.mvc.security.model.User;

/**
 * Authorization manager
 * @author pubx 2010-4-20 05:30:51
 */
public interface AuthorizationManager {

	List<Menu> getAllMenuByContext(String context);

	User getUserPermissionByLoginName(String loginName, String context);
	
	/**
	 * Get all menu of the specific context
	 * @param context
	 * @return
	 */
	List<Menu> getAllMenuAndFolderByContext(String context);
}
