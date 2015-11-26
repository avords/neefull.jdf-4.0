package com.mvc.framework.db;

/**
 *
 * @author pubx <br>
 * @create Jul 28, 2008 <br>
 */
public interface Dialect {

	public static final String SQL_END_DELIMITER = ";";

	public static final String RS_NUMBER = "rs_number";

	public boolean supportsLimit();

	/**
	 * @param sql
	 * @param skipResult
	 * @param maxmResult
	 * @return page SQL
	 */
	public String getLimitString(String sql, int skipResult, int maxmResult);

	/**
	 * @param sql
	 * @return total SQL
	 */
	public String getCountSqlString(String sql);

}
