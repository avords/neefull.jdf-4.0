package com.mvc.framework.db;
/**
 *
 * @author	pubx <br>
 * @create	Jul 30, 2008 <br>
 */
public class DB2Dialect extends AbstractDialect {

	public String getCountSqlString(String sql) {
		sql = DialectUtils.trim(sql);
		StringBuilder sb = new StringBuilder(sql.length() + 50);
		sb.append("select count(*) as ");
		sb.append(RS_NUMBER);
		sb.append(" from (");
		sb.append(sql);
		sb.append(") t");
		return sb.toString();
	}

	public String getLimitString(String sql, int skipResult, int maxmResult) {
		int startOfSelect = sql.toLowerCase().indexOf("select");
		StringBuilder pagingSelect = new StringBuilder( sql.length() + 100 )
					.append( sql.substring(0, startOfSelect) )
					.append("select * from ( select ")
					.append( getRowNumber(sql) );
		pagingSelect.append(" row_.* from ( ").append( sql ).append(" ) as row_");

		pagingSelect.append(" ) as temp_ where rownumber_ ");
		if (skipResult>=0&&skipResult<=maxmResult) {
			pagingSelect.append("between " + (skipResult+1) + " and " + maxmResult);
		}
		return pagingSelect.toString();
	}

	private String getRowNumber(String sql) {
		StringBuilder rownumber = new StringBuilder(50)
			.append("rownumber() over(");

		int orderByIndex = sql.toLowerCase().indexOf("order by");

		if ( orderByIndex>0 && !hasDistinct(sql) ) {
			rownumber.append( sql.substring(orderByIndex) );
		}

		rownumber.append(") as rownumber_,");

		return rownumber.toString();
	}

	private static boolean hasDistinct(String sql) {
		return sql.toLowerCase().indexOf("select distinct")>=0;
	}

}
