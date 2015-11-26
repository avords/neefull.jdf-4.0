package com.mvc.component.file.service;

import java.io.InputStream;
import java.io.OutputStream;

import com.mvc.component.file.model.BaseFile;

public class DbFileHandler extends AbstractFileHandler {

	public boolean deleteFile(BaseFile baseFile) throws Exception {
		return false;
	}

	public boolean saveFile(InputStream inputStream, BaseFile baseFile) throws Exception {
		return false;
	}

	public int isFileExists(BaseFile baseFile) throws Exception {
		return 0;
	}

	public boolean write(BaseFile baseFile, OutputStream outputStream) throws Exception {
		return false;
	}

	public boolean checkFile(BaseFile baseFile, String basePath) {
		return false;
	}
}
