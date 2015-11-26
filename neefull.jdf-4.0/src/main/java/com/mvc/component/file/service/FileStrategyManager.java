package com.mvc.component.file.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mvc.component.file.model.FileStrategy;
import com.mvc.framework.service.BaseService;

@Service
public class FileStrategyManager extends BaseService<FileStrategy, Long> {

	private static final Logger LOGGER = Logger.getLogger(FileStrategyManager.class);

	public FileStrategy getFileStrategyByAppName(String appName){
		String sql = "select A from " + FileStrategy.class.getName() + " A where appName=?";
		List<FileStrategy> list =  searchBySql(sql, new Object[] { appName});
		if(list.size()>0){
			return list.get(0);
		}else{
			LOGGER.error("Not found file strategy for application:" + appName);
			return null;
		}
	}
}
