package com.mvc.component.file.service;

import java.io.InputStream;
import java.io.OutputStream;

import com.mvc.component.file.model.BaseFile;
import com.mvc.component.file.model.FileStrategy;

/**
 * FTP file handler
 * @author
 */
public final class FtpFileHandler extends AbstractFileHandler {

	public boolean deleteFile(BaseFile baseFile) throws Exception {
		boolean result = false;
		baseFile.setStatus(BaseFile.FILE_STATUS_DEL_FAIL);
		FileStrategy fileStrategy = baseFile.getFileStrategy();
		String fileRealBasePath = getFileRealPath(baseFile);
		if (!FilePathUtil.isBlank(fileRealBasePath)) {
			Ftp ftp = new Ftp(fileStrategy.getFtpIp(), fileStrategy.getFtpPort(), fileStrategy.getFtpUser(),
					fileStrategy.getFtpPassword());
			if (ftp.deleteSingle(fileRealBasePath, String.valueOf(baseFile.getFileId()))) {
				baseFile.setStatus(BaseFile.FILE_STATUS_DELETE);
				result = true;
			}
		}
		return result;
	}

	public int isFileExists(BaseFile baseFile) throws Exception {
		int result = BaseFile.FILE_STATUS_DELETE;
		String fileRealBasePath = getFileRealPath(baseFile);
		if (FilePathUtil.isBlank(fileRealBasePath)) {
			result = BaseFile.FILE_STATUS_NOT_EXITS;
		} else {
			result = BaseFile.FILE_STATUS_OK;
		}
		return result;
	}

	public boolean saveFile(InputStream inputStream, BaseFile baseFile) throws Exception {
		FileStrategy fileStrategy = baseFile.getFileStrategy();
		String fileRealBasePath = null;
		String fileName = null;
		if(null!=baseFile.getRelativePath()){
			fileRealBasePath = fileStrategy.getStoragePath() + "/" + baseFile.getRelativePath();
			fileRealBasePath = fileRealBasePath.replace("\\", "/");
			String[] a = fileRealBasePath.split("/");
			fileName = a[a.length-1];
			fileRealBasePath = fileRealBasePath.substring(0,fileRealBasePath.length()-fileName.length()-1);
		}else{
			fileRealBasePath = getFileSavePath(baseFile);
			fileName = String.valueOf(baseFile.getFileId());
		}
		Ftp ftp = new Ftp(fileStrategy.getFtpIp(), fileStrategy.getFtpPort(), fileStrategy.getFtpUser(),fileStrategy.getFtpPassword());
		if (ftp.uploadSingle(fileRealBasePath, fileName, inputStream)) {
			baseFile.setStatus(BaseFile.FILE_STATUS_OK);
			return true;
		}
		return false;
	}

	public boolean write(BaseFile baseFile, OutputStream outputStream) throws Exception {
		FileStrategy fileStrategy = baseFile.getFileStrategy();
		boolean result = false;
		String fileRealBasePath = getFileRealPath(baseFile);
		if (!FilePathUtil.isBlank(fileRealBasePath)) {
			Ftp ftp = new Ftp(fileStrategy.getFtpIp(), fileStrategy.getFtpPort(), fileStrategy.getFtpUser(),
					fileStrategy.getFtpPassword());
			if (ftp.downloadSingle(fileRealBasePath, String.valueOf(baseFile.getFileId()), outputStream)) {
				result = true;
			}
		}
		return result;
	}

	public boolean checkFile(BaseFile baseFile, String basePath) {
		FileStrategy fileStrategy = baseFile.getFileStrategy();
		Ftp ftp = new Ftp(fileStrategy.getFtpIp(), fileStrategy.getFtpPort(), fileStrategy.getFtpUser(),
				fileStrategy.getFtpPassword());
		try {
			if (ftp.isExist(basePath, String.valueOf(baseFile.getFileId()))) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
}

