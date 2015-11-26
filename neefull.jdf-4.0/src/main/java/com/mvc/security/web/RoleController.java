package com.mvc.security.web;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.framework.service.PageManager;
import com.mvc.framework.util.ListUtils;
import com.mvc.framework.util.LocaleUtils;
import com.mvc.framework.web.PageController;
import com.mvc.security.model.Department;
import com.mvc.security.model.DimLocation;
import com.mvc.security.model.Menu;
import com.mvc.security.model.Operation;
import com.mvc.security.model.Role;
import com.mvc.security.model.User;
import com.mvc.security.service.DepartmentManager;
import com.mvc.security.service.DimLocationManager;
import com.mvc.security.service.MenuManager;
import com.mvc.security.service.OperationManager;
import com.mvc.security.service.RoleLocationManager;
import com.mvc.security.service.RoleManager;
import com.mvc.security.service.RoleMenuManager;
import com.mvc.security.service.RoleOperationManager;
import com.mvc.security.service.UserManager;
import com.mvc.security.service.UserRoleManager;

@Controller
@RequestMapping("/role")
public class RoleController extends PageController<Role> {
	private static final String BASE_DIR = "security/";
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private MenuManager menuManager;
	@Autowired
	private RoleMenuManager roleMenuManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private UserRoleManager userRoleManager;
	@Autowired
	private DepartmentManager departmentManager;
	@Autowired
	private OperationManager operationManager;
	@Autowired
	private RoleOperationManager roleOperationManager;
	@Autowired
	private DimLocationManager dimLocationManager;
	@Autowired
	private RoleLocationManager roleLocationManager;

	@Override
	public PageManager getEntityManager() {
		return roleManager;
	}

	@Override
	public String getFileBasePath() {
		return BASE_DIR;
	}

	@RequestMapping("/editRoleMenu/{roleId}")
	public String editRoleMenu(ModelMap modelMap, HttpServletRequest request, @PathVariable Long roleId) {
		Locale locale = LocaleUtils.getLocale(request);
		List<Menu> haveMenus = menuManager.getMenusByRoleId(roleId);
		List<Menu> allMenus = menuManager.getAllMenu();
		List<Menu> notHaveMenus = ListUtils.filter(allMenus, haveMenus);
		for(Menu menu : haveMenus){
			menu.setPath(MenuController.interpret(menu.getPath(), locale));
		}
		for(Menu menu : notHaveMenus){
			menu.setPath(MenuController.interpret(menu.getPath(), locale));
		}
		modelMap.addAttribute("haveMenus", haveMenus);
		modelMap.addAttribute("notHaveMenus", notHaveMenus);
		modelMap.addAttribute("role", roleManager.getByObjectId(roleId));
		return BASE_DIR + "editRoleMenu";
	}
	
	

	@RequestMapping("/saveRoleMenu")
	public String saveRoleMenu(HttpServletRequest request, Long roleId) {
		String[] menuIds = request.getParameterValues("menuId");
		roleMenuManager.saveRoleMenus(menuIds, roleId);
		return "redirect:/role/editRoleMenu/" + roleId + getMessage("common.base.success",request) + "&" + appendAjaxParameter(request);
	}

	@RequestMapping("/editRoleUser/{roleId}")
	public String editRoleUser(ModelMap modelMap,@PathVariable Long roleId) {
		List<Department> allDepartments = departmentManager.getAll();
		List<User> allMenus  = userManager.getAll();
		List<User> haveMenus = userManager.getUsersByRoleId(roleId);
		List<User> notHaveMenus = ListUtils.filter(allMenus, haveMenus);
		Department department;
		for(User user : notHaveMenus){
			department = getDepartment(allDepartments, user);
			user.setFullName(null==department?user.getUserName():department.getFullName() + "-" + user.getUserName());
		}
		for(User user : haveMenus){
			department = getDepartment(allDepartments, user);
			user.setFullName(null==department?user.getUserName():department.getFullName() + "-" + user.getUserName());
		}
		modelMap.addAttribute("haveMenus", haveMenus);
		modelMap.addAttribute("notHaveMenus", notHaveMenus);
		modelMap.addAttribute("role", roleManager.getByObjectId(roleId));
		return BASE_DIR + "editRoleUser";
	}

	@RequestMapping("/editRoleOpera/{roleId}")
	public String editRoleOpera(ModelMap modelMap,@PathVariable Long roleId) {
		List<Operation> allMenus  = operationManager.getAll();
		List<Operation> haveMenus = operationManager.getOperationsByRoleId(roleId);
		List<Operation> notHaveMenus = ListUtils.filter(allMenus, haveMenus);
		modelMap.addAttribute("haveMenus", haveMenus);
		modelMap.addAttribute("notHaveMenus", notHaveMenus);
		modelMap.addAttribute("role", roleManager.getByObjectId(roleId));
		return BASE_DIR + "editRoleOpera";
	}

	@RequestMapping("/saveRoleOpera")
	public String saveRoleOpera(HttpServletRequest request, Long roleId) {
		String[] operationIds = request.getParameterValues("selected");
		roleOperationManager.saveRoleOperationByRoleId(operationIds, roleId);
		return "redirect:/role/editRoleOpera/" + roleId + getMessage("common.base.saveSuccess",request);
	}

	private Department getDepartment(List<Department> allDepartments, User user) {
		Department department= null;
		if(null!=user.getDepartmentId()){
			department = departmentManager.getDepartment(user.getDepartmentId(), allDepartments);
		}
	    return department;
    }

	@RequestMapping("/saveRoleUser")
	public String saveRoleUser(HttpServletRequest request, Long roleId) {
		String[] userIds = request.getParameterValues("menuId");
		userRoleManager.saveUserRolesByRoleId(userIds, roleId);
		return "redirect:/role/editRoleUser/" + roleId + getMessage("common.base.saveSuccess",request) + "&" + appendAjaxParameter(request);
	}

	@RequestMapping("/editRoleLocation/{roleId}")
	public String editRoleLocation(ModelMap modelMap,@PathVariable Long roleId) {
		List<DimLocation> allMenus  = dimLocationManager.getAll();
		List<DimLocation> haveMenus = dimLocationManager.getDimLocationByRoleId(roleId);
		List<DimLocation> notHaveMenus = ListUtils.filter(allMenus, haveMenus);
		modelMap.addAttribute("haveMenus", haveMenus);
		modelMap.addAttribute("notHaveMenus", notHaveMenus);
		modelMap.addAttribute("role", roleManager.getByObjectId(roleId));
		return BASE_DIR + "editRoleLocation";
	}

	@RequestMapping("/saveRoleLocation")
	public String saveRoleLocation(HttpServletRequest request, Long roleId) {
		String[] operationIds = request.getParameterValues("menuId");
		roleLocationManager.saveRoleLocations(operationIds, roleId);
		return "redirect:/role/editRoleLocation/" + roleId + getMessage("common.base.saveSuccess",request) + "&" + appendAjaxParameter(request);
	}
	
	@RequestMapping("/roleNameExists")
	public @ResponseBody
	String roleNameExists(Role role) throws Exception {
		Role exist = roleManager.checkIfExistsRole(role);
		if (exist != null) {
			return "false";
		} else {
			return "true";
		}
	}
}
