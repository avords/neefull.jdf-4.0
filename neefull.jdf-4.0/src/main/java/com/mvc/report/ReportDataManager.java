package com.mvc.report;

import java.util.LinkedHashMap;
import java.util.List;

public interface ReportDataManager {
	
	List getListData(String namedSql,LinkedHashMap parameters);

	Object getData(String namedSql, LinkedHashMap parameters);
}
