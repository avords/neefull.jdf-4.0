package com.mvc.framework.db;
/**
 *
 * @author	pubx <br>
 * @create	Jul 29, 2008 <br>
 */
public class MySQLDialect extends AbstractDialect {

    public String getLimitString(String sql, int skipResult, int maxmResult){
        sql = DialectUtils.trim(sql);
        StringBuilder sb = new StringBuilder(sql.length() + 20);
        sb.append(sql);
        if (skipResult > 0) {
            sb.append(" limit ").append(skipResult).append(',').append(maxmResult).append(SQL_END_DELIMITER);
        } else {
            sb.append(" limit ").append(maxmResult).append(SQL_END_DELIMITER);
        }
        return sb.toString();
    }

    public String getCountSqlString(String sql){
        sql = DialectUtils.trim(sql);
        sql = sql.toLowerCase();
        StringBuilder sb = new StringBuilder(sql.length() + 10);
        if(sql.startsWith("select")){
            int i=sql.indexOf(" ");
            int j=sql.lastIndexOf("from");
            sb.append(sql.subSequence(0, i));
            sb.append(" ");
            sb.append("count(*) as ");
            sb.append(RS_NUMBER);
            sb.append(" ");
            sb.append(sql.subSequence(j, sql.length()));
        }
        return sb.toString();
    }
}
