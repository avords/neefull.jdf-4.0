package com.mvc.security.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;

import com.mvc.Constants;
import com.mvc.framework.model.BaseEntity;

@Entity(name = "F_MENU")
@Audited
public class Menu extends BaseEntity {
	public static final int MENU_STATUS_ONLINE = 1;
	public static final int MENU_STATUS_OFFLINE = 2;

	public static final String HTTP_LINK_PREFIX = "http://";

	public static final String PORT_SPLIT = ":";

	public static final String ROOT_CONTEXT = "/";
	
	public static final String PATH_SEPARATOR = ROOT_CONTEXT;

	public static final int DEFAULT_HTTP_PORT = 80;

	public static final int ROOT_FOLDER_ID = 0;
	public static final int TYPE_FOLDER = 1;
	public static final int TYPE_MENU = 2;
	public static final int RESOURCE_STAUS_NORMAL = 1;
	public static final int RESOURCE_STAUS_OFFLINE = 2;
	public static final int DISPLAY_POSITION_SYSTEM_MENU = 0;
	public static final int DISPLAY_POSITION_TAB = 1;
	public static final int DISPLAY_POSITION_BOTH = 2;
	public static final int DISPLAY_NONE = 3;

	public static final Menu ROOT = new Menu();
	static{
		ROOT.setObjectId(0L);
		ROOT.setParentId(-1L);
		ROOT.setName("ROOT");
		ROOT.setType(TYPE_FOLDER);
	}
	@Length(max = Constants.ModelDefine.LARGER_LENGTH)
	private String url;
	private Integer type;
	private Integer orderId;
	private Long parentId;
	private Long serverId;
	private Long moduleId;
	@NotNull
	private Integer status;
	private String fullUrl;
	@Length(max = Constants.ModelDefine.BIG_LENGTH)
	private String path;
	@NotNull
	@Length(max = Constants.ModelDefine.MEDIUM_LENGTH)
	private String name;
	private Integer displayPosition;
	
	private Boolean crud;
	@Length(max = Constants.ModelDefine.LARGE_LENGTH)
	private String icon;
	
	@Transient
	private List<MenuLink> menuLinks = new ArrayList<MenuLink>(0);
	
	public String getUrl() {
    	return url;
    }

	public void setUrl(String url) {
    	this.url = url;
    }

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getParentId() {
    	return parentId;
    }

	public void setParentId(Long parentId) {
    	this.parentId = parentId;
    }

	public Long getServerId() {
    	return serverId;
    }

	public void setServerId(Long serverId) {
    	this.serverId = serverId;
    }
	public String getPath() {
    	return path;
    }

	public void setPath(String path) {
    	this.path = path;
    }
	@Transient
	public String getFullUrl() {
    	return fullUrl;
    }
	public void setFullUrl(String fullUrl) {
    	this.fullUrl = fullUrl;
    }
	public String getName() {
    	return name;
    }
	public void setName(String name) {
    	this.name = name;
    }
	public Integer getDisplayPosition() {
    	return displayPosition;
    }
	public void setDisplayPosition(Integer displayPosition) {
    	this.displayPosition = displayPosition;
    }

	public Boolean getCrud() {
		return crud;
	}

	public void setCrud(Boolean crud) {
		this.crud = crud;
	}
	
	public boolean isMenu(){
		return type==Menu.TYPE_MENU;
	}
	
	public static void main(String[] args) {
		System.out.println("http://www.mvc.com/sample/role/page".replace("/page", "/create"));
		System.out.println("/delete/11".indexOf("/delete/\\d+"));
	}

	public List<MenuLink> getMenuLinks() {
		return menuLinks;
	}

	public void setMenuLinks(List<MenuLink> menuLinks) {
		this.menuLinks = menuLinks;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	
}
