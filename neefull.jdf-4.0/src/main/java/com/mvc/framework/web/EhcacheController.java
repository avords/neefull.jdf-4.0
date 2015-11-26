package com.mvc.framework.web;

import javax.annotation.Resource;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("ehcache")
public class EhcacheController {
	
	@Resource
	@Autowired
	private CacheManager cacheManager;
	
	@RequestMapping(value = "/clearAll")
	public String clearAll(ModelMap modelMap) throws Exception {
		cacheManager.clearAll();
		modelMap.addAttribute("result", true);
		return "jsonView";
	}
	
	@RequestMapping(value = "/clear")
	public String clear(ModelMap modelMap,String cacheName) throws Exception {
		boolean result =false;
		if(StringUtils.isNotBlank(cacheName)){
			cacheManager.clearAllStartingWith(cacheName);
			result = true;
		}
		modelMap.addAttribute("result", result);
		return "jsonView";
	}
	
	@RequestMapping(value = "/statistics")
	public String status(ModelMap modelMap,String cacheName) throws Exception {
		boolean result =false;
		if(StringUtils.isNotBlank(cacheName)){
			Ehcache cache = cacheManager.getEhcache(cacheName);
			if(cache!=null){
				modelMap.addAttribute("statistics", cache.getStatistics().toString());
				result = true;
			}
		}
		
	/*	String[] cacheNames = cacheManager.getCacheNames();
		for (int i = 0; i < cacheNames.length; i++) {
			cacheName = cacheNames[i];
			System.out.println(cacheName + ":" + cacheManager.getCache(cacheName).getStatistics().toString());
		}*/
		modelMap.addAttribute("result", result);
		return "jsonView";
	}
	
	
}
