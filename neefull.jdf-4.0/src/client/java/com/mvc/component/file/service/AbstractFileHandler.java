/*
 * AbstractFileHandler.java 2009-12-4
 * Copyright(c) 2007-2010 by broadtext
 * ALL Rights Reserved.
 */
package com.mvc.component.file.service;

import java.io.File;

import com.mvc.component.file.model.BaseFile;
import com.mvc.component.file.model.FileStrategy;

/**
 * Abstract file handler
 *
 * @author pubx 2009-12-4 03:15:58
 */
public abstract class AbstractFileHandler implements FileHandler {

	
	public String getFileSavePath(BaseFile baseFile) {
		FileStrategy fileStrategy = baseFile.getFileStrategy();
		String rootPath = fileStrategy.getStoragePath();
		rootPath = FilePathUtil.checkFilePath(rootPath);
		switch (fileStrategy.getCatalogType()) {
		case FileStrategy.CATALOG_ROOT:
			break;
		case FileStrategy.CATALOG_YEARLY:
			rootPath = rootPath + FilePathUtil.getYearPath(baseFile.getFileId());
			break;
		case FileStrategy.CATALOG_MONTHLY:
			rootPath = rootPath + FilePathUtil.getMonthPath(baseFile.getFileId());
			break;
		case FileStrategy.CATALOG_DAILY:
			rootPath = rootPath + FilePathUtil.getDayPath(baseFile.getFileId());
			break;
		default:
			break;
		}
		return rootPath;
	}

	public String getFileRealPath(BaseFile baseFile) {
		FileStrategy fileStrategy = baseFile.getFileStrategy();
		String basePath = fileStrategy.getStoragePath();
		basePath = FilePathUtil.checkFilePath(basePath);
		String fileRealPath = null;
		if(null!=baseFile.getRelativePath()){
			fileRealPath = basePath + baseFile.getRelativePath();
		}else{
			switch (fileStrategy.getCatalogType()) {
			case FileStrategy.CATALOG_ROOT:
				//if (checkFile(baseFile, basePath)){
					fileRealPath = basePath;
				//}
				break;
			case FileStrategy.CATALOG_YEARLY:
				fileRealPath = getYearlyCatalog(baseFile, basePath);
				break;
			case FileStrategy.CATALOG_MONTHLY:
				fileRealPath = getMonthlyCatalog(baseFile, basePath);
				break;
			case FileStrategy.CATALOG_DAILY:
				fileRealPath = getDailyDatalog(baseFile, basePath);
				break;
			case FileStrategy.CATALOG_YMD:
				fileRealPath = getYMDCatalog(baseFile, basePath);
				break;
			default:
				break;
			}
			fileRealPath += baseFile.getFileId();
		}
		return fileRealPath;
	}

	private String getDailyDatalog(BaseFile baseFile, String basePath) {
	    return basePath + FilePathUtil.getDayPath(baseFile.getFileId()) + File.separator;
    }

	private String getMonthlyCatalog(BaseFile baseFile, String basePath) {
	    return basePath + FilePathUtil.getMonthPath(baseFile.getFileId()) + File.separator;
    }

	private String getYearlyCatalog(BaseFile baseFile, String basePath) {
	    return basePath + FilePathUtil.getYearPath(baseFile.getFileId()) + File.separator;
    }

	private String getYMDCatalog(BaseFile baseFile, String basePath) {
	    return basePath + FilePathUtil.getYMDPath(baseFile.getFileId()) + File.separator;
    }

}
