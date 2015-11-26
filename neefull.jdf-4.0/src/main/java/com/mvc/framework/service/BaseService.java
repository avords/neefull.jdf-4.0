package com.mvc.framework.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Id;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mvc.framework.dao.BaseHibernateDao;
import com.mvc.framework.dao.HibernateIdUtils;
import com.mvc.framework.dao.HibernateWebUtils;
import com.mvc.framework.dao.ReflectUtil;
import com.mvc.framework.util.PageSearch;
import com.mvc.framework.validate.FieldProperty;
import com.mvc.framework.validate.ValidateContainer;

/**
 * Basic Service Support CRUD, page query and common search function
 *
 * @author pubx 2010-3-29 02:26:46
 */
@Transactional(readOnly=true)
public abstract class BaseService<T, PK extends Serializable> implements PageManager<T> {
	private static final Logger LOGGER = Logger.getLogger(BaseService.class);

	@Autowired
	private BaseHibernateDao<T, Serializable> baseHibernateDao;

	private Class<T> actualArgumentType;

	public BaseService() {
		final Type thisType = getClass().getGenericSuperclass();
		final Type type;
		try{
			if (thisType instanceof ParameterizedType) {
				type = ((ParameterizedType) thisType).getActualTypeArguments()[0];
			} else if (thisType instanceof Class) {
				type = ((ParameterizedType) ((Class) thisType).getGenericSuperclass()).getActualTypeArguments()[0];
			} else {
				throw new IllegalArgumentException("Problem handling type construction for " + getClass());
			}
			if (type instanceof Class) {
				this.actualArgumentType = (Class<T>) type;
			} else if (type instanceof ParameterizedType) {
				this.actualArgumentType = (Class<T>) ((ParameterizedType) type).getRawType();
			} else {
				throw new IllegalArgumentException("Problem determining the class of the generic for " + getClass());
			}
		}catch(Exception e){
			LOGGER.warn(e.getMessage());
		}
		LOGGER.debug(getClass() + ":" + actualArgumentType);
	}

	protected Class getActualArgumentType() {
		return actualArgumentType;
	}

	@Override
	public void delete(Serializable id) {
		delete(getByObjectId(id));
	}

	@Override
	public void delete(T entity) {
		baseHibernateDao.delete(entity);
	}


	@Override
	public void save(T entity) {
		baseHibernateDao.save(entity);
	}

	@Override
	public void update(Serializable id){
		//Nothing to do
	}

	public Session getSession() {
		return baseHibernateDao.getSession();
	}

	/**
	 * Page query
	 */
	@Override
	public void find(final PageSearch page) {
		DetachedCriteria detachedCriteria = HibernateWebUtils.createCriteria(page);
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		if (0 == page.getTotalCount()) {
			criteria.setProjection(Projections.rowCount());
			Object object = criteria.uniqueResult();
			int total = object == null ? 0 : ((Number) object).intValue();
			page.setTotalCount(total);
		}
		addOrder(page, criteria);
		criteria.setProjection(null);
		page.setList(criteria.setFirstResult(page.getBegin()).setMaxResults(page.getPageSize()).list());
	}

	protected void addOrder(final PageSearch page, Criteria criteria) {
		if (page.getOrders() != null) {
			for (Order order : page.getOrders()) {
				criteria.addOrder(order);
			}
		}
	}

	@Override
	public Long createSystemId() {
		return createSystemId(getActualArgumentType());
	}

	public Long createSystemId(Class entity) {
		return HibernateIdUtils.getObjectId(getSession().getSessionFactory(), entity);
	}

	public List<T> getAll() {
		String sql = "select A from " + getActualArgumentType().getName() + " A";
		return searchBySql(sql);
	}

	public Long getTotalCount() {
		String sql = "select count(*) from " + getActualArgumentType().getName() + " A";
		Query query = getSession().createQuery(sql);
		return (Long) query.list().get(0);
	}

	public int deleteByWhere(String where) {
		return deleteEntitiesByWhere(getActualArgumentType(), where);
	}

	public int deleteEntitiesByWhere(Class entity, String where) {
		StringBuilder hql = new StringBuilder();
		hql.append("DELETE ").append(entity.getName()).append(" WHERE ").append(where);
		Query query = getSession().createQuery(hql.toString());
		int result = query.executeUpdate();
		return result;
	}

	// ==== search by SQL (return as List<T> or T)
	/**
	 * Search by HQL and get object list.
	 *
	 * @param sql
	 * @return
	 */
	public List<T> searchBySql(String sql) {
		return getSession().createQuery(sql).list();
	}

	/**
	 * Search by HQL with prepared parameters.
	 *
	 * @param entity
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<T> searchBySql(String sql, Object... params) {
		Query query = getSession().createQuery(sql);
		int i = 0;
		for (Object object : params) {
			query.setParameter(i++, object);
		}
		return query.list();
	}

	/**
	 * Search by HQL and return object of specific type - T.
	 *
	 * @param entity
	 * @param sql
	 * @return
	 */
	public T searchObjectBySql(String sql) {
		List<T> result = this.searchBySql(sql);
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	/**
	 * Search by HQL with prepared parameters.
	 *
	 * @param entity
	 * @param sql
	 * @param params
	 * @return
	 */
	public T searchObjectBySql(String sql, Object... params) {
		List<T> result = this.searchBySql(sql, params);
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	// ==== search by SQL (return as List<A> or A)

	/**
	 * Search By HQL and return List of specified type (entity).
	 *
	 * @param entity
	 * @param sql
	 * @return
	 */
	private <A> List<A> searchBySql(Class<A> entity, String sql) {
		return getSession().createQuery(sql).list();
	}

	/**
	 * @param entity
	 * @param sql
	 * @param params
	 * @return
	 */
	public <A> List<A> searchBySql(Class<A> entity, String sql, Object... params) {
		Query query = getSession().createQuery(sql);
		int i = 0;
		for (Object object : params) {
			query.setParameter(i++, object);
		}
		return query.list();
	}

	/**
	 * @param entity
	 * @param sql
	 * @param params
	 * @return
	 */
	public <A> A searchObjectBySql(Class<A> entity, String sql, Object... params) {
		List<A> result = this.searchBySql(entity, sql, params);
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	// ==== search by Native SQL (return as List or Object)
	/**
	 * @param entity
	 * @param sql
	 * @return
	 */
	public <A> List<A> searchByNativeSql(Class<A> entity, String sql) {
		return getSession().createSQLQuery(sql).addEntity(entity).list();
	}

	/**
	 * @param entity
	 * @param sql
	 * @param params
	 * @return
	 */
	public <A> List<A> searchByNativeSql(Class<A> entity, String sql, Object... params) {
		Query query = getSession().createSQLQuery(sql).addEntity(entity);
		int i = 0;
		for (Object object : params) {
			query.setParameter(i++, object);
		}
		return query.list();
	}

	/**
	 *
	 * @param entity
	 * @param sql
	 * @return
	 */
	public <A> A searchObjectByNativeSql(Class<A> entity, String sql) {
		List<A> result = this.searchByNativeSql(entity, sql);
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	/**
	 * @param entity
	 * @param sql
	 * @param params
	 * @return
	 */
	public <A> A searchObjectByNativeSql(Class<A> entity, String sql, Object... params) {
		List<A> result = this.searchByNativeSql(entity, sql, params);
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	/**
	 * Search by where
	 *
	 * @param where
	 *            the condition after the where
	 * @return
	 */
	public T searchByWhere(String where,Object... params) {
		Class entityClass = getActualArgumentType();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM  ").append(entityClass.getName()).append(" A WHERE ").append(where);
		List<T> list = searchBySql(entityClass, sql.toString(),params);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<T> search(String where,Object... params) {
		Class entityClass = getActualArgumentType();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM  ").append(entityClass.getName()).append(" A WHERE ").append(where);
		return searchBySql(entityClass, sql.toString(),params);
	}

	/**
	 * Get object by objectId
	 *
	 * @param objectId
	 * @return
	 */
	@Override
	public T getByObjectId(Serializable objectId) {
		if (null == objectId) {
			return null;
		}
		Class entityClass = getActualArgumentType();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM  ").append(entityClass.getName()).append(" A WHERE A.objectId = ?");
		List<T> list = searchBySql(entityClass, sql.toString(), new Object[] { objectId });
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean isFieldUnique(String field, Object value, Serializable objectId){
		Class entityClass = getActualArgumentType();
		String sql = "select A from " + entityClass.getName() + " A where  A." + field + " = ?";
		Object[] parameters = new Object[] { value};
		if (objectId != null) {
			sql += " and A.objectId != ?";
			parameters = new Object[] { value, objectId };
		}
		List<T> list = searchBySql(sql, parameters);
		if (list.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 动态更新
	 * 自动根据更新非空属性。
	 * 实体中任何不为空的属性均会被更新，为空的属性不修改。适合局部字段更新的情况
	 * @param entity 需要更新的实体
	 */
	public void updateDynamic(final T entity) {
		List<Object> values= new LinkedList<Object>();
		List<String> primaryKeys = new ArrayList<String>(3);
		ReflectUtil.getIdByAnnotation(entity.getClass(), Id.class, primaryKeys);
		List<FieldProperty> fields = ValidateContainer.getAllFieldsOfTheDomain(entity.getClass().getName());
		List<String> updatedFields = new ArrayList<>();
		for (FieldProperty field : fields) {
			String fieldName = field.getField().getName();
			Object obj = ReflectUtil.invokeGet(fieldName, entity);
			if (obj != null&&!primaryKeys.contains(fieldName)) {
				values.add(obj);
				updatedFields.add(fieldName);
			}
		}
		executeUpdate(entity, updatedFields, values, primaryKeys);
	}

	/**
	 * 更新指定的实体任意字段
	 * 字段可以更新为空
	 * @param entity
	 * @param updatedFileds 要更新的属性名称
	 */
	public void update(final T entity,String... updatedFileds) {
		List<Object> allValues= new LinkedList<Object>();
		List<String> primaryKeys = new ArrayList<String>(3);
		ReflectUtil.getIdByAnnotation(entity.getClass(), Id.class, primaryKeys);
		for (String field : updatedFileds) {
			Object obj = ReflectUtil.invokeGet(field, entity);
			if (!primaryKeys.contains(field)) {
				allValues.add(obj);
			}
		}
		executeUpdate(entity, Arrays.asList(updatedFileds), allValues, primaryKeys);
	}

	private void executeUpdate(final T entity, List<String> updatedFields, List<Object> values, List<String> primaryKeys) {
		StringBuilder condition = new StringBuilder(" where");
		StringBuilder set = new StringBuilder("set");
		int valueCount = 0;
		for (String filed : updatedFields) {
			if (valueCount == 0) {
				if(values.get(valueCount)!=null){
				    set.append(" " + filed + "=?");
				} else {
					set.append(" " + filed + "=null");
				}
			} else {
				if(values.get(valueCount)!=null){
				    set.append(" ," + filed + "=?");
				}else{
					set.append(" ," + filed + "=null");
				}
			}
			valueCount++;
		}
		int conditionCount = 0;
		for (String str : primaryKeys) {
			Object obj = ReflectUtil.invokeGet(str, entity);
			values.add(obj);
			if (conditionCount == 0) {
				condition.append(" " + str + "=?");
			} else {
				condition.append(" and " + str + "=?");
			}
			conditionCount++;
		}
		StringBuilder sql = new StringBuilder("update " + entity.getClass().getName() + " ");
		sql.append(set);
		sql.append(condition);
		Query query = getSession().createQuery(sql.toString());
		int j = 0;
		for(int i=0;i<values.size();i++){
			if(values.get(i) != null){
				query.setParameter(j++, values.get(i));
			}
		}
		query.executeUpdate();
	}
}
