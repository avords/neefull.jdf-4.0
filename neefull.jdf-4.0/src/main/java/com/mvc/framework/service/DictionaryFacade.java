package com.mvc.framework.service;

import java.util.ArrayList;
import java.util.List;

import com.mvc.framework.model.Dictionary;
/**
 * Dictionary Facade to dictionary manager
 * @author pubx
 */
public class DictionaryFacade {

	private DictionaryManager dictionaryManager;

	public List<Dictionary> getDirectChildrenByParentId(int parentId) {
		return dictionaryManager.getDirectChildrenByParentId(parentId);
	}

	public List<Dictionary> getChildrenByParentId(int parentId) {
		return dictionaryManager.getChildrenByParentId(parentId);
	}

	public List<Dictionary> getChildrenByRootId(int rootId) {
		return dictionaryManager.getChildrenByRootId(rootId);
	}

	public Dictionary getDictionaryByDictionaryIdAndValue(int dictionaryId, int value) {
		List<Dictionary> dictionaries = dictionaryManager.getDictionariesByDictionaryId(dictionaryId);
		for (Dictionary dictionary : dictionaries) {
			if (value == dictionary.getValue()) {
				return dictionary;
			}
		}
		return null;
	}
	public Dictionary getDictionaryByDictionaryIdAndName(int dictionaryId, String name) {
		List<Dictionary> dictionaries = dictionaryManager.getDictionariesByDictionaryId(dictionaryId);
		for (Dictionary dictionary : dictionaries) {
			if (dictionary.getName().equals(name)) {
				return dictionary;
			}
			
			
		}
		return null;
	}
	public List<Dictionary> getDictionariesByDictionaryIdAndParentValue(int dictionaryId, int parentValue ) {
		List<Dictionary> dictionaries = dictionaryManager.getDictionariesByDictionaryId(dictionaryId);
		List<Dictionary> result = new ArrayList<Dictionary>();
		for (Dictionary dictionary : dictionaries) {
			if (parentValue == dictionary.getParentId()) {
				result.add(dictionary);
			}
		}
		return result;
	}

	public Dictionary getDictionaryByDictionaryId(long dictionaryId){
		return dictionaryManager.getDictionaryByDictionaryId(dictionaryId);
	}

	public Dictionary getDictionaryByParentIdAndValue(int parentId, int value) {
		List<Dictionary> dictionarys = dictionaryManager.getChildrenByParentId(parentId);
		if(null!=dictionarys){
			for (Dictionary dictionary : dictionarys) {
				if (value == dictionary.getValue()) {
					return dictionary;
				}
			}
		}
		return null;
	}
	
	public List<Dictionary> getDictionariesByDictionaryIdOrderByNameAsc(int dictionaryId){
		return dictionaryManager.getDictionariesByDictionaryIdOrderByNameAsc(dictionaryId);
	}
	
	public List<Dictionary> getDictionariesByDictionaryIdOrderByNameDesc(int dictionaryId){
		return dictionaryManager.getDictionariesByDictionaryIdOrderByNameDesc(dictionaryId);
	}

	public List<Dictionary> getDictionariesByDictionaryId(int dictionaryId) {
		return dictionaryManager.getDictionariesByDictionaryId(dictionaryId);
	}

	public void setDictionaryManager(DictionaryManager dictionaryManager) {
    	this.dictionaryManager = dictionaryManager;
    }
	
	public List<Dictionary> getValidDictionariesByDictionaryId(int dictionaryId) {
		List<Dictionary> dictionaries = dictionaryManager.getDictionariesByDictionaryId(dictionaryId);
		List<Dictionary> valid = new ArrayList<Dictionary>();
		for(Dictionary dictionary : dictionaries){
			if(dictionary.getStatus()!=null && dictionary.getStatus()){
				valid.add(dictionary);
			}
		}
		return valid;
	}
}
