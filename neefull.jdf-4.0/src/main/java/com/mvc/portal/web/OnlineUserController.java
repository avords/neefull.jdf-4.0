/*
 * (#)OnlineUserController.java 1.0 2010-12-2
 */
package com.mvc.portal.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.framework.config.GlobalConfig;
import com.mvc.framework.service.PageManager;
import com.mvc.framework.util.FrameworkContextUtils;
import com.mvc.framework.web.PageController;
import com.mvc.portal.model.OnlineUser;
import com.mvc.portal.service.OnlineUserManager;

@Controller
@RequestMapping("/onlineUser")
public class OnlineUserController extends PageController<OnlineUser> {
	private static final Logger LOGGER = Logger.getLogger(OnlineUserController.class);
	@Autowired
	private OnlineUserManager onlineUserManager;

	@Override
	public PageManager<OnlineUser> getEntityManager() {
		return onlineUserManager;
	}

	@Override
	public String getFileBasePath() {
		return null;
	}
	@RequestMapping(value = "/checkOnlineUser")
	public String checkOnlineUser(HttpServletRequest request, ModelMap modelMap) throws Exception {
		boolean isNeedLogout = false;
		if(GlobalConfig.isSingleLogin()){
			OnlineUser onlineUser = onlineUserManager.searchByWhere(" sessionId = '" + request.getSession().getId() + "' AND status=" + false  );
			if(onlineUser!=null){
				isNeedLogout = true;
			}
		}
		if(!isNeedLogout){
			List<OnlineUser> onlineUsers  = onlineUserManager.getAllOnlneUser();
			boolean containCurrent = false;
			for(OnlineUser user : onlineUsers){
				if(user.getUserId().equals(FrameworkContextUtils.getCurrentUserId())){
					containCurrent = true;
					break;
				}
			}
			if(!containCurrent){
				OnlineUser user = new OnlineUser();
				user.setUserName(FrameworkContextUtils.getCurrentUserName());
				onlineUsers.add(user);
			}
			StringBuilder builder = new StringBuilder();
			for(OnlineUser online : onlineUsers ){
				builder.append(online.getUserName() + " ");
			}
			modelMap.addAttribute("allOnlineUserCount",onlineUsers.size());
			modelMap.addAttribute("allOnlineUserName",builder.toString());
		}
		modelMap.addAttribute("valid",!isNeedLogout);
		return "jsonView";
	}

}
