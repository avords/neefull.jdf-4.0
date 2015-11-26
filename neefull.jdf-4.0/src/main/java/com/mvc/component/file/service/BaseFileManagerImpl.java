package com.mvc.component.file.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.component.file.BaseFileManager;
import com.mvc.component.file.model.BaseFile;
import com.mvc.component.file.model.FileStrategy;
import com.mvc.framework.config.GlobalConfig;
import com.mvc.framework.service.BaseService;

@Service("baseFileManager")
public class BaseFileManagerImpl extends BaseService<BaseFile, String> implements BaseFileManager {
	private static final Logger LOGGER = Logger.getLogger(BaseFileManagerImpl.class);
	private static final int RANDOM_BEGIN = 5;
	private static final int DERAULT_FILE_ID_LENGTH = 18;
	@Autowired
	private FileStrategyManager fileStrategyManager;

	public BaseFile getBaseFileByFileIdAndAppName(Long fileId, String appName) {
		BaseFile baseFile = null;
		if (null!=fileId && appName != null) {
			baseFile = getBaseFileByFileId(fileId);
			if(baseFile!=null){
				FileStrategy fileStrategy = fileStrategyManager.getByObjectId(baseFile.getStrategyId());
				//Portal can get all, But other application can only get their own
				if(GlobalConfig.getPortalServerName().equals(appName)||appName.equals(fileStrategy.getAppName())){
					baseFile.setFileStrategy(fileStrategy);
				}else{
					baseFile = null;
				}
			}
		}
		return baseFile;
	}

	public void updateBaseFileStatus(BaseFile baseFile) {
		super.save(baseFile);
	}

	public void deleteBaseFile(Long fileId) {
		BaseFile baseFile = new BaseFile();
		baseFile.setFileId(fileId);
		baseFile.setStatus(BaseFile.FILE_STATUS_DELETE);
		super.save(baseFile);
	}

	private BaseFile getBaseFileByFileId(Long fileId) {
		String sql = "SELECT A FROM  " + BaseFile.class.getName()+ " A WHERE A.fileId = " + fileId;
		List<BaseFile> list = searchBySql(sql);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public BaseFile saveBaseFile(BaseFile baseFile) {
		FileStrategy fileStrategy =fileStrategyManager.getFileStrategyByAppName(baseFile.getAppName());
		baseFile.setFileStrategy(fileStrategy);
		baseFile.setStrategyId(fileStrategy.getObjectId());
		super.save(baseFile);
		return baseFile;
	}
	private static volatile int count = 0;
	private static synchronized String getRandomStr() {
		count++;
		if(count>=100000){
			count = 0;
		}
		return String.format("%0" + RANDOM_BEGIN +"d",count);
	}

	public Long getNewFileId() {
		StringBuilder result = new StringBuilder(DERAULT_FILE_ID_LENGTH);
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		result.append(dateFormat.format(new Date()));
		result.append(getRandomStr());
		return Long.parseLong(result.toString());
	}

	public static void main(String[] args) {
		BaseFileManagerImpl baseFileManagerImpl = new BaseFileManagerImpl();
		for(int i=0;i<100;i++){
			System.out.println(getRandomStr());
		}
		System.out.println(getRandomStr());
    }

	public void updateFileName(BaseFile baseFile) {
		super.save(baseFile);
	}

	public FileStrategy getFileStrategyByAppName(String appName) {
	    return fileStrategyManager.getFileStrategyByAppName(appName);
    }

}
