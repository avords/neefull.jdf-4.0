package com.mvc.component.file.service;

import java.io.InputStream;
import java.io.OutputStream;

import com.mvc.component.file.model.BaseFile;

/**
 * File handler
 * @author pubx 2010-3-31 04:43:54
 */

public interface FileHandler {

	boolean deleteFile(BaseFile baseFile) throws Exception;

	boolean saveFile(InputStream inputStream, BaseFile baseFile) throws Exception;

	int isFileExists(BaseFile baseFile)throws Exception;

	boolean write(BaseFile baseFile, OutputStream outputStream) throws Exception;

    boolean checkFile(BaseFile baseFile, String basePath);
}
