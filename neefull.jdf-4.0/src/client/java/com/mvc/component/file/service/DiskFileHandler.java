package com.mvc.component.file.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.mvc.component.file.model.BaseFile;

public class DiskFileHandler extends AbstractFileHandler {
	private static final Logger LOGGER = Logger.getLogger(DiskFileHandler.class);
	private static final int DEFAULT_BUFFER_SIZE = 2048;

	public boolean deleteFile(BaseFile baseFile) throws Exception {
		boolean result = false;
		baseFile.setStatus(BaseFile.FILE_STATUS_DEL_FAIL);
		String absolutePathName = getFileRealPath(baseFile);
		if (!FilePathUtil.isBlank(absolutePathName)) {
			File file = new File(absolutePathName);
			if (file.exists() && file.delete()) {
				baseFile.setStatus(BaseFile.FILE_STATUS_DELETE);
				result = true;
			}
		}
		return result;
	}

	public int isFileExists(BaseFile baseFile) throws Exception {
		int result = BaseFile.FILE_STATUS_DELETE;
		String absolutePathName = getFileRealPath(baseFile);
		if (FilePathUtil.isBlank(absolutePathName)) {
			result = BaseFile.FILE_STATUS_NOT_EXITS;
		} else {
			result = BaseFile.FILE_STATUS_OK;
		}
		return result;
	}

	public boolean saveFile(InputStream inputStream, BaseFile baseFile) throws Exception {
		return handleDisk(inputStream, baseFile);
	}

	public boolean write(BaseFile baseFile, OutputStream outputStream) throws Exception {
		boolean result = false;
		String absolutePathName = getFileRealPath(baseFile);
		if (!FilePathUtil.isBlank(absolutePathName)) {
			InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(absolutePathName);
				byte[] data = new byte[DEFAULT_BUFFER_SIZE];
				int len;
				while ((len = inputStream.read(data)) != -1) {
					outputStream.write(data, 0, len);
				}
				result = true;
			} catch (FileNotFoundException e) {
				LOGGER.error("write", e);
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}
			}
		}
		return result;
	}

	
	private boolean handleDisk(InputStream inputStream, BaseFile baseFile) throws Exception {
		// Make folder automatically
		String filePath = getFileRealPath(baseFile);
		File file = new File(filePath);
		if (!file.exists()) {
			File folder = new File(file.getParent());
			folder.mkdirs();
		}
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
			BufferedOutputStream buffOutputStream = new BufferedOutputStream(fileOutputStream);
			byte[] data = new byte[DEFAULT_BUFFER_SIZE];
			int len;
			while ((len = inputStream.read(data)) != -1) {
				buffOutputStream.write(data, 0, len);
			}
			buffOutputStream.close();
			baseFile.setStatus(BaseFile.FILE_STATUS_OK);
			return true;
		} catch (FileNotFoundException e) {
			LOGGER.error("write", e);
		} finally {
			if (fileOutputStream != null) {
				fileOutputStream.close();
			}
		}
		return false;
	}

	public boolean checkFile(BaseFile baseFile, String basePath) {
		File file = new File(basePath + baseFile.getFileId());
		if (file.exists()) {
			return true;
		}
		return false;
	}
}
