package com.mvc.report.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mvc.framework.service.BaseIbatisService;
import com.mvc.report.ReportDataManager;

@Service
public class IbatisReportDataManager extends BaseIbatisService implements ReportDataManager {

	public List getListData(String namedSql, LinkedHashMap parameters) {
		return getSqlMapClientTemplate().queryForList(namedSql, parameters);
    }

	public Object getData(String namedSql, LinkedHashMap parameters) {
		return getSqlMapClientTemplate().queryForObject(namedSql, parameters);
    }
}
