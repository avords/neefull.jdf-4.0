package com.mvc.component.file;

import com.mvc.component.file.model.BaseFile;
import com.mvc.component.file.model.FileStrategy;

/**
 * Base File Manager 
 *
 * @author pubx 2010-4-1 03:12:04
 */
public interface BaseFileManager {
	/**
	 * save
	 * @param baseFile
	 * @return
	 */
	BaseFile saveBaseFile(BaseFile baseFile);

	/**
	 * Get by application name and file ID
	 * @param fileId
	 * @param appName
	 */
	BaseFile getBaseFileByFileIdAndAppName(Long fileId, String appName);

	/**
	 * Update file status
	 * @param baseFile
	 */
	void updateBaseFileStatus(BaseFile baseFile);

	/**
	 * Update file name
	 *
	 * @param baseFile
	 */
	void updateFileName(BaseFile baseFile);

	/**
	 * Delete File info from database
	 * @param fileId
	 */
	void deleteBaseFile(Long fileId);

	/**
	 * Get the application file strategy
	 * Return null if not found
	 * @param appName
	 * @return
	 */
	FileStrategy getFileStrategyByAppName(String appName);

	Long getNewFileId();
}
