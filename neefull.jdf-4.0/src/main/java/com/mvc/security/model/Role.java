package com.mvc.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;

import com.mvc.Constants;
import com.mvc.framework.model.BaseEntity;

@Entity(name = "F_ROLE")
@Audited
public class Role extends BaseEntity {
	
	public static final int ROLE_TYPE_COMMON = 1;
	public static final int ROLE_TYPE_MANAGER = 2;
	public static final int ROLE_TYPE_ADMIN = 3;
	public static final Role ALL_PERMISSION = new Role();

	static{
		ALL_PERMISSION.setObjectId(0L);
		ALL_PERMISSION.setName("Super Adminn");
		ALL_PERMISSION.setType(ROLE_TYPE_MANAGER);
	}

	@NotNull
	@Column(unique=true,length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String name;
	private Integer type;
	@Length(max=64)
	private String departmentLayer;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDepartmentLayer() {
		return departmentLayer;
	}

	public void setDepartmentLayer(String departmentLayer) {
		this.departmentLayer = departmentLayer;
	}
}
