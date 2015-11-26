package com.mvc.security.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.framework.dao.PropertyFilter;
import com.mvc.framework.service.PageManager;
import com.mvc.framework.util.ListUtils;
import com.mvc.framework.util.PageSearch;
import com.mvc.framework.web.PageController;
import com.mvc.portal.service.LoginErrorManager;
import com.mvc.security.SecurityConstants;
import com.mvc.security.model.Department;
import com.mvc.security.model.Role;
import com.mvc.security.model.User;
import com.mvc.security.model.UserRole;
import com.mvc.security.service.DepartmentManager;
import com.mvc.security.service.RoleManager;
import com.mvc.security.service.UserManager;
import com.mvc.security.service.UserRoleManager;
import com.mvc.security.util.SecurityUtils;

@Controller
@RequestMapping("/user")
public class UserController extends PageController<User> {
	
	private static final Logger LOGGER = Logger.getLogger(UserController.class);

	private static final String BASE_DIR = "security/";
	@Autowired
	private UserManager userManager;
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private UserRoleManager userRoleManager;
	@Autowired
	private DepartmentManager departmentManager;
	@Autowired
	private LoginErrorManager loginErrorManager;

	@RequestMapping("/editUserRole/{userId}")
	public String editUserRole(ModelMap modelMap, @PathVariable Long userId) {
		User user = userManager.getByObjectId(userId);
		List<Role> allRoles = roleManager.getAll();
		List<Role> haveRoles = roleManager.getRolesByUserId(userId);
		List<Role> notHaveRoles = ListUtils.filter(allRoles, haveRoles);
		modelMap.addAttribute("notHaveRoles", notHaveRoles);
		modelMap.addAttribute("haveRoles", haveRoles);
		modelMap.addAttribute("user", user);
		return BASE_DIR + "editUserRole";
	}

	@RequestMapping("/saveUserRole")
	public String saveUserRole(HttpServletRequest request, Long userId) {
		String[] roleIds = request.getParameterValues("menuId");
		if (roleIds == null || roleIds.length == 0) {
			userRoleManager.deleteUserRoleByUserId(userId);
		} else {
			List<UserRole> userRoles = new ArrayList<UserRole>(roleIds.length);
			for (String roleId : roleIds) {
				UserRole userRole = new UserRole();
				userRole.setRoleId(Long.valueOf(roleId));
				userRole.setUserId(userId);
				userRoles.add(userRole);
			}
			userRoleManager.saveUserRolesByUserId(userRoles, userId);
		}
		return "redirect:/user/editUserRole/" + userId + getMessage("common.base.success", request) + "&" + appendAjaxParameter(request);
	}

	@RequestMapping("/changePassword")
	public String changePassword() {
		return BASE_DIR + "changePassword";
	}

	@RequestMapping("/savePassword")
	public String savePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String oldPlainPassoed = request.getParameter("oldPassword");
		String newPlainPassword = request.getParameter("newPassword");
		String confirmPalinPassword = request.getParameter("confirmPassword");
		String resultMessage = "account.updatePassword.Failed";
		if (StringUtils.isNotBlank(newPlainPassword)) {
			if (newPlainPassword.equals(confirmPalinPassword)) {
				User user = userManager.getByObjectId((Long)request.getSession().getAttribute(SecurityConstants.USER_ID));
				if (null != user) {
					if (SecurityUtils.generatePassword(oldPlainPassoed).equals(user.getPassword())) {
						user.setPassword(SecurityUtils.generatePassword(newPlainPassword));
						userManager.updatePassword(user);
						resultMessage = "account.updatePaddword.Success";
					} else {
						resultMessage = "account.updatePaddword.oldPasswordWrong";
					}
				} else {
					resultMessage = "account.updatePaddword.accountNotExist";
				}
			} else {
				resultMessage = "account.updatePaddword.twoPasswordNotTheSame";
			}
		} else {
			resultMessage = "account.updatePaddword.newPasswordIsBlank";
		}
		return "redirect:changePassword" + getMessage(resultMessage, request) + "&" + appendAjaxParameter(request);
	}

	@RequestMapping("/resetPassword/{objectId}")
	public String resetPassword(HttpServletRequest request, @PathVariable Long objectId) {
		request.setAttribute("entity", userManager.getByObjectId(objectId));
		return BASE_DIR + "resetPassword";
	}

	@RequestMapping(value = "/saveReset", method = RequestMethod.POST)
	public String saveReset(HttpServletRequest request, User user) {
		String newPlainPassword = request.getParameter("newPassword");
		String message;
		if (StringUtils.isNotBlank(newPlainPassword)) {
			user.setPassword(SecurityUtils.generatePassword(newPlainPassword));
			userManager.updatePassword(user);
			message = "user.password.reset.success";
		} else {
			message = "user.password.reset.blank";
		}
		return "redirect:resetPassword/" + user.getObjectId() + getMessage(message, request) + "&"
				+ appendAjaxParameter(request);
	}

	@RequestMapping(value = "/unlock/{loginName}", method = RequestMethod.POST)
	public String unlock(@PathVariable String loginName, ModelMap modelMap) throws Exception {
		loginErrorManager.updateLoginErrorStatus(loginName);
		modelMap.put("result", true);
		return "jsonView";
	}
	
	@RequestMapping("/loginNameExists")
	public @ResponseBody
	String loginNameExists(User user) throws Exception {
		User exist = userManager.checkIfExistsUser(user);
		if (exist != null) {
			return "false";
		} else {
			return "true";
		}
	}
	
	protected String handleEdit(HttpServletRequest request, HttpServletResponse response, Long objectId)
			throws Exception {
		request.setAttribute("departments", departmentManager.getAll());
		return super.handleEdit(request, response, objectId);
	}
	
	protected String handlePage(HttpServletRequest request, PageSearch page) {
		request.setAttribute("departments", departmentManager.getAll());
		return super.handlePage(request, page);
	}
	
	@Override
	protected String handleSave(HttpServletRequest request, ModelMap modelMap, User t) throws Exception {
		// save departmentLayer
		if(t.getDepartmentId()!=null){
			Department department = departmentManager.getByObjectId(t.getDepartmentId());
			if(department!=null){
				t.setDepartmentLayer(department.getLayer());
			}
		}
		return super.handleSave(request, modelMap, t);
	}
	
	protected PageSearch preparePage(HttpServletRequest request) {
		PageSearch pageSearch = super.preparePage(request);
		pageSearch.getRelationships().add("menu.objectId_roleMenu.menuId;roleId_userRole.roleId;userId_user.objectId");
		pageSearch.getRelationships().add("role.objectId_userRole.roleId;userId_user.objectId");
		return pageSearch;
	}
	
	@RequestMapping("departmentUser/{departmentId}")
	public String departmentUser(HttpServletRequest request, @PathVariable Long departmentId){
		PageSearch page  = preparePage(request);
		page.getFilters().add(new PropertyFilter(getEntityName(),"EQL_departmentId", departmentId +""));
		handlePage(request, page);
		afterPage(request, page, IS_NOT_BACK);
		return getFileBasePath() + "departmentUser";
	}
	
	@RequestMapping("departmentUsers/{departmentId}")
	public String departmentUsers( @PathVariable Long departmentId,ModelMap modelMap){
		List<User> users = userManager.getUsersByDepartmentId(departmentId);
		modelMap.put("users", users);
 		return "jsonView";
	}
	
	@RequestMapping("selectSingle")
	public String select(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageSearch page = preparePage(request);
		handlePage(request, page);
		afterPage(request, page, IS_NOT_BACK);
		request.setAttribute("action", "selectSingle");
		request.setAttribute("departments", departmentManager.getAll());
		return getFileBasePath() + "selectSingleUser";
	}
	
	@Override
	public PageManager getEntityManager() {
		return userManager;
	}

	@Override
	public String getFileBasePath() {
		return BASE_DIR;
	}

}
