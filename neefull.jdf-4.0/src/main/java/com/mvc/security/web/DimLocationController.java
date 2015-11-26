package com.mvc.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.framework.service.PageManager;
import com.mvc.framework.web.PageController;
import com.mvc.security.model.DimLocation;
import com.mvc.security.service.DimLocationManager;
@Controller
@RequestMapping("/dimLocation")
public class DimLocationController extends PageController<DimLocation>{
	@Autowired
	private DimLocationManager dimLocationManager;

	@Override
    public PageManager<DimLocation> getEntityManager() {
	    return dimLocationManager;
    }

	@Override
    public String getFileBasePath() {
	    return "security/";
    }

}
