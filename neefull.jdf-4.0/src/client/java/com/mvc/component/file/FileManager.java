package com.mvc.component.file;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.mvc.component.file.model.BaseFile;

/**
 * 
 * @author pubx 2010-3-31 01:57:00
 */
public interface FileManager {
	/**
	 * Delete fiel by file ID
	 * 
	 * @param fileId
	 * @return
	 * @throws Exception
	 */
	boolean deleteFile(Long fileId) throws Exception;

	/**
	 * Save file with specific file name
	 * 
	 * @param file
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	Long saveFile(File file, String fileName) throws Exception;

	/**
	 * Save file with default file name
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	Long saveFile(File file) throws Exception;

	/**
	 * Save file with specific file name and input stream
	 * 
	 * @param fileName
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	Long saveFile(String fileName, InputStream inputStream) throws Exception;

	/**
	 * Save file with specific saved day If save day is null,then will saved
	 * forever
	 * 
	 * @param file
	 * @param saveDay
	 * @return
	 * @throws Exception
	 */
	Long saveFile(File file, Integer saveDay) throws Exception;

	/**
	 * Save file with specific file name and saved day If save day is null,then
	 * will saved forever
	 * 
	 * @param file
	 * @param fileName
	 * @param saveDay
	 * @return
	 * @throws Exception
	 */
	Long saveFile(File file, String fileName, Integer saveDay) throws Exception;

	/**
	 * Save input stream with specific save day
	 * If save day is null,then will saved forever
	 * @param fileName
	 * @param inputStream
	 * @param saveDay
	 * @return
	 * @throws Exception
	 */
	Long saveFile(String fileName, InputStream inputStream, Integer saveDay) throws Exception;

	/**
	 * 取得文件字节数组，如文件较大，建议使用<code>write</code>和<code>download</code>方法
	 * 
	 * @param fileId
	 *            文件ID
	 * @return 文件字节数组
	 * @throws Exception
	 */
	
	/**
	 * Get byte array of a file
	 * If the file size is big,use<code>write</code> or <code>download</code>
	 * @see #write(Long, OutputStream)
	 * @See {@link #download(Long, HttpServletResponse)}
	 * @param fileId
	 * @return
	 * @throws Exception
	 */
	byte[] getBytesOfFile(Long fileId) throws Exception;

	void write(Long fileId, OutputStream outputStream) throws Exception;

	void write(BaseFile baseFile, OutputStream outputStream) throws Exception;

	void download(Long fileId, HttpServletResponse response) throws Exception;

	/**
	 * Check the file status:code>FileManager.FILE_STATUS_OK</code>,<code>FileManager.FILE_STATUS_NOT_EXITS</code>,<code>FileManager.FILE_STATUS_DELETE</code>
	 * @param fileId
	 * @return
	 * @throws Exception
	 */
	Integer checkFile(Long fileId) throws Exception;
}
