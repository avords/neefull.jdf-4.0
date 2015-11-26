package com.mvc.component.file.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.log4j.Logger;

public class Ftp {
	private static final Logger LOGGER = Logger.getLogger(Ftp.class);
	private String ftpIp;
	private int ftpPort;
	private String ftpUserName;
	private String ftpPassword;

	private FTPClient ftpClient;

	public Ftp(String ip, int port, String userName, String password) {
		this.ftpIp = ip;
		this.ftpPort = port;
		this.ftpUserName = userName;
		this.ftpPassword = password;
	}

	private void connect() throws Exception {
		ftpClient = new FTPClient();
		ftpClient.setControlEncoding("GBK");
		FTPClientConfig ftpClientConf = new FTPClientConfig(FTPClientConfig.SYST_NT);
		ftpClientConf.setServerLanguageCode("zh");
		ftpClient.configure(ftpClientConf);

		ftpClient.connect(ftpIp, ftpPort);
		ftpClient.login(ftpUserName, ftpPassword);

		ftpClient.type(1);
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	}

	private void setUploadPath(String path) throws IOException {
		if (path != null && !"".equals(path)) {
			mkdirs(path);
		}
	}

	private void logout() throws Exception {
		if (ftpClient != null) {
			ftpClient.logout();
			ftpClient.disconnect();
		}
	}

	private void doUpload(String fileName, InputStream inputStream) throws Exception {
		boolean ok = ftpClient.storeFile(fileName, inputStream);
		if (!ok) {
			LOGGER.error(fileName + "FTP上传失败.");
		}
		if (inputStream != null) {
			inputStream.close();
		}
	}

	public boolean uploadSingle(String path, String fileName, InputStream inputStream) {
		boolean result = false;
		try {
			connect();
			setUploadPath(path);
			doUpload(fileName, inputStream);
			logout();
			result = true;
		} catch (Exception e) {
		}
		return result;
	}

	public void uploadBatch(String path, String[] fileNames, InputStream[] inputStreams) throws Exception {
		connect();
		setUploadPath(path);
		for (int i = 0; i < fileNames.length; i++) {
			doUpload(fileNames[i], inputStreams[i]);
		}
		logout();
	}

	public boolean downloadSingle(String path, String fileName, OutputStream outputStream) throws Exception {
		connect();
		setUploadPath(path);
		boolean result = ftpClient.retrieveFile(fileName, outputStream);
		logout();
		return result;
	}

	public boolean deleteSingle(String path, String fileName) throws Exception {
		connect();
		setUploadPath(path);
		boolean result = ftpClient.deleteFile(fileName);
		logout();
		return result;
	}

	public boolean isExist(String path, String fileId) throws Exception {
		connect();
		setUploadPath(path);
		ftpClient.printWorkingDirectory();
		String[] files = ftpClient.listNames();
		boolean result = false;
		if (files.length != 0) {
			for (int i = 0; i < files.length; i++) {
				if (fileId.equals(files[i])) {
					result = true;
				}
			}
		}
		logout();
		return result;
	}

	/**
	 * @param path
	 * @throws IOException
	 */
	private void mkdirs(String path) throws IOException {
		path = path.replace("\\", "/");
		String[] uploadPaths = path.split("/");
		for (int i = 0; i < uploadPaths.length; i++) {
			if (uploadPaths[i] != null){
				if (!ftpClient.changeWorkingDirectory(uploadPaths[i])) {
					ftpClient.makeDirectory(uploadPaths[i]);
					ftpClient.changeWorkingDirectory(uploadPaths[i]);
				}
			}

		}
	}

}
