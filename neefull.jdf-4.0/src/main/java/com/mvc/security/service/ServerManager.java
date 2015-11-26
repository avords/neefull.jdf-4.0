package com.mvc.security.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mvc.framework.service.BaseService;
import com.mvc.security.model.Server;

@Service
public class ServerManager extends BaseService<Server, Long> {

	public List<Server> getServerByContext(String context) {
		String sql = "select A from " + Server.class.getName() + " A where A.context = ?";
		return searchBySql(sql, new Object[] { context });
	}
	
	public Server checkIfExistsServer(Server server) {
		String sql = "select A from " + Server.class.getName() + " A where  A.name = ?";
		Object[] parameters = new Object[] { server.getName() };
		Long objectId = server.getObjectId();
		if (objectId != null && objectId != 0) {
			sql += " and A.objectId != ?";
			parameters = new Object[] { server.getName(), objectId };
		}
		List<Server> userList = searchBySql(sql, parameters);
		if (userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}
}
