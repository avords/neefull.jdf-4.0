package com.mvc.component.file.service;

import org.apache.log4j.Logger;

import com.mvc.component.file.model.BaseFile;
import com.mvc.component.file.model.FileStrategy;

public final class FileHandlerFactory {

	private static final Logger LOGGER = Logger.getLogger(FileHandlerFactory.class);

	private FileHandlerFactory(){
	}

	public static FileHandler getFileHandler(BaseFile baseFile){
		FileHandler fileHandler = null;
		if(baseFile != null){
			FileStrategy fileStrategy = baseFile.getFileStrategy();
			if(fileStrategy != null){
				switch (fileStrategy.getStoreType()) {
                case FileStrategy.STORE_FTP:
                	fileHandler = new FtpFileHandler();
	                break;
                case FileStrategy.STORE_DISK:
                	fileHandler = new DiskFileHandler();
	                break;
                case FileStrategy.STORE_SMB:
                	fileHandler = new SmbFileHandler();
	                break;
                case FileStrategy.STORE_DB:
                	fileHandler = new DbFileHandler();
	                break;
                default:
                	LOGGER.error("Undefined file store type : " + fileStrategy.getStoreType());
	                break;
                }
			}else{
				LOGGER.error("Not found file strategy for application :" + baseFile.getAppName());
			}
		}
		return fileHandler;
	}

}
