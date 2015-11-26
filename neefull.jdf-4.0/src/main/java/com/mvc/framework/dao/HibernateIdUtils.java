package com.mvc.framework.dao;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Entity;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.DefaultNamingStrategy;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.cfg.ObjectNameNormalizer;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.id.enhanced.TableGenerator;
import org.hibernate.type.LongType;
/**
 * Utility for generating ID
 */
public final class HibernateIdUtils {
	private static final String UN_CACHE_CUSTOMISE_ID_PREFIX = "U_C_C_ID_";
	/**
	 * Cached object ID generator
	 */
	private static final ConcurrentHashMap<String, TableGenerator> OBJECT_ID_GENERATORS = new ConcurrentHashMap<String, TableGenerator>();
	
	private HibernateIdUtils(){
	}
	/**
	 * Each table has a key(Table name + ".objectId")
	 * Cache size is 50
	 * @param sessionFactory
	 * @param entity
	 * @return
	 */
	public static Long getObjectId(SessionFactory sessionFactory,Class entity){
		if(sessionFactory instanceof SessionFactoryImplementor){
			SessionFactoryImplementor sessionFactoryImplementor = (SessionFactoryImplementor)sessionFactory;
			Entity entityClass = (Entity)entity.getAnnotation(Entity.class);
			String keyValue = null;
			//Nuknown object,may be will not occur
	    	if(entityClass==null){
	    		keyValue = "UnknownObject_objectId";
	    	}else {
	    		keyValue = entityClass.name() +".objectId";
	    	}
	    	TableGenerator objectIdGenerator = getObjectIdGenerator(sessionFactoryImplementor, keyValue);
			SessionImplementor session = (SessionImplementor)sessionFactoryImplementor.getCurrentSession();
			return (Long)objectIdGenerator.generate(session, entity);
		}else{
			throw new RuntimeException("System Error: the core api of hibernate changed");
		}
	}
	
	/**
	 * Generate uncached customize ID
	 * @param sessionFactory
	 * @param customizeIdKey the unique ID name,such as "OrderNo"
	 * @return
	 */
	public static Long getUnCacheCustomizeId(SessionFactory sessionFactory,String customizeIdKey){
		if(sessionFactory instanceof SessionFactoryImplementor){
			SessionFactoryImplementor sessionFactoryImplementor = (SessionFactoryImplementor)sessionFactory;
			String keyValue = UN_CACHE_CUSTOMISE_ID_PREFIX + customizeIdKey;
			TableGenerator objectIdGenerator = getCustomizeIdGenerator(sessionFactoryImplementor, keyValue);
			SessionImplementor session = (SessionImplementor)sessionFactoryImplementor.getCurrentSession();
			return (Long)objectIdGenerator.generate(session, null);
		}else{
			throw new RuntimeException("System Error: the core api of hibernate changed");
		}
	}
	
	private static TableGenerator getCustomizeIdGenerator(
            SessionFactoryImplementor sessionFactoryImplementor, String keyValue) {
		TableGenerator objectIdGenerator = OBJECT_ID_GENERATORS.get(keyValue);
	    if(objectIdGenerator==null){
	    	objectIdGenerator =  new TableGenerator();
	    	Properties properties = new Properties();
	    	properties.setProperty("increment_size","0");
	    	properties.setProperty("segment_column_name","sequence_name");
	    	properties.setProperty("segment_value",keyValue);
	    	properties.setProperty("value_column_name","sequence_next_hi_value");
	    	properties.setProperty("table_name","id_sequences");
	    	properties.put(
	    	        PersistentIdentifierGenerator.IDENTIFIER_NORMALIZER, 
	    	        new ObjectNameNormalizer() { 
	    	            @Override
	    	            protected boolean isUseQuotedIdentifiersGlobally() { 
	    	                return false; 
	    	            }

	    				@Override
	                    protected NamingStrategy getNamingStrategy() {
	                        return DefaultNamingStrategy.INSTANCE;
	                    }
	    	        }
	    	);
	    	objectIdGenerator.configure(new LongType(), properties, sessionFactoryImplementor.getDialect());
	    	OBJECT_ID_GENERATORS.put(keyValue, objectIdGenerator);
	    }
	    return objectIdGenerator;
    }
	
	
	private static TableGenerator getObjectIdGenerator(
            SessionFactoryImplementor sessionFactoryImplementor, String keyValue) {
		TableGenerator objectIdGenerator = OBJECT_ID_GENERATORS.get(keyValue);
	    if(objectIdGenerator==null){
	    	objectIdGenerator =  new TableGenerator();
	    	Properties properties = new Properties();
	    	properties.setProperty("increment_size","0");
	    	properties.setProperty("segment_column_name","sequence_name");
	    	properties.setProperty("segment_value",keyValue);
	    	properties.setProperty("value_column_name","sequence_next_hi_value");
	    	properties.setProperty("table_name","id_sequences");
	    	properties.put(
	    	        PersistentIdentifierGenerator.IDENTIFIER_NORMALIZER, 
	    	        new ObjectNameNormalizer() { 
	    	            @Override
	    	            protected boolean isUseQuotedIdentifiersGlobally() { 
	    	                return false; 
	    	            }

	    				@Override
	                    protected NamingStrategy getNamingStrategy() {
	                        return DefaultNamingStrategy.INSTANCE;
	                    }
	    	        }
	    	);
	    	objectIdGenerator.configure(new LongType(), properties, sessionFactoryImplementor.getDialect());
	    	OBJECT_ID_GENERATORS.put(keyValue, objectIdGenerator);
	    }
	    return objectIdGenerator;
    }
	
	private static final String ID_NAME = "ID";

	public static Long getSequence(Session session, String sequenceName) {
		SQLQuery query = session.createSQLQuery("SELECT " + sequenceName + ".NEXTVAL " + ID_NAME + " FROM DUAL");
		query.addScalar(ID_NAME, new org.hibernate.type.IntegerType());
		List children = query.list();
		return ((Number) children.iterator().next()).longValue();
	}
}
