package com.mvc.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

import com.mvc.Constants;
import com.mvc.framework.model.BaseEntity;
@Entity(name="F_MENU_IMAGE")
@Audited
public class MenuImage extends BaseEntity{
	/**
	 * Module grade
	 */
	public static final int MENU_IMAGE_TYPE_MODULE = 1;
	/**
	 * Menu grade
	 */
	public static final int MENU_IMAGE_TYPE_MENU = 2;
	public static final String DEFAULT_IMAGE_NAME = "0.gif";
	public static final String DEFAULT_BIG_FOLDER_IMAGE_NAME = "10001.gif";
	public static final String DEFAULT_BIG_FILE_IMAGE_NAME = "10002.gif";

	private Long moduleId;
	private Long menuId;
	@NotNull
	private Integer type;
	@NotNull
	@Column(length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String imageName;

	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
}

