package com.mvc.portal.service;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.mvc.framework.service.BaseService;
import com.mvc.security.model.LoginLog;

@Service
public class LoginLogDataManager extends BaseService<LoginLog, Long> {

	private static final Logger LOGGER = Logger.getLogger(LoginLogDataManager.class);
	private String dialect;

	private void setDialect(){
		if(dialect==null){
			Session session = getSession();
			try{
				dialect = PropertyUtils.getProperty(session.getSessionFactory(), "dialect").toString();
			}catch (Exception e){
				dialect = "";
			}
		}
	}

	public void run() {
		Session session = getSession();
		setDialect();
		String opTime = "";
		String isNull = "";
		if(dialect.indexOf("MySQL")!=-1){
			opTime = "date_format(b.begin_Date,'%Y%m%d')";
			isNull = "ifnull";
			
		}else if (dialect.indexOf("oracle")!=-1){
			opTime = "to_char(b.begin_Date,'yyyy-mm-dd')";
			isNull = "nvl";
		}else if (dialect.indexOf("SQLServer")!=-1){
			opTime = "CONVERT(varchar(100), b.begin_Date, 112)";
			isNull = "ifnull";
		}
		Integer insertLine = 0;
		Integer updateLine = 0;
		if(isNull.length()>1){
			String insert = "insert into login_log_yyyymmdd(op_time,  user_id, online_time,  spend_time,  logout_from,  begin_date,  end_date) select " + opTime + ",b.user_id," + isNull + "(b.online_time,0)," + isNull + "(b.spend_time,0)," + isNull+ "(b.logout_form,0),b.begin_date,b.end_date  from f_login_log b where b.status = 2 and b.user_id is not null;";
			insertLine = session.createSQLQuery(insert).executeUpdate();
			String update = "update f_login_log a set status = 3 where status = 2 and exists(select 1 from login_log_yyyymmdd b where b.user_id = a.user_id and b.begin_date = a.begin_date);";
			updateLine = session.createSQLQuery(update).executeUpdate();
		}
		LOGGER.debug("Login log ETL executed(new:" + insertLine + ",update:" + updateLine + ")");
	}
}
