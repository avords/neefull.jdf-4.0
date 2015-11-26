package com.mvc.component.file.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.component.file.model.FileStrategy;
import com.mvc.component.file.service.FileStrategyManager;
import com.mvc.framework.service.PageManager;
import com.mvc.framework.web.PageController;
@Controller
@RequestMapping("/fileStrategy")
public class FileStrategyController extends PageController<FileStrategy>{
	@Autowired
	private FileStrategyManager fileStrategyManager;

	@Override
    public PageManager<FileStrategy> getEntityManager() {
	    return fileStrategyManager;
    }

	@Override
    public String getFileBasePath() {
	    return "framework/";
    }

}
