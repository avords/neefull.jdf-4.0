package com.mvc.framework.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.mvc.framework.dao.BaseIbatisDao;


public class BaseIbatisService {
	@Autowired
	private BaseIbatisDao baseIbatisDao;

	private SqlMapClientTemplate sqlMapClientTemplate;

	@PostConstruct
	public void init() {
		sqlMapClientTemplate = baseIbatisDao.getSqlMapClientTemplate();
	}

	public SqlMapClientTemplate getSqlMapClientTemplate(){
		return sqlMapClientTemplate;
	}

	public void setBaseIbatisDao(BaseIbatisDao baseIbatisDao) {
    	this.baseIbatisDao = baseIbatisDao;
    	if(baseIbatisDao!=null){
    		sqlMapClientTemplate = baseIbatisDao.getSqlMapClientTemplate();
    	}
    }
}
