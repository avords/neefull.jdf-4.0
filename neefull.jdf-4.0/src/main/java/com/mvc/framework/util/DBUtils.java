package com.mvc.framework.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.mvc.framework.db.DB2Dialect;
import com.mvc.framework.db.Dialect;
import com.mvc.framework.db.OracleDialect;

public final class DBUtils {
	private DBUtils() {
	}
	private static final Logger logger = Logger.getLogger(DBUtils.class);
	public static final String ORACLE_TEST_SQL = "SELECT 1 FROM DUAL";
	public static final String DB2_TEST_SQL = "SELECT CURRENT DATE FROM SYSIBM.SYSDUMMY1";

	public static boolean testDataSource(DataSource dataSource, String testSql) {
		try {
			return testConnection(dataSource.getConnection(), testSql);
		} catch (SQLException e) {
			logger.error("testDataSource", e);
		}
		return false;

	}

	public static boolean testConnection(Connection connection, String testSql) {
		boolean result = false;
		try {
			Statement statement = connection.createStatement();
			statement.executeQuery(testSql);
			result = true;
		} catch (SQLException e) {
			logger.error("testConnection", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e2) {
				}
			}
		}
		return result;
	}

	public static Dialect getDialect(String jndiName){
		if(jndiName.indexOf("db2")!=-1){
			return new DB2Dialect();
		}else{
			return new OracleDialect();
		}
	}

}
