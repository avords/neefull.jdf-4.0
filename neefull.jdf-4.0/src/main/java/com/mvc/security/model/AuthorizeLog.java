package com.mvc.security.model;

import java.util.Date;

import javax.persistence.Entity;

import com.mvc.framework.model.BaseEntity;
@Entity(name = "F_AUTHORIZE_LOG")
public class AuthorizeLog extends BaseEntity {
	
	public static final int OPERATION_TYPE_ADD_USER = 1;
	
	public static final int OPERATION_TYPE_UPDATE_USER = 2;
	
	public static final int OPERATION_TYPE_DELETE_USER = 3;
	
	public static final int OPERATION_TYPE_ADD_ROLE = 4;
	
	public static final int OPERATION_TYPE_UPDATE_ROLE = 5;
	
	public static final int OPERATION_TYPE_DELETE_ROLE = 6;
	
	public static final int OPERATION_TYPE_ADD_PERMISSION = 7;
	
	public static final int OPERATION_TYPE_UPDATE_PERMISSION = 8;
	
	public static final int OPERATION_TYPE_DELETE_PERMISSION = 9;
	
	public static final int OPERATION_TYPE_ADD_USER_TO_ROLE = 10;
	
	public static final int OPERATION_TYPE_DELETE_USER_FROM_ROLE = 11;
	
	public static final int OPERATION_TYPE_ADD_PERMISSION_TO_ROLE = 12;
	
	public static final int OPERATION_TYPE_DELETE_PERMISSION_FROM_ROLE = 13;
	
	public static final int OPERATION_TYPE_ADD_ROLE_TO_USER = 14;
	
	public static final int OPERATION_TYPE_DELETE_ROLE_FROM_USER = 15;
	
	public static final int OPERATION_TYPE_BATCH_ADD_PERMISSION_TO_ROLE = 16;
	
	public static final int OPERATION_TYPE_BATCH_DELETE_PERMISSION_FROM_ROLE = 17;
	
	public static final int OPERATION_TYPE_UPDATE_PASSWORD = 18;

	/**
	 * Object ID
	 */
	private Long operationObject;
	/**
	 * Object Name
	 */
	private String operationObjectName;
	/**
	 * Operation content ID
	 */
	private Long operationContent;
	/**
	 * Operation content name
	 */
	private String operationContentName;
	/**
	 * Operation type
	 */
	private Integer operationType;
	/**
	 * Operator
	 */
	private Long userId;
	/**
	 * The operator department
	 */
	private String department;
	
	private Date operationDate;
	private Long operationId;

	public Long getOperationObject() {
    	return operationObject;
    }
	public void setOperationObject(Long operationObject) {
    	this.operationObject = operationObject;
    }

	public String getOperationObjectName() {
    	return operationObjectName;
    }
	public void setOperationObjectName(String operationObjectName) {
    	this.operationObjectName = operationObjectName;
    }

	public Long getOperationContent() {
    	return operationContent;
    }
	public void setOperationContent(Long operationContent) {
    	this.operationContent = operationContent;
    }

	public String getOperationContentName() {
    	return operationContentName;
    }
	public void setOperationContentName(String operationContentName) {
    	this.operationContentName = operationContentName;
    }

	public Integer getOperationType() {
    	return operationType;
    }
	public void setOperationType(Integer operationType) {
    	this.operationType = operationType;
    }

	public Long getUserId() {
    	return userId;
    }
	public void setUserId(Long userId) {
    	this.userId = userId;
    }

	public String getDepartment() {
    	return department;
    }
	public void setDepartment(String department) {
    	this.department = department;
    }

	public Date getOperationDate() {
    	return operationDate;
    }
	public void setOperationDate(Date operationDate) {
    	this.operationDate = operationDate;
    }

	public Long getOperationId() {
    	return operationId;
    }
	public void setOperationId(Long operationId) {
    	this.operationId = operationId;
    }

}
