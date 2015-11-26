package com.mvc.portal.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.framework.service.PageManager;
import com.mvc.framework.web.PageController;
import com.mvc.portal.model.MenuImage;
import com.mvc.portal.service.MenuImageManager;
import com.mvc.security.SecurityConstants;
import com.mvc.security.model.Menu;
@Controller
@RequestMapping("/menuImage")
public class MenuImageController extends PageController<MenuImage>{
	@Autowired
	private MenuImageManager menuImageManager;
	@Override
	public PageManager<MenuImage> getEntityManager() {
		return menuImageManager;
	}

	@Override
	public String getFileBasePath() {
		return "portal/";
	}
	
	public static void main(String[] args) {
		String url = Thread.currentThread().getContextClassLoader().getResource("").toString();
		System.out.println(url);
    }

	protected String handleEdit(HttpServletRequest request, HttpServletResponse response, Long id) throws Exception {
		MenuImage entity = null;
		if (null != id) {
			entity = getEntityManager().getByObjectId(id);
			if(entity.getImageName()==null){
				entity.setImageName(MenuImage.DEFAULT_IMAGE_NAME);
			}
		}else{
			entity = new MenuImage();
			entity.setImageName(MenuImage.DEFAULT_IMAGE_NAME);
		}
		request.setAttribute("entity", entity);
		List<Menu> permissions = (List<Menu>)request.getSession().getAttribute(SecurityConstants.MENU_PERMISSION);
		List<Menu> folders = new ArrayList<Menu>();
		for(Menu menu : permissions){
			if(menu.getType()==Menu.TYPE_FOLDER){
				folders.add(menu);
			}
		}
		request.setAttribute("menus", permissions);
		request.setAttribute("folders", folders);
		return getFileBasePath() + "edit" + getActualArgumentType().getSimpleName();
	}
	
	public String getSubMenu(HttpServletRequest request){
		return "";
	}
}
