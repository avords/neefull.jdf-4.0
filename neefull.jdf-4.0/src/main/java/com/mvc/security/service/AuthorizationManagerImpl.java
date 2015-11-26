package com.mvc.security.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvc.framework.config.GlobalConfig;
import com.mvc.security.SecurityConstants;
import com.mvc.security.model.Department;
import com.mvc.security.model.Menu;
import com.mvc.security.model.MenuLink;
import com.mvc.security.model.Operation;
import com.mvc.security.model.Role;
import com.mvc.security.model.Server;
import com.mvc.security.model.User;

public class AuthorizationManagerImpl implements AuthorizationManager {
	private UserManager userManager;
	@Autowired
	private MenuManager menuManager;
	@Autowired
	private OperationManager operationManager;
	@Autowired
	private ServerManager serverManager;
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private DepartmentManager departmentManager;
	@Autowired
	private DimLocationManager dimLocationManager;
	@Autowired
	private MenuLinkManager menuLinkManager;

	public List<Menu> getAllMenuByContext(String context) {
		List<Server> servers = getServers(context);
		List<Menu> result = new ArrayList<Menu>();
		if(servers==null||servers.size()==0){
			result.addAll(menuManager.getAllOnLineMenus());
		}else{
			for (Server server : servers) {
				result.addAll(menuManager.getOnlineMenusByServerId(server.getObjectId()));
			}	
		}
		fillFullUrl(result);
		setMenuLinks(result);
		return result;
	}
	
	public List<Menu> getAllMenuAndFolderByContext(String context){
		List<Server> servers = getServers(context);
		List<Menu> result = new ArrayList<Menu>();
		for (Server server : servers) {
			result.addAll(menuManager.getMenusByServerId(server.getObjectId()));
		}
		fillFullUrl(result);
		result.addAll(menuManager.getAllFolder());
		Collections.sort(result,new MenuComparator());
		setMenuLinks(result);
		return result;
	}

	private List<Server> getServers(String context) {
		List<Server> servers;
		if (context.equals(GlobalConfig.getPortalServerName())) {
			servers = serverManager.getAll();
		} else {
			servers = serverManager.getServerByContext(context);
		}
		return servers;
	}

	public User getUserPermissionByLoginName(String loginName, String context) {
		User user = userManager.getUserByLoginName(loginName);
		if(null!=user){
			List<Menu> allPermissionMenus;
			List<Operation> allPermissionOperations;
			boolean hasAllPermission = hasAllPermission(user);
			if(hasAllPermission){
				allPermissionMenus = getAllMenuByContext(context);
				allPermissionOperations = operationManager.getAll();
			}else{
				allPermissionMenus = new ArrayList<Menu>();
				List<Server> servers = getServers(context);
				if(servers==null|| servers.size()==0){
					allPermissionMenus.addAll(menuManager.getMenusByUserId(user.getObjectId()));
				}else{
					for (Server server : servers) {
						allPermissionMenus.addAll(menuManager.getMenusByServerIdAndUserId(server.getObjectId(), user.getObjectId()));
					}
				}
				fillFullUrl(allPermissionMenus);
				allPermissionOperations = operationManager.getOperationsByUserId(user.getObjectId());
			}
			allPermissionMenus.addAll(menuManager.getAllFolder());
			Collections.sort(allPermissionMenus,new MenuComparator());
			setMenuLinks(allPermissionMenus);
			user.setMenus(allPermissionMenus);
			user.setOperations(allPermissionOperations);
			//Location permission
			user.setDimLocations(dimLocationManager.getUserLocation(user.getObjectId()));
			
			user.setRoles(roleManager.getRolesByUserId(user.getObjectId()));
		}

		if(user!=null&&null!=user.getDepartmentId()){
			Department department = departmentManager.getDepartmentByDepartmentId(user.getDepartmentId());
			if(null==department){
				department = new Department();
			}
			user.setDepartment(department);
			user.setDepartmentLayer(department.getLayer());
		}
		return user;
	}

	private void setMenuLinks(List<Menu> allPermissionMenus) {
		if(allPermissionMenus.size()>0){
			List<MenuLink> menuLinks = menuLinkManager.getAll();
			for(MenuLink menuLink : menuLinks){
				for(Menu menu:allPermissionMenus){
					if(menu.getObjectId()==menuLink.getMenuId().intValue()){
						menu.getMenuLinks().add(menuLink);
						break;
					}
				}
			}
		}
	}

	private boolean hasAllPermission(User user) {
	    boolean hasAllPermission = false;
	    if (SecurityConstants.SUPER_MANAGER.equals(user.getLoginName().toLowerCase())) {
	    	hasAllPermission = true;
	    } else {
	    	List<Role> roles = roleManager.getRolesByUserId(user.getObjectId());
	    	for(Role role : roles) {
	    		if(role.getObjectId().equals(Role.ALL_PERMISSION.getObjectId())){
	    			hasAllPermission = true;
	    			break;
	    		}
	    	}
	    }
	    return hasAllPermission;
    }

	static class MenuComparator implements Comparator<Menu> {
		public int compare(Menu o1, Menu o2) {
			if(null!=o1.getParentId()&&null!=o2.getParentId()){
				if(o1.getParentId().equals(o2.getParentId())){
					if(null!=o1.getOrderId()&&null!=o2.getOrderId()){
						return o1.getOrderId().compareTo(o2.getOrderId());
					}
					return 0;
				}
				return o1.getParentId().compareTo(o2.getParentId());
			}else{
				if(null!=o1.getOrderId()&&null!=o2.getOrderId()){
					return o1.getOrderId().compareTo(o2.getOrderId());
				}
				return 0;
			}

		}
	}

	private void fillFullUrl(List<Menu> menus) {
		Map<Long, String> serverRootUrl = new HashMap<Long, String>();
		for (Menu menu : menus) {
			if(Menu.TYPE_MENU == menu.getType()){
				String rootUrl = serverRootUrl.get(menu.getServerId());
				if (rootUrl == null) {
					rootUrl = getRootUrl(menu);
					serverRootUrl.put(menu.getServerId(), rootUrl);
				}
				menu.setFullUrl(rootUrl + menu.getUrl());
			}
		}
	}

	private String getRootUrl(Menu menu) {
		StringBuilder rootUrl = new StringBuilder(30);
		Server server = serverManager.getByObjectId(menu.getServerId());
		if(server!=null){
			if(StringUtils.isNotBlank(server.getDomain())||StringUtils.isNotBlank(server.getIp())){
				String prefix = server.getProtocol()==null?Menu.HTTP_LINK_PREFIX:server.getProtocol() + "://";
				rootUrl.append(prefix).append(StringUtils.isNotBlank(server.getDomain())?server.getDomain():server.getIp()).append(
				        server.getPort() == Menu.DEFAULT_HTTP_PORT ? "" : Menu.PORT_SPLIT + server.getPort());
			}
			if(StringUtils.isNotBlank(server.getContext())){
				rootUrl.append(Menu.ROOT_CONTEXT).append(server.getContext());
			}
		}
		return rootUrl.toString();
	}

	public UserManager getUserManager() {
    	return userManager;
    }

	public void setUserManager(UserManager userManager) {
    	this.userManager = userManager;
    }

	public MenuManager getMenuManager() {
    	return menuManager;
    }

	public void setMenuManager(MenuManager menuManager) {
    	this.menuManager = menuManager;
    }

	public ServerManager getServerManager() {
    	return serverManager;
    }

	public void setServerManager(ServerManager serverManager) {
    	this.serverManager = serverManager;
    }
}