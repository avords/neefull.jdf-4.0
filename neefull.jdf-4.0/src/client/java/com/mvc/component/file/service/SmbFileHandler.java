package com.mvc.component.file.service;

import java.io.InputStream;
import java.io.OutputStream;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

import com.mvc.component.file.model.BaseFile;
import com.mvc.component.file.model.FileStrategy;

public class SmbFileHandler extends AbstractFileHandler {

	public boolean deleteFile(BaseFile baseFile) throws Exception {
		FileStrategy fileStrategy = baseFile.getFileStrategy();
		String absolutePathName = getFileRealPath(baseFile);
		baseFile.setRelativePath(absolutePathName.replaceAll("\\\\", "/"));
		StringBuilder url = new StringBuilder(30);
		url.append("smb://").append(fileStrategy.getFtpUser()).append(":");
		url.append(fileStrategy.getFtpPassword()).append("@").append(fileStrategy.getFtpIp());
		url.append("/").append(absolutePathName);
		SmbFile shareDir = new SmbFile(url.toString().replaceAll("\\\\", "/"));
		shareDir.delete();
	    return true;
    }

	public boolean saveFile(InputStream inputStream, BaseFile baseFile) throws Exception {
		FileStrategy fileStrategy = baseFile.getFileStrategy();
		String absolutePathName = getFileRealPath(baseFile);
		baseFile.setRelativePath(absolutePathName.replaceAll("\\\\", "/"));
		StringBuilder url = new StringBuilder(30);
		url.append("smb://").append(fileStrategy.getFtpUser()).append(":");
		url.append(fileStrategy.getFtpPassword()).append("@").append(fileStrategy.getFtpIp());
		url.append("/").append(absolutePathName);
		SmbFile shareDir = new SmbFile(url.toString().replaceAll("\\\\", "/"));
		String parentPath = shareDir.getParent();
		SmbFile smbFile = new SmbFile(parentPath.substring(0,parentPath.length()-1));
		if (!smbFile.exists()) {
			smbFile.mkdirs();
		}
		if (!shareDir.exists()) {
			shareDir.createNewFile();
		}
		SmbFileOutputStream smbFileOutputStream = new SmbFileOutputStream( shareDir);
		byte[] data = new byte[1024];
		int len;
		while ((len = inputStream.read(data)) != -1) {
			smbFileOutputStream.write(data, 0, len);
		}
		smbFileOutputStream.close();
		inputStream.close();
	    return true;
    }

	public int isFileExists(BaseFile baseFile) throws Exception {
	    return 0;
    }

	public boolean write(BaseFile baseFile, OutputStream outputStream) throws Exception {

		FileStrategy fileStrategy = baseFile.getFileStrategy();
		StringBuilder url = new StringBuilder(30);
		url.append("smb://").append(fileStrategy.getFtpUser()).append(":");
		url.append(fileStrategy.getFtpPassword()).append("@").append(fileStrategy.getFtpIp());
		//统一文件入口
		if(null==baseFile.getRelativePath()){
			url.append("/").append(getFileRealPath(baseFile));
		}else{
			url.append("/").append(fileStrategy.getStoragePath()).append("/").append(baseFile.getRelativePath());
		}
		SmbFile shareDir = new SmbFile(url.toString().replaceAll("\\\\", "/"));
		SmbFileInputStream inputStream = new SmbFileInputStream( shareDir);
		byte[] data = new byte[1024];
		int len;
		while ((len = inputStream.read(data)) != -1) {
			outputStream.write(data, 0, len);
		}
		inputStream.close();
		outputStream.close();
	    return true;
    }

	public boolean checkFile(BaseFile baseFile, String basePath) {
	    return true;
    }

}
