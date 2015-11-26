/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * $Id: PropertyFilter.java 763 2009-12-27 18:36:21Z calvinxiu $
 */
package com.mvc.framework.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.mvc.framework.util.DateUtils;
import com.mvc.framework.util.ReflectUtils;

/**
 *
 * Simple page query condition tools
 * @author calvin
 */
public class PropertyFilter implements Serializable {
	private static final long serialVersionUID = 2240796641258375858L;

	private static final Logger LOGGER = Logger.getLogger(PropertyFilter.class);
	/**
	 * Property compare type
	 */
	public enum MatchType {
		/**
		 * EQ:equal
		 * LIKE:likes
		 * NLIKE:not likes
		 * START:start with
		 * END:end with
		 * LT:less than
		 * GT:great than
		 * LE:less equal
		 * GE:great equal
		 * NE:not equal
		 * IN:is null
		 * NN:is not null
		 * IC:in
		 * NC:not in
		 */
		EQ, LIKE,START,END,LT, GT, LE, GE,NE,IN,NN,OR,NLIKE,IC,NC;
	}

	/**
	 * Property data type
	 */
	public enum PropertyType {
		S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class),T(Date.class);

		private Class<?> clazz;

		PropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getValue() {
			return clazz;
		}
	}
	//Root entity must let className empty
	private String objectName = null;
	private String propertyName = null;
	private Class<?> propertyType = null;
	private Object propertyValue = null;
	private List propertyValues = null;
	private MatchType matchType = null;
	private Set<PropertyFilter> orPropertyFilters;
	//Original parameter value
	private String propertyValueString = null;
	//Original parameter name
	private String filterName = null;
	
	public PropertyFilter(){
		
	}
	public PropertyFilter(Set<PropertyFilter> set) {
		this.matchType = MatchType.OR;
		this.orPropertyFilters = set;
	}

	public static void main(String[] args) {
    }

	/**
	 *  
	 * @param defalutObjectName default object name
	 * @param filterName property name
	 * @param value the value of the property
	 */
	public PropertyFilter(final String defalutObjectName, final String filterName, final String value) {
		String matchTypeStr = StringUtils.substringBefore(filterName, "_");
		String matchTypeCode = StringUtils.substring(matchTypeStr, 0, matchTypeStr.length() - 1);
		String propertyTypeCode = StringUtils.substring(matchTypeStr, matchTypeStr.length() - 1, matchTypeStr.length());
		this.filterName = filterName;
		matchType = Enum.valueOf(MatchType.class, matchTypeCode);
		propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode).getValue();
		propertyName =  StringUtils.substringAfter(filterName, "_");
		if(propertyName.indexOf(".")!=-1){
			String[] array = propertyName.split("\\.");
			objectName = array[0];
			propertyName = array[1];
		}else{
			objectName = defalutObjectName;
		}
		propertyValueString = value;
		try{
			this.propertyValue = ReflectUtils.convertStringToObject(value, propertyType);
			//Handle Date type
			if(propertyTypeCode.equals("D")){
				//Set to the begin of the date or end of the date
				if(matchType == MatchType.GE||matchType == MatchType.GT){
					this.propertyValue = DateUtils.getBeginOfTheDate((Date)this.propertyValue);
				}else if(matchType == MatchType.LE||matchType == MatchType.LT){
					this.propertyValue = DateUtils.getEndOfTheDate((Date)this.propertyValue);
				}
			}
			if (matchType == PropertyFilter.MatchType.IC || matchType == PropertyFilter.MatchType.NC) {
				String[] values = value.split(",");
				propertyValues = new ArrayList(values.length);
				for(String v : values){
					propertyValues.add(ReflectUtils.convertStringToObject(v, propertyType));
				}
			}
		}catch (Exception e) {
		}
	}

	
	public Object getPropertyValue() {
		return propertyValue;
	}

	
	public Class<?> getPropertyType() {
		return propertyType;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public Set<PropertyFilter> getOrPropertyFilters() {
    	return orPropertyFilters;
    }

	public String getPropertyName() {
    	return propertyName;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matchType == null) ? 0 : matchType.hashCode());
		result = prime * result + ((objectName == null) ? 0 : objectName.hashCode());
		result = prime * result + ((propertyName == null) ? 0 : propertyName.hashCode());
		result = prime * result + ((propertyValue == null) ? 0 : propertyValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertyFilter other = (PropertyFilter) obj;
		if (matchType != other.matchType)
			return false;
		if (objectName == null) {
			if (other.objectName != null)
				return false;
		} else if (!objectName.equals(other.objectName))
			return false;
		if (propertyName == null) {
			if (other.propertyName != null)
				return false;
		} else if (!propertyName.equals(other.propertyName))
			return false;
		if (propertyValue == null) {
			if (other.propertyValue != null)
				return false;
		} else if (!propertyValue.equals(other.propertyValue))
			return false;
		return true;
	}

	public String getPropertyValueString() {
    	return propertyValueString;
    }

	public void setPropertyValueString(String propertyValueString) {
    	this.propertyValueString = propertyValueString;
    }

	public String getFilterName() {
    	return filterName;
    }

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}

	public void setMatchType(MatchType matchType) {
		this.matchType = matchType;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	public List getPropertyValues() {
		return propertyValues;
	}
	public void setPropertyValues(List propertyValues) {
		this.propertyValues = propertyValues;
	}
}
