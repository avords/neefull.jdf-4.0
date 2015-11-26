package com.mvc.component.file;

import java.io.OutputStream;


/**
 * Extend file manager
 * @author pubx
 *
 */
public interface ExtendFileManager {

	/**
	 * Save web page such as :html、mp3、mp4、jpg
	 * @param relativePath  file relative path including suffix,for example:news/20101101/index.html,upload/20101101/201001101111111.mp3
	 * @param content Encoding with UTF-8
	 * @param appName The application name
	 * @throws Exception 
	 */
	void saveMedia(String relativePath, byte[] content, String appName) throws Exception;

	/**
	 * Get file(Including html、mp3、mp4、jpg）
	 * @param relativePath file relative path including suffix,for example:news/20101101/index.html,upload/20101101/201001101111111.mp3
	 * @param outputStream 
	 * @param appName The application name
	 * @throws Exception 
	 */
	void writeMedia(String relativePath, OutputStream outputStream, String appName) throws Exception;

	/**
	 * Delete file
	 * @param relativePath file relative path including suffix,for example:news/20101101/index.html,upload/20101101/201001101111111.mp3
	 * @param appName The application name
	 * @throws Exception
	 */
	void deleteMedia(String relativePath, String appName) throws Exception;
}
