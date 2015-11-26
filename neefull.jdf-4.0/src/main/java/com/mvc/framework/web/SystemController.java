package com.mvc.framework.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("system")
public class SystemController {
	
	@Resource
	@Autowired
	private CacheManager cacheManager;
	
	@RequestMapping(value = "info")
	public String info(HttpServletRequest request) throws Exception {
		
		/**
		 * System Info: User count,Menu count,Role count,Cache status,Job status
		 * System operation:clear cache, Stop job, Start job
		 * 
		 */
		Map<String,String> cacheInfo = new HashMap<String, String>(); 
		cacheManager.getCacheNames();
		
		for(String cacheName: cacheManager.getCacheNames()){
			cacheInfo.put(cacheName, cacheManager.getCache(cacheName).getStatistics().toString());
		}
		request.setAttribute("cacheInfo", cacheInfo);
		
		return "framework/systemInfo";
	}
}
