package com.mvc.framework.db;


/**
 * @author pubx
 */
public class OracleDialect extends AbstractDialect {

	public String getLimitString(String sql, int skipResults, int maxResults) {
		StringBuilder result = new StringBuilder(sql.length() + 100);
		result.append("select * from ( select row_.*, rownum rownum_ from ( ");
		result.append(sql);
		result.append(" ) row_ where rownum <= ");
		result.append(maxResults);
		result.append(") where rownum_ > ");
		result.append(skipResults);
		return result.toString();
	}

	public String getCountSqlString(String sql) {
		sql = DialectUtils.trim(sql);
		StringBuilder sb = new StringBuilder(sql.length());
		sb.append("SELECT COUNT(*) AS ");
		sb.append(RS_NUMBER);
		sb.append(" FROM (");
		sb.append(sql);
		sb.append(")");
		return sb.toString();
	}
}
