package com.mvc.framework.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;

import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory;
import org.hibernate.hql.spi.QueryTranslator;
import org.hibernate.hql.spi.QueryTranslatorFactory;
import org.springframework.orm.hibernate4.SessionFactoryUtils;

public final class HibernateUtils {

	private HibernateUtils() {
	}

	public static String hqlToSql(SessionFactory sessionFactory, String hql) {
		if (sessionFactory != null && hql != null && hql.trim().length() > 0) {
			final QueryTranslatorFactory translatorFactory = new ASTQueryTranslatorFactory();
			final SessionFactoryImplementor factory = (SessionFactoryImplementor) sessionFactory;
			final QueryTranslator translator = translatorFactory.createQueryTranslator(hql, hql, Collections.EMPTY_MAP, factory);
			translator.compile(Collections.EMPTY_MAP, false);
			return translator.getSQLString();
		}
		return null;
	}
	
	public static Connection getConnection(SessionFactory sessionFactory) throws SQLException{
		return SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
	}
}
