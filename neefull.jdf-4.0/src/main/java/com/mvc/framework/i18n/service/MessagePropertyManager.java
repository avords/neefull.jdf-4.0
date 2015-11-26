package com.mvc.framework.i18n.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mvc.framework.i18n.model.MessageProperty;
import com.mvc.framework.service.BaseService;
import com.mvc.framework.util.MessageUtils;
@Service
public class MessagePropertyManager extends BaseService<MessageProperty, String>{
	
	public List<MessageProperty> getMessagePropertyByLanguageId(long languageId){
		return searchBySql("select A from " + MessageProperty.class.getName() + " A where A.languageId=?", new Object[]{languageId});
	}
	
	public void save(List<MessageProperty> messageProperties,String messageKey){
		deleteByWhere("messageKey='" + messageKey + "'");
		for(MessageProperty property : messageProperties){
			property.setMessageKey(messageKey);
			super.save(property);
		}
		MessageUtils.clear();
	}
	
	public int deleteByWhere(String where){
		MessageUtils.clear();
		return super.deleteByWhere(where);
	}
	
	public List<MessageProperty> getMessagePropertiesByMessageKey(String messageKey){
		return searchBySql("select A from " + MessageProperty.class.getName() + " A where messageKey=?", new Object[]{messageKey});
	}
}
