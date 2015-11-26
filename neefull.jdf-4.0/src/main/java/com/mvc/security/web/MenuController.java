package com.mvc.security.web;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.framework.service.PageManager;
import com.mvc.framework.util.LocaleUtils;
import com.mvc.framework.util.MessageUtils;
import com.mvc.framework.util.PageSearch;
import com.mvc.framework.web.PageController;
import com.mvc.security.model.Menu;
import com.mvc.security.model.Server;
import com.mvc.security.service.MenuManager;
import com.mvc.security.service.ServerManager;

@Controller
@RequestMapping("/menu")
public class MenuController extends PageController<Menu> {
	private static final String BASE_DIR = "security/";
	@Autowired
	private MenuManager menuManager;
	@Autowired
	private ServerManager serverManager;

	@Override
	public PageManager getEntityManager() {
		return menuManager;
	}

	@Override
	public String getFileBasePath() {
		return BASE_DIR;
	}
	
	public static final String interpret(String path,Locale locale){
		if(StringUtils.isNotBlank(path)){
			String[] paths = path.split("/");
			StringBuilder result = new StringBuilder(path.length()/5);
			for(int i=1;i<paths.length;i++){
				result.append("/").append(MessageUtils.getMessage(paths[i], locale));
			}
			return result.toString();
		}
		return path;
	}
	
	public static void main(String[] args) {
    }
	
	protected String handlePage(HttpServletRequest request, PageSearch page) {
		getEntityManager().find(page);
		Locale locale = LocaleUtils.getLocale(request);
		for(Menu menu : (List<Menu>)page.getList()){
			menu.setPath(interpret(menu.getPath(), locale));
		}
		return getFileBasePath() + "list" + getActualArgumentType().getSimpleName();
	}

	protected String handleEdit(HttpServletRequest request, HttpServletResponse response, Long objectId) throws Exception {
		List<Server> servers = serverManager.getAll();
		request.setAttribute("servers", servers);
		List<Menu> pathes = menuManager.getAllFolder();
		Locale locale = LocaleUtils.getLocale(request);
		for(Menu menu : pathes){
			menu.setPath(interpret(menu.getPath(), locale));
		}
		request.setAttribute("pathes", pathes);
		if(objectId==null){
			Menu menu = new Menu();
			menu.setCrud(true);
			request.setAttribute("entity", menu);
		}
		return super.handleEdit(request, response, objectId);
	}
}
