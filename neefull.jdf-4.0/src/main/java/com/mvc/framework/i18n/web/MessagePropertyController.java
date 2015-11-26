package com.mvc.framework.i18n.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.framework.i18n.model.Language;
import com.mvc.framework.i18n.model.MessageProperty;
import com.mvc.framework.i18n.service.LanguageManager;
import com.mvc.framework.i18n.service.MessagePropertyManager;
import com.mvc.framework.service.PageManager;
import com.mvc.framework.util.PageSearch;
import com.mvc.framework.web.PageController;
@Controller
@RequestMapping("messageProperty")
public class MessagePropertyController extends PageController<MessageProperty>{
	@Autowired
	private MessagePropertyManager messagePropertyManager;
	@Autowired
	private LanguageManager languageManager;
	
	protected String handlePage(HttpServletRequest request, PageSearch page) {
		page.setSortOrder("asc");
		page.setSortProperty("messageKey");
		request.setAttribute("languages", languageManager.getAll());
		return super.handlePage(request, page);
	}
	
	protected String handleEdit(HttpServletRequest request, HttpServletResponse response, Long objectId) throws Exception {
		request.setAttribute("languages", languageManager.getAll());
		return super.handleEdit(request, response, objectId);
	}
	
	@RequestMapping("editMessageProperty")
	public String editMessageProperty(HttpServletRequest request,String messageKey){
		List<MessageProperty> messageProperties = null;
		if(messageKey!=null){
			request.setAttribute("messageKey", messageKey);
			messageProperties = messagePropertyManager.getMessagePropertiesByMessageKey(messageKey);
		}
		List<Language> languages = languageManager.getAll();
		List<MessageProperty> properties = new ArrayList<MessageProperty>(languages.size());
		for(Language language : languages){
			MessageProperty messageProperty = null;
			if(messageProperties!=null&&messageProperties.size()>0){
				for(MessageProperty property : messageProperties){
					if(property.getLanguageId().equals(language.getObjectId())){
						messageProperty = property;
						break;
					}
				}
			}
			if(messageProperty==null){
				messageProperty = new MessageProperty();
			}
			messageProperty.setLanguage(language);
			properties.add(messageProperty);
		}
		request.setAttribute("messageProperties", properties);
		return getFileBasePath() + "editMessageProperty";
	}
	
	@RequestMapping("saveMessageProperty")
	public String saveMessageProperty(HttpServletRequest request,Language language,String messageKey){
		List<MessageProperty> messageProperties = language.getMessageProperties();
		messagePropertyManager.save(messageProperties, messageKey);
		return "redirect:editMessageProperty" + getMessage("common.base.success", request) + "&messageKey=" + messageKey;
	}
	
	@RequestMapping("deleteByKey")
	public String deleteByKey(HttpServletRequest request,String messageKey){
		if(StringUtils.isNotBlank(messageKey)){
			messagePropertyManager.deleteByWhere("messageKey='" + messageKey + "'");
		}
		return "redirect:back";
	}
	
	
	@Override
    public PageManager<MessageProperty> getEntityManager() {
	    return messagePropertyManager;
    }

	@Override
    public String getFileBasePath() {
	    return "framework/";
    }

}
