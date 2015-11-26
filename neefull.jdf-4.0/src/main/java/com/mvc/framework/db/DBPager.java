package com.mvc.framework.db;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


public final class DBPager {
	private static final Logger LOGGER = Logger.getLogger(DBPager.class);
	private static final int DEFAULT_PAGE_SIZE = 10000;
	public static final int DB_ERROR = -1;
	private int pageSize;
	private RowMapper rowMapper;
	private JdbcTemplate jdbcTemplate;
	private int currentPage = 1;
	private long total = DB_ERROR;
	private String querySql;
	private Dialect dialect;
	private boolean needMapper = false;
	
	public DBPager(DataSource ds, RowMapper rm, String sql,Dialect dialect) {
		this(ds, rm, sql, DEFAULT_PAGE_SIZE,dialect);
	}

	public DBPager(DataSource ds, RowMapper rm, String sql) throws Exception {
		this(ds, rm, sql, DEFAULT_PAGE_SIZE,new OracleDialect());
	}

	public DBPager(DataSource ds, RowMapper rm, String sql, int ps,Dialect dialect) {
		this.pageSize = ps;
		this.rowMapper = rm;
		this.querySql = sql;
		this.jdbcTemplate = new JdbcTemplate(ds);
		this.dialect = dialect;
		try{
			jdbcTemplate.setQueryTimeout(0);
			total = jdbcTemplate.queryForLong(dialect.getCountSqlString(sql));
		}catch (DataAccessException e) {
			total = DB_ERROR;
			LOGGER.error("DBPager",e);
			throw e;
		}
		if(null!=this.rowMapper){
			needMapper = true;
		}
	}

	public boolean isOk(){
		return total!=DB_ERROR;
	}
	
	public boolean hasNext() {
		return ((currentPage-1) * pageSize) <= total;
	}

	public List nextPage() throws Exception {
		String pageSql;
		if(total>=pageSize){
			pageSql = dialect.getLimitString(querySql, (currentPage - 1) * pageSize, currentPage*pageSize);
		}else{
			pageSql = querySql;
		}
		currentPage++;
		List result = null;
		if(needMapper){
			result = jdbcTemplate.query(pageSql, rowMapper);
		}else {
			result = jdbcTemplate.queryForList(pageSql);
		}
		return result;
	}

	public long saveWithHibernate(SessionFactory sessionFactory) throws Exception {
		long record = 1;
		StatelessSession session = sessionFactory.openStatelessSession();
		Transaction tx = session.beginTransaction();
		try{
			while(hasNext()){
				List list = nextPage();
				for(Object entity:list) {
					session.insert(entity);
					if(record%1000==0){
						tx.commit();
						tx = session.beginTransaction();
					}
					record++;
				}
			}
			tx.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return record;
	}

	public long getTotal(){
		return total;
	}
}
