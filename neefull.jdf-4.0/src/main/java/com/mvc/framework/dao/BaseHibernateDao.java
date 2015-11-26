package com.mvc.framework.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.stereotype.Repository;

import com.mvc.framework.util.ReflectUtils;

@Repository
public class BaseHibernateDao<T, PK extends Serializable> {
	private static final Logger LOGGER = Logger.getLogger(BaseHibernateDao.class);
	@Resource
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Save or update
	 */
	public void save(final T entity) {
		getSession().saveOrUpdate(entity);
	}

	/**
	 * delete entity
	 *
	 * @param entity
	 */
	public void delete(final T entity) {
		getSession().delete(entity);
	}

	/**
	 * Delete by ID
	 */
	public void delete(final PK id) {
		delete(get(id));
	}

	/**
	 * Get by ID
	 */
	public T get(final PK id) {
		return (T) getSession().get(ReflectUtils.getSuperClassGenricType(getClass()), id);
	}

	public T load(final Class clazz, final PK id) {
		return (T) getSession().load(clazz, id);
	}

	public T get(final Class clazz, final PK id) {
		return (T) getSession().get(clazz, id);
	}

	/**
	 * Get all
	 */
	public List<T> getAll() {
		return find();
	}

	/**
	 * Get all with sort function
	 */
	public List<T> getAll(String orderBy, boolean isAsc) {
		Criteria c = createCriteria();
		if (isAsc) {
			c.addOrder(Order.asc(orderBy));
		} else {
			c.addOrder(Order.desc(orderBy));
		}
		return c.list();
	}

	public List<T> findBy(final String propertyName, final Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	public T findUniqueBy(final String propertyName, final Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	public List<T> findByIds(List<PK> ids) {
		return find(Restrictions.in(getIdName(), ids));
	}

	public <X> List<X> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	public <X> List<X> find(final String hql, final Map<String, Object> values) {
		return createQuery(hql, values).list();
	}

	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	public <X> X findUnique(final String hql, final Map<String, Object> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	public int batchExecute(final String hql, final Map<String, Object> values) {
		return createQuery(hql, values).executeUpdate();
	}

	public Query createQuery(final String queryString, final Object... values) {
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	public Query createQuery(final String queryString, final Map<String, Object> values) {
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}


	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(ReflectUtils.getSuperClassGenricType(getClass()));
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	public void initEntity(T entity) {
		Hibernate.initialize(entity);
	}

	/**
	 * @see #initEntity(Object)
	 */
	public void initEntity(List<T> entityList) {
		for (T entity : entityList) {
			Hibernate.initialize(entity);
		}
	}

	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}


	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	public <X> List<X> distinct(List list) {
		Set<X> set = new LinkedHashSet<X>(list);
		return new ArrayList<X>(set);
	}

	public String getIdName() {
		ClassMetadata meta = sessionFactory.getClassMetadata(ReflectUtils.getSuperClassGenricType(getClass()));
		return meta.getIdentifierPropertyName();
	}
}
