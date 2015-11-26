package com.mvc.framework.i18n.service;

import org.springframework.stereotype.Service;

import com.mvc.framework.i18n.model.Language;
import com.mvc.framework.service.BaseService;
@Service
public class LanguageManager extends BaseService<Language, Long>{
	
	public Language getLanguageByName(String name){
		return searchByWhere("name='" + name + "'");
	}
}
