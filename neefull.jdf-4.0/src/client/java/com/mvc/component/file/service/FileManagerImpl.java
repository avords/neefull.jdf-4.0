package com.mvc.component.file.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mvc.ProjectConstants;
import com.mvc.component.file.BaseFileManager;
import com.mvc.component.file.ExtendFileManager;
import com.mvc.component.file.FileManager;
import com.mvc.component.file.model.BaseFile;
import com.mvc.component.file.model.FileStrategy;
import com.mvc.framework.util.MimeUtils;
import com.mvc.framework.web.FrameworkFactory;
@Service("fileManager")
public class FileManagerImpl implements FileManager,ExtendFileManager {

	private static final int ONE_SECOND = 1000;

	private static final Logger LOGGER = Logger.getLogger(FileManagerImpl.class);

	//Forever
	private static final int DEFAULT_FILE_SAVE_DAYS = -1;

	private BaseFileManager baseFileManager;

	private BaseFileManager getBaseFileManager() {
		if(null==baseFileManager){
			baseFileManager = FrameworkFactory.getBaseFileManager();
		}
		return baseFileManager;
	}

	public boolean deleteFile(Long fileId) throws Exception {
		BaseFile baseFile = getBaseFileManager().getBaseFileByFileIdAndAppName(fileId, ProjectConstants.PROJECT_NAME);
		boolean result = false;
		if(baseFile.getStatus()==BaseFile.FILE_STATUS_OK){
			FileHandler fileHandler = FileHandlerFactory.getFileHandler(baseFile);
			if (fileHandler != null) {
				result = fileHandler.deleteFile(baseFile);
				getBaseFileManager().updateBaseFileStatus(baseFile);
			}
		}
		return result;
	}

	public byte[] getBytesOfFile(Long fileId) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		write(fileId, outputStream);
		byte[] contents = outputStream.toByteArray();
		return contents;
	}

	public void write(Long fileId, OutputStream outputStream) throws Exception {
		BaseFile baseFile = getBaseFileManager().getBaseFileByFileIdAndAppName(fileId,
				ProjectConstants.PROJECT_NAME);
		write(baseFile, outputStream);
	}

	public void write(BaseFile baseFile, OutputStream outputStream) throws Exception {
		boolean result = false;
		FileHandler fileHandler = FileHandlerFactory.getFileHandler(baseFile);
		if (fileHandler != null) {
			result = fileHandler.write(baseFile, outputStream);
		}
		if (!result) {
			LOGGER.error("Failed read file " + baseFile.getFileId());
		}
		outputStream.flush();
		outputStream.close();
	}

	public Long saveFile(File file,String fileName) throws Exception {
		return saveFile(file, fileName, DEFAULT_FILE_SAVE_DAYS);
	}

	public Long saveFile(File file) throws Exception {
		return saveFile(file, null, DEFAULT_FILE_SAVE_DAYS);
	}

	public Long saveFile(String fileName, InputStream inputStream) throws Exception {
		return saveFile(fileName, inputStream, DEFAULT_FILE_SAVE_DAYS);
	}

	public Long saveFile(File file, Integer saveDay) throws Exception {
	    return saveFile(file, null, saveDay);
    }

	
	public Long saveFile(File file, String fileName, Integer saveDay) throws Exception {
		InputStream inputStream = null;
		Long fileId = null;
		try {
			inputStream = new FileInputStream(file);
			fileName = null==fileName?file.getName():fileName;
			fileId = saveFile(fileName, inputStream, saveDay);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (file != null) {
				file.delete();
			}
		}
		return fileId;
	}

	public Long saveFile(String fileName, InputStream inputStream, Integer saveDay) throws Exception {
		if (null==saveDay || saveDay <= 0) {
			saveDay = DEFAULT_FILE_SAVE_DAYS;
		}
		BaseFile baseFile = new BaseFile();
		baseFile.setAppName(ProjectConstants.PROJECT_NAME);
		baseFile.setFileSize(inputStream.available());
		baseFile.setName(fileName);
		baseFile.setStatus(BaseFile.FILE_STATUS_ERROR);
		baseFile.setSaveDay(saveDay);
		baseFile.setCreateDate(new Date());
		baseFile.setFileId(getBaseFileManager().getNewFileId());
		getBaseFileManager().saveBaseFile(baseFile);
		FileHandler fileHander = FileHandlerFactory.getFileHandler(baseFile);
		Long fileId = null;
		if (fileHander != null) {
			FileStrategy fileStrategy = baseFile.getFileStrategy();
			if (FileStrategy.COMPRESS_ZIP == fileStrategy.getCompressType()) {
				inputStream = FileCompressUtils.handleZip(inputStream, baseFile);
			} else if (FileStrategy.COMPRESS_RAR == fileStrategy.getCompressType()) {
				inputStream = FileCompressUtils.handleRar(inputStream, baseFile);
			}
			boolean result = fileHander.saveFile(inputStream, baseFile);
			if (result) {
				baseFile.setStatus(BaseFile.FILE_STATUS_OK);
				getBaseFileManager().updateBaseFileStatus(baseFile);
				fileId = baseFile.getFileId();
			}
		}
		return fileId;
	}

	public Integer checkFile(Long fileId) throws Exception {
		BaseFile baseFile = getBaseFileManager().getBaseFileByFileIdAndAppName(fileId, ProjectConstants.PROJECT_NAME);
		int result = BaseFile.FILE_STATUS_DELETE;
		FileHandler fileHandler = FileHandlerFactory.getFileHandler(baseFile);
		if (fileHandler != null) {
			result = fileHandler.isFileExists(baseFile);
		}
		return result;
	}

	public void download(Long fileId, HttpServletResponse response) throws Exception {
		BaseFile baseFile = getBaseFileManager().getBaseFileByFileIdAndAppName(fileId, ProjectConstants.PROJECT_NAME);
		if(baseFile!=null&&baseFile.getStatus()==BaseFile.FILE_STATUS_OK){
			setDownloadResponseHeaders(response, baseFile.getName());
			ServletOutputStream outputStream = response.getOutputStream();
			write(baseFile, outputStream);
		}else{
			LOGGER.error("File " + fileId + " does not exist or be deleted.");
		}
	}

	private void setDownloadResponseHeaders(HttpServletResponse response, String exportFileName)
			throws UnsupportedEncodingException {
		response.setCharacterEncoding("UTF-8"); 
		exportFileName = URLEncoder.encode(exportFileName, "UTF-8"); 
		String mimeType = MimeUtils.getFileMimeType(exportFileName);
		if (StringUtils.isNotBlank(mimeType)) {
			response.setContentType(mimeType);
		} else {
			LOGGER.error("Unknown file type : " + exportFileName);
			mimeType = MimeUtils.getFileMimeType("txt.txt");
			response.setContentType(mimeType);
		}
		response.setHeader("Content-Disposition", "attachment;filename=\"" + exportFileName + "\"");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setDateHeader("Expires", (System.currentTimeMillis() + ONE_SECOND));
	}

	public void saveMedia(String relativePath, byte[] content, String appName) throws Exception {
		FileStrategy fileStrategy = getBaseFileManager().getFileStrategyByAppName(appName);
		BaseFile baseFile = new BaseFile();
		baseFile.setAppName(appName);
		baseFile.setFileStrategy(fileStrategy);
		baseFile.setRelativePath(relativePath);
		FileHandler fileHander = FileHandlerFactory.getFileHandler(baseFile);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
		fileHander.saveFile(inputStream, baseFile);
    }

	public void writeMedia(String relativePath,OutputStream outputStream,String appName) throws Exception{
		FileStrategy fileStrategy = getBaseFileManager().getFileStrategyByAppName(appName);
		BaseFile baseFile = new BaseFile();
		baseFile.setAppName(appName);
		baseFile.setFileStrategy(fileStrategy);
		baseFile.setRelativePath(relativePath);
		FileHandler fileHander = FileHandlerFactory.getFileHandler(baseFile);
		if(fileHander!=null){
			fileHander.write(baseFile, outputStream);
		}
	}

	public void deleteMedia(String relativePath, String appName) throws Exception {
		FileStrategy fileStrategy = getBaseFileManager().getFileStrategyByAppName(appName);
		BaseFile baseFile = new BaseFile();
		baseFile.setAppName(appName);
		baseFile.setFileStrategy(fileStrategy);
		baseFile.setRelativePath(relativePath);
		FileHandler fileHander = FileHandlerFactory.getFileHandler(baseFile);
		fileHander.deleteFile(baseFile);
    }

}
