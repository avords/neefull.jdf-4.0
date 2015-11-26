package com.mvc.framework.db;

public class SQLServerDialect extends org.hibernate.dialect.SQLServerDialect implements Dialect{

	public String getCountSqlString(String sql) {
		sql = DialectUtils.trim(sql);
		StringBuilder sb = new StringBuilder(sql.length());
		sb.append("SELECT COUNT(*) AS ");
		sb.append(RS_NUMBER);
		sb.append(" FROM (");
		sb.append(sql);
		sb.append(") a");
		return sb.toString();
    }

}
