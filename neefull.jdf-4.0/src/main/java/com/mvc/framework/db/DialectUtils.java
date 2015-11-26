package com.mvc.framework.db;

public final class DialectUtils {
	private DialectUtils(){
	}
	public static final String trim(String sql) {
		sql = sql.trim();
		if (sql.endsWith(Dialect.SQL_END_DELIMITER)) {
			sql = sql.substring(0, sql.length() - 1
					- Dialect.SQL_END_DELIMITER.length());
		}
		return sql;
	}
}
