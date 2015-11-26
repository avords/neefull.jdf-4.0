package com.mvc.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.framework.service.PageManager;
import com.mvc.framework.web.PageController;
import com.mvc.security.model.Server;
import com.mvc.security.service.ServerManager;

@Controller
@RequestMapping("/server")
public class ServerController extends PageController<Server>{
	private static final String BASE_DIR = "security/";
	@Autowired
	private ServerManager serverManager;

	@RequestMapping("/serverNameExists")
	public @ResponseBody
	String serverNameExists(Server server) throws Exception {
		Server exist = serverManager.checkIfExistsServer(server);
		if (exist != null) {
			return "false";
		} else {
			return "true";
		}
	}
	
	@Override
    public PageManager getEntityManager() {
	    return serverManager;
    }

	@Override
    public String getFileBasePath() {
	    return BASE_DIR;
    }

}
