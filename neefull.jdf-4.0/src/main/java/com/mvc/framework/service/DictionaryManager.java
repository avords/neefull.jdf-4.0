package com.mvc.framework.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.mvc.framework.model.BaseTree;
import com.mvc.framework.model.Dictionary;
/**
 * 字典表业务实现
 * 根据字典ID获得对象的方法都被添加了缓存
 * @author pubx
 */
public class DictionaryManager extends BaseService<Dictionary, Integer> {

	public void save(Dictionary entity) {
		super.save(entity);
		if(entity.getParentId()!=BaseTree.ROOT){
			Dictionary parent = getByObjectId(entity.getParentId());
			if(parent.getType()==BaseTree.FILE){
				parent.setType(BaseTree.FOLDER);
				super.save(parent);
			}
		}
	}

	public void deleteDictionary(Dictionary dictionary) {
		super.delete(dictionary);
	}

	public List<Dictionary> getDirectChildrenByParentId(long parentId) {
		List<Dictionary> dictionaries = searchBySql("select A from " + Dictionary.class.getName() + " A where A.parentId=?",new Object[]{parentId});
		Collections.sort(dictionaries, new DictionaryComparator());
		return dictionaries;
	}

	public List<Dictionary> getChildrenByParentId(long parentId) {
		List<Dictionary> dictionaries = searchBySql("select A from " + Dictionary.class.getName() + " A where A.parentId=?",new Object[]{parentId});
		if (dictionaries.size() > 0) {
			List<Dictionary> result = new ArrayList<Dictionary>();
			result.addAll(dictionaries);
			for (Dictionary dictionary : dictionaries) {
				if(null!=dictionary.getObjectId()){
					List<Dictionary> list = getChildrenByParentId(dictionary.getObjectId());
					if (list != null && list.size() > 0) {
						result.addAll(list);
					} else {
						return result;
					}
				}
			}
			return result;
		} else {
			return null;
		}
	}

	public void deleteDictionaresByDictionaryId(int dictionaryId) {
		deleteByWhere("dictionaryId = "+ dictionaryId);
	}

	public List<Dictionary> getChildrenByRootId(int rootId) {
		List<Dictionary> result = new ArrayList<Dictionary>();
		result.addAll(getDictionariesByDictionaryId(rootId));
		result.addAll(getChildrenByParentId(rootId));
		return result;
	}
	
	public List<Dictionary> getDictionariesByDictionaryId(int dictionaryId) {
		String sql = "select A from " + Dictionary.class.getName()
				+ " A," + Dictionary.class.getName() + " B  where A.parentId = B.objectId AND B.dictionaryId = " + dictionaryId + " ";
		List<Dictionary> dictionaries = searchBySql(sql);
		Collections.sort(dictionaries, new DictionaryComparator());
		return dictionaries;
	}
	
	public List<Dictionary> getDictionariesByDictionaryIdOrderByNameAsc(long dictionaryId){
		String sql = "select A from " + Dictionary.class.getName()
				+ " A," + Dictionary.class.getName() + " B  where A.parentId = B.objectId AND B.dictionaryId = " + dictionaryId + " order by A.name asc";
		List<Dictionary> dictionaries = searchBySql(sql);
		return dictionaries;
	}
	
	public List<Dictionary> getDictionariesByDictionaryIdOrderByNameDesc(long dictionaryId){
		String sql = "select A from " + Dictionary.class.getName()
				+ " A," + Dictionary.class.getName() + " B  where A.parentId = B.objectId AND B.dictionaryId = " + dictionaryId + " order by A.name desc";
		List<Dictionary> dictionaries = searchBySql(sql);
		return dictionaries;
	}
	
	public Dictionary getDictionaryByDictionaryId(long dictionaryId){
		String sql = "select A from " + Dictionary.class.getName()
				+ " A where dictionaryId = " + dictionaryId ;
	return searchObjectBySql(sql);
	}
	// The hook to update cache
	public void updateHook(int dictionaryId) {
		//nothing todo
	}

	static class DictionaryComparator implements Comparator<Dictionary> {
		public int compare(Dictionary o1, Dictionary o2) {
			if (null != o1.getSortId() && null != o2.getSortId()) {
				return o1.getSortId().compareTo(o2.getSortId());
			}
			return 0;

		}
	}
}