package com.mvc.framework.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;

import com.mvc.framework.dao.PropertyFilter;

public class PageSearch implements Serializable {
    private static final long serialVersionUID = -1419154159597545749L;
	private static final int SEARCH_ID_FACTOR = 10000;
	public static final int DEFAULT_PAGE_SIZE = 15;
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	private int pageSize = DEFAULT_PAGE_SIZE;
	private int totalCount;
	private int currentPage;
	private List data;
	private transient String[] sortProperties = null;
	private transient String[] sortOrders = null;
	private transient List<Order> orders = null;
	private transient Class entityClass;
	//Format as relation_user.objectId_userRole.userId
	//          relation_userRole.roleId_role.objectId
	private transient Set<String> relationships = new LinkedHashSet<String>();
	//Property name format as user.name, role.name(Root entity's name can be ignore:(user.)name)
	private transient List<PropertyFilter> filters = new ArrayList<PropertyFilter>();

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalPage(){
		if(totalCount>0){
			return (totalCount-1)/pageSize + 1;
		}else {
			return 0;
		}
	}
	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	/**
	 * 返回实际的当前页，对页码进行了校验
	 * @return
	 */
	public int getPage(){
		if(currentPage<0){
			return 1;
		}else if(currentPage > getTotalPage()) {
			return getTotalPage();
		}else{
			return currentPage;
		}
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getBegin() {
		if (currentPage < 1) {
			currentPage = 1;
		}
		return (currentPage - 1) * pageSize;
	}
	
	/**
	 * 当前页最后一条，页面显示用
	 * @return
	 */
	public int getLast(){
		int end = getEnd();
		if(end>totalCount){
			return totalCount;
		}else{
			return end;
		}
	}

	public int getEnd() {
		return currentPage * pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List getList() {
		return data;
	}

	public void setList(List list) {
		this.data = list;
	}

	public String getSearchId() {
		return Integer.toHexString(pageSize * SEARCH_ID_FACTOR + currentPage);
	}

	public List<PropertyFilter> getFilters() {
    	return filters;
    }

	public void setFilters(List<PropertyFilter> filters) {
    	this.filters = filters;
    }

	public Class getEntityClass() {
    	return entityClass;
    }

	public void setEntityClass(Class entityClass) {
    	this.entityClass = entityClass;
    }

	public String[] getSortProperties() {
    	return sortProperties;
    }

	public void setSortProperties(String[] sortProperties) {
    	this.sortProperties = sortProperties;
    }

	public String[] getSortOrders() {
    	return sortOrders;
    }

	public void setSortOrders(String[] sortOrders) {
    	this.sortOrders = sortOrders;
    }

	public List<Order> getOrders() {
		if(orders==null){
			orders = new ArrayList<Order>();
			if(sortProperties != null && sortProperties.length > 0){
				for(int i=0;i<sortProperties.length;i++){
					String sort = sortProperties[i];
					if(StringUtils.isNotBlank(sort)){
						String orderType = null;
						if(sortOrders.length>=i){
							orderType = sortOrders[i];
						}
						if(StringUtils.isBlank(orderType)){
							orderType = ASC;
						}
						Order order = null;
						if(ASC.equalsIgnoreCase(orderType)){
							order = Order.asc(sort);
						}else{
							order = Order.desc(sort);
						}
						orders.add(order);
					}
				}
			}
		}
    	return orders;
    }

	public void setOrders(List<Order> orders) {
    	this.orders = orders;
    }

	public void setSortProperty(String sortProperty) {
    	this.sortProperties = new String[]{sortProperty};
    }

	public void setSortOrder(String sortOrder) {
    	this.sortOrders = new String[]{sortOrder};
    }
	public String getSortProperty() {
		if(sortProperties!=null){
			return sortProperties[0];
		}
    	return null;
    }
	
	public String getSortOrder() {
		if(sortOrders!=null){
			return sortOrders[0];
		}
    	return null;
    }

	public Set<String> getRelationships() {
		return relationships;
	}

	public void setRelationships(Set<String> relationships) {
		this.relationships = relationships;
	}
	
	public Object getFilterValue(String filterName){
		if(filterName!=null){
			for(PropertyFilter filter : filters){
				if(filter.getFilterName().equals(filterName)){
					return filter.getPropertyValue();
				}
			}
		}
		return null;
	}
}
