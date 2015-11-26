package com.mvc.framework.service;

import java.io.Serializable;

/**
 * CRUD interfase
 *
 * @param <T> entity
 * @param <PK> primary key
 * @author pubx 2010-3-29 09:38:26
 */
public interface Manager<T>{

	T getByObjectId(Serializable objectId);

	void delete(Serializable objectId);

	void delete(T entity);

	void save(T entity);
	
	/**
	 * Default for update cache of getByObjectId
	 * If update partly property, can call this to flush the cache
	 * @param objectId
	 */
	void update(Serializable objectId);
	
	/**
	 * Check the field is unique or else
	 * @param fieldName the field name
	 * @param value the field value, type can be String,Integer,Long,Date
	 * @param objectId optional
	 * @return
	 */
	boolean isFieldUnique(String fieldName, Object value, Serializable objectId);

	Serializable createSystemId();
}
