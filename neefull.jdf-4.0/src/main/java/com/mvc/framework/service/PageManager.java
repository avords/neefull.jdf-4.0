package com.mvc.framework.service;

import com.mvc.framework.util.PageSearch;

/**
 * Page query
 * @param <T> Entity
 * @author pubx 2010-3-29 09:41:31
 */
public interface PageManager<T> extends Manager<T>{
	void find(PageSearch page);
}
