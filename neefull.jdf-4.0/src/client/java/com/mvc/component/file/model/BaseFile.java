package com.mvc.component.file.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.mvc.Constants;

@Entity(name = "F_BASE_FILE")
public class BaseFile implements Serializable {
	
	public static final int FILE_STATUS_ERROR = 0;
	
	public static final int FILE_STATUS_OK = 1;
	
	public static final int FILE_STATUS_DELETE = 2;
	
	public static final int FILE_STATUS_DEL_FAIL = 3;
	
	public static final int FILE_STATUS_NOT_EXITS = 4;

	public static void main(String[] args) {
		//20101105033247005
		//20101105033259767
		//20101105153330014
		//9223372036854775807

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	    System.out.println(Long.MAX_VALUE);
		System.out.println(dateFormat.format(new Date()));
//		Random random = new Random();
//		for(int i=0;i<100;i++){
//			System.out.println(random.nextInt(100));
//		}
    }
	/**
	 * yyyyMMddHHmmss + 5 digits number
	 */
	@Id
	private Long fileId;
	@NotNull
	@Column(length=Constants.ModelDefine.LARGE_LENGTH)
	private String name;
	private Integer saveDay;
	@NotNull
	private Date createDate;
	@NotNull
	private Integer fileSize;
	@NotNull
	private Integer status;
	@NotNull
	private Long strategyId;
	@Transient
	private FileStrategy fileStrategy;
	@Transient
	private String appName;
	@Transient
	private String relativePath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSaveDay() {
		return saveDay;
	}

	public void setSaveDay(Integer saveDay) {
		this.saveDay = saveDay;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}
	@Transient
	public FileStrategy getFileStrategy() {
		return fileStrategy;
	}

	public void setFileStrategy(FileStrategy fileStrategy) {
		this.fileStrategy = fileStrategy;
	}

	public Integer getStatus() {
    	return status;
    }

	public void setStatus(Integer status) {
    	this.status = status;
    }

	public Long getStrategyId() {
    	return strategyId;
    }

	public void setStrategyId(Long strategyId) {
    	this.strategyId = strategyId;
    }
	@Transient
	public String getAppName() {
    	return appName;
    }

	public void setAppName(String appName) {
    	this.appName = appName;
    }

	public Long getFileId() {
    	return fileId;
    }

	public void setFileId(Long fileId) {
    	this.fileId = fileId;
    }

	public String getRelativePath() {
    	return relativePath;
    }

	public void setRelativePath(String relativePath) {
    	this.relativePath = relativePath;
    }
}
