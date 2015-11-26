package com.mvc.framework.i18n.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.framework.i18n.model.Language;
import com.mvc.framework.i18n.service.LanguageManager;
import com.mvc.framework.service.PageManager;
import com.mvc.framework.web.PageController;
@Controller
@RequestMapping("language")
public class LanguageController extends PageController<Language>{
	@Autowired
	private LanguageManager languageManager;

	@Override
    public PageManager<Language> getEntityManager() {
	    return languageManager;
    }

	@Override
    public String getFileBasePath() {
	    return "framework/";
    }
	
}
