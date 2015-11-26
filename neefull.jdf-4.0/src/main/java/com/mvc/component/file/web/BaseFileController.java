package com.mvc.component.file.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.component.file.FileManager;
import com.mvc.component.file.model.BaseFile;
import com.mvc.component.file.service.BaseFileManagerImpl;
import com.mvc.framework.service.PageManager;
import com.mvc.framework.util.FileUpDownUtils;
import com.mvc.framework.util.UploadFile;
import com.mvc.framework.web.FrameworkFactory;
import com.mvc.framework.web.PageController;

@Controller
@RequestMapping("/baseFile")
public class BaseFileController extends PageController<BaseFile> {
	private static final String BASE_DIR = "framework/";
	@Autowired
	private BaseFileManagerImpl baseFileManager;

	@RequestMapping("/uploadAndDownload")
	public String uploadAndDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UploadFile uploadFile = FileUpDownUtils.getUploadFile(request, "uploadFile");
		FileManager fileManager = FrameworkFactory.getFileManager();
		Long fileId = fileManager.saveFile(uploadFile.getFile(),uploadFile.getFileName());
		fileManager.download(fileId, response);
		return null;
	}

	@RequestMapping("/download/{fileId}")
	public String download(HttpServletRequest request, HttpServletResponse response,@PathVariable Long fileId) throws Exception {
		FileManager fileManager = FrameworkFactory.getFileManager();
		fileManager.download(fileId, response);
		return null;
	}

	@Override
	public PageManager getEntityManager() {
		return baseFileManager;
	}

	@Override
	public String getFileBasePath() {
		return BASE_DIR;
	}
}
