package com.mvc.security.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;

import com.mvc.Constants;
import com.mvc.framework.model.BaseEntity;

@Entity(name = "F_MENU_LINK")
@Audited
public class MenuLink extends BaseEntity{
	@NotNull
	private Long menuId;
	@NotNull
	@Length(max = Constants.ModelDefine.LARGE_LENGTH)
	private String url;
	@Length(max = Constants.ModelDefine.MEDIUM_LENGTH)
	private String remark;

	public String getUrl() {
		return url;
	}

	public Long getMenuId() {
    	return menuId;
    }

	public void setMenuId(Long menuId) {
    	this.menuId = menuId;
    }

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
    	return remark;
    }

	public void setRemark(String remark) {
    	this.remark = remark;
    }
}
