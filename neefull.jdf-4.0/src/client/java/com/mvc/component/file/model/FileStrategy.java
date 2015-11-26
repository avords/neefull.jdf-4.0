package com.mvc.component.file.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

import com.mvc.Constants;
import com.mvc.framework.model.BaseEntity;

/**
 * @author pubx
 */
@Entity(name = "F_FILE_STRATEGY")
@Audited
public class FileStrategy extends BaseEntity {
	
	public static final int COMPRESS_NO = 1;
	
	public static final int COMPRESS_ZIP = 2;
	
	public static final int COMPRESS_RAR = 3;
	
	public static final int COMPRESS_AUTO = 4;
	
	public static final int STORE_DB = 1;
	
	public static final int STORE_FTP = 2;
	
	public static final int STORE_DISK = 3;
	
	public static final int STORE_SMB = 4;

	// public static final int ENCRYPT_NO = 1;
	// public static final int ENCRYPT_YES = 2;

	
	public static final int CATALOG_ROOT = 0;
	
	public static final int CATALOG_YEARLY = 1;
	
	public static final int CATALOG_MONTHLY = 2;
	
	public static final int CATALOG_DAILY = 3;
	
	public static final int CATALOG_YMD = 4;
	/**
	 * Application name,identified the file owner
	 */
	@NotNull
	@Column(unique=true,length=Constants.ModelDefine.SHORT_LENGTH)
	private String appName;
	
	@NotNull
	private Integer storeType;
	
	@NotNull
	private Integer compressType;
	
	@NotNull
	@Column(length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String storagePath;
	@Column(length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String ftpIp;
	
	private Integer ftpPort;
	@Column(length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String ftpUser;
	@Column(length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String ftpPassword;

	
	@NotNull
	private Integer catalogType;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	public Integer getStoreType() {
		return storeType;
	}

	public void setStoreType(Integer storeType) {
		this.storeType = storeType;
	}
	public Integer getCompressType() {
		return compressType;
	}

	public void setCompressType(Integer compressType) {
		this.compressType = compressType;
	}
	public String getStoragePath() {
		return storagePath;
	}

	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}
	public String getFtpIp() {
		return ftpIp;
	}

	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
	}
	public String getFtpUser() {
		return ftpUser;
	}

	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}
	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	public Integer getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(Integer ftpPort) {
		this.ftpPort = ftpPort;
	}
	public Integer getCatalogType() {
		return catalogType;
	}

	public void setCatalogType(Integer catalogType) {
		this.catalogType = catalogType;
	}
}
