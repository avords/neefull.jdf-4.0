package com.mvc.framework.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.loader.criteria.CriteriaQueryTranslator;
import org.springframework.util.Assert;
import org.springframework.web.util.WebUtils;

import com.mvc.framework.config.ProjectConfig;
import com.mvc.framework.dao.PropertyFilter.MatchType;
import com.mvc.framework.util.PageSearch;
import com.mvc.framework.util.ReflectUtils;

public final class HibernateWebUtils {

	private static final Map<String, String> ENTITIES = new HashMap<String, String>();
	static {
		for (Object object : ProjectConfig.getAllModel()) {
			ENTITIES.put(object.getClass().getSimpleName(), object.getClass().getCanonicalName());
		}
	}

	private static String getFullClassName(String simpleName) {
		return ENTITIES.get(simpleName);
	}

	private HibernateWebUtils() {
	}

	public static List<PropertyFilter> buildPropertyFilters(final HttpServletRequest request,
			final String defaultObectName) {
		return buildPropertyFilters(request, "search_", defaultObectName);
	}

	/**
	 * eg. search_EQS_name
	 */
	public static List<PropertyFilter> buildPropertyFilters(final HttpServletRequest request,
			final String filterPrefix, final String defaultObjectName) {
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();
		// Get request parameter
		Map<String, Object> filterParamMap = WebUtils.getParametersStartingWith(request, filterPrefix);
		// Construct PropertyFilter list
		for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
			String filterName = entry.getKey();
			String value = entry.getValue().toString();
			// Ignore when parameter value is blank
			if (StringUtils.isNotBlank(value)) {
				PropertyFilter filter = new PropertyFilter(defaultObjectName, filterName, value);
				filterList.add(filter);
			}
		}
		return filterList;
	}

	private static String getMatchedRelation(Set<String> all, String targetObjectName) {
		for (String relation : all) {
			if (relation.startsWith(targetObjectName + ".")) {
				return relation;
			}
		}
		return null;
	}

	public static DetachedCriteria createCriteria(final PageSearch pageSearch) {
		DetachedCriteria criteria = createCriteria(pageSearch.getEntityClass(), pageSearch.getFilters());
		Set<String> allRelationShips = pageSearch.getRelationships();
		if (allRelationShips.size() != 0) {
			Set<String> noneRootObject = new HashSet<String>();
			List<PropertyFilter> noneRootFilter = new ArrayList<PropertyFilter>();
			for (PropertyFilter filter : pageSearch.getFilters()) {
				// find the none root entity condition
				if (filter.getFilterName().indexOf(".") != -1) {
					noneRootFilter.add(filter);
					noneRootObject.add(filter.getObjectName());
				}
			}
			if (noneRootFilter.size() > 0) {
				for (String objectName : noneRootObject) {
					String relation = getMatchedRelation(allRelationShips, objectName);
					if (relation != null) {
						// role.objectId__userRole.roleId;userId_user.objectId
						String[] arr0 = relation.split("_");
						if (arr0.length >= 2) {
							DetachedCriteria role = DetachedCriteria.forEntityName(
									getFullClassName(upperFirstName(objectName)), upperFirstName(objectName));
							role.add(Property.forName(arr0[0].split("\\.")[1]).eqProperty(
									upperFirstName(arr0[1].split(";")[0])));
							for (PropertyFilter filter : noneRootFilter) {
								if (filter.getObjectName().equals(objectName)) {
									Criterion criterion = buildPropertyFilterCriterion(filter.getPropertyName(),
											filter.getPropertyValue(), filter.getMatchType(),filter.getPropertyValues());
									role.add(criterion);
								}
							}
							role.setProjection(Projections.property(arr0[0].split("\\.")[1]));

							DetachedCriteria last = role;
							for (int i = 1; i < arr0.length - 1; i++) {
								String userRoleClass = upperFirstName(arr0[i].split("\\.")[0]);
								DetachedCriteria userRole = DetachedCriteria.forEntityName(
										getFullClassName(userRoleClass), userRoleClass);
								String[] arr3 = arr0[i].split("\\.")[1].split(";");
								userRole.add(Property.forName(arr3[1]).eqProperty(
										upperFirstName(arr0[i + 1].split(";")[0])));

								userRole.add(Subqueries.exists(last.setProjection(Projections.property(arr0[i - 1]
										.split(";")[0].split("\\.")[1]))));
								// userRole.add(Subqueries.propertyEq(arr0[i].split(";")[0].split("\\.")[1],last));
								userRole.setProjection(Projections.property(arr3[1]));
								last = userRole;
							}
							// criteria.add(Subqueries.propertyEq(
							// arr0[arr0.length-1].split("\\.")[1], last));
							criteria.add(Subqueries.exists(last.setProjection(Projections
									.property(arr0[arr0.length - 2].split(";")[1]))));
						}
					}
				}
			}
		}
		return criteria;
	}

	public static void main(String[] args) {
		System.out.println("bang.pu".split("\\.").length);
	}

	public static DetachedCriteria createCriteria(final Class entityClass, final List<PropertyFilter> propertyFilters) {
		return createCriteria(entityClass, buildPropertyFilterCriterions(propertyFilters));
	}

	private static String getClassName(Class entityClass) {
		return entityClass.getSimpleName();
	}

	public static String upperFirstName(String objectName) {
		return objectName.substring(0, 1).toUpperCase() + objectName.substring(1);
	}

	public static String lowerFirstName(String objectName) {
		return objectName.substring(0, 1).toLowerCase() + objectName.substring(1);
	}

	public static DetachedCriteria createCriteria(final Class entityClass, final Criterion... criterions) {
		DetachedCriteria criteria = DetachedCriteria.forClass(entityClass, getClassName(entityClass));
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	public static Criterion[] buildPropertyFilterCriterions(final List<PropertyFilter> filters) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		for (PropertyFilter filter : filters) {
			if (MatchType.OR.equals(filter.getMatchType())) {
				Disjunction disjunction = Restrictions.disjunction();
				for (PropertyFilter propertyFilter : filter.getOrPropertyFilters()) {
					if (propertyFilter.getFilterName().indexOf(".") == -1) {
						Criterion criterion = buildPropertyFilterCriterion(propertyFilter.getPropertyName(),
								propertyFilter.getPropertyValue(), propertyFilter.getMatchType(), filter.getPropertyValues());
						disjunction.add(criterion);
					}
				}
				criterionList.add(disjunction);
			} else {
				if (filter.getFilterName().indexOf(".") == -1) {
					Criterion criterion = buildPropertyFilterCriterion(filter.getPropertyName(),
							filter.getPropertyValue(), filter.getMatchType(), filter.getPropertyValues());
					criterionList.add(criterion);
				}
			}
		}
		return criterionList.toArray(new Criterion[criterionList.size()]);
	}

	public static Criterion buildPropertyFilterCriterion(final String propertyName, final Object propertyValue,
			final MatchType matchType,final Object... propertyValues) {
		Assert.hasText(propertyName, "propertyName can not be null");
		Criterion criterion = null;
		try {
			// Construct criterion according MatchType
			if (MatchType.EQ.equals(matchType)) {
				criterion = Restrictions.eq(propertyName, propertyValue);
			} else if (MatchType.LIKE.equals(matchType)) {
				criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.ANYWHERE);
			} else if (MatchType.LE.equals(matchType)) {
				criterion = Restrictions.le(propertyName, propertyValue);
			} else if (MatchType.LT.equals(matchType)) {
				criterion = Restrictions.lt(propertyName, propertyValue);
			} else if (MatchType.GE.equals(matchType)) {
				criterion = Restrictions.ge(propertyName, propertyValue);
			} else if (MatchType.GT.equals(matchType)) {
				criterion = Restrictions.gt(propertyName, propertyValue);
			} else if (MatchType.NE.equals(matchType)) {
				criterion = Restrictions.ne(propertyName, propertyValue);
			} else if (MatchType.IN.equals(matchType)) {
				criterion = Restrictions.isNull(propertyName);
			} else if (MatchType.NN.equals(matchType)) {
				criterion = Restrictions.isNotNull(propertyName);
			} else if (MatchType.START.equals(matchType)) {
				criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.START);
			} else if (MatchType.NLIKE.equals(matchType)) {
				criterion = Restrictions.not(Restrictions.like(propertyName, (String) propertyValue, MatchMode.START));
			} else if (MatchType.IC.equals(matchType)){
				criterion = Restrictions.in(propertyName, propertyValues);
			} else if (MatchType.NC.equals(matchType)){
				criterion = Restrictions.not(Restrictions.in(propertyName, propertyValues));
			}
		} catch (Exception e) {
			throw ReflectUtils.convertReflectionExceptionToUnchecked(e);
		}
		return criterion;
	}

	public static String buildHql(final List<PropertyFilter> filters, CriteriaImpl criteria,
			SessionFactoryImplementor sessionFactoryImplementor) {
		Criterion[] criterions = buildPropertyFilterCriterions(filters);
		return buildHql(criterions, criteria, sessionFactoryImplementor);
	}

	private static Object getQueryValues(PropertyFilter propertyFilter) {
		MatchType matchType = propertyFilter.getMatchType();
		Object result = propertyFilter.getPropertyValue();
		if (result instanceof String) {
			switch (matchType) {
			case LIKE:
			case NLIKE:
				result = "%" + result + "%";
				break;
			case START:
				result = result + "%";
				break;
			case END:
				result = "%" + result;
			default:
				break;
			}
		}
		return result;
	}

	public static Object[] getQueryParameter(final List<PropertyFilter> filters) {
		List<Object> result = new ArrayList<Object>();
		for (PropertyFilter filter : filters) {
			if (MatchType.OR.equals(filter.getMatchType())) {
				for (PropertyFilter propertyFilter : filter.getOrPropertyFilters()) {
					if (filter.getMatchType() != MatchType.IN && filter.getMatchType() != MatchType.NN) {
						result.add(getQueryValues(propertyFilter));
					}
				}
			} else if(MatchType.IC.equals(filter.getMatchType())||MatchType.NC.equals(filter.getMatchType())){
				List<Object> list = filter.getPropertyValues();
				result.addAll(list);
			}else {
				if (filter.getMatchType() != MatchType.IN && filter.getMatchType() != MatchType.NN) {
					result.add(getQueryValues(filter));
				}
			}
		}
		return result.toArray();
	}

	public static void setQueryParameter(final Query query, final Object... values) {
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
	}

	public static String buildHql(Criterion[] criterions, CriteriaImpl criteria,
			SessionFactoryImplementor sessionFactoryImplementor) {
		StringBuilder hql = new StringBuilder(criterions.length * 20);
		CriteriaQueryTranslator translator = new CriteriaQueryTranslator(sessionFactoryImplementor, criteria,
				criteria.getEntityOrClassName(), CriteriaQueryTranslator.ROOT_SQL_ALIAS);
		for (Criterion c : criterions) {
			hql.append(" AND ").append(c.toSqlString(criteria, translator));
		}
		return hql.toString();
	}

	public static String convertCamelStyleToUpperCase(String camelStyleString) {
        if (StringUtils.isBlank(camelStyleString)) {
            return null;
        }
        if (camelStyleString.length() == 1) {
            return camelStyleString.toUpperCase();
        }
        String upperCaseString = "";
        upperCaseString += camelStyleString.charAt(0);
        for (int i = 1; i < camelStyleString.length(); i++) {
            if ((camelStyleString.charAt(i) >= 'A')
                    && (camelStyleString.charAt(i) <= 'Z')) {
                //如果为大写字母,说明为单词开头,则加下划线分割
                upperCaseString += "_";
            }
            upperCaseString += camelStyleString.charAt(i);
        }
        upperCaseString = upperCaseString.toUpperCase();
        return upperCaseString;
    }

	public static String buildSql(final List<PropertyFilter> filters){
		StringBuilder result = new StringBuilder();
		for(PropertyFilter filter : filters){
			if(filter.getMatchType().equals(MatchType.IC)||filter.getMatchType().equals(MatchType.NC)){
				result.append(" AND ").append(filter.getPropertyName()).append(" ").append(getCompare(filter.getMatchType())).append(" (");
				for(int i=0;i<filter.getPropertyValues().size();i++){
					if(i!=filter.getPropertyValues().size()-1){
						result.append("?,");
					} else {
						result.append("?");
					}
				}
				result.append(") ");
			} else {
				result.append(" AND ").append(filter.getPropertyName()).append(" ").append(getCompare(filter.getMatchType())).append(" ? ");
			}
		}
		if(result.length()>0){
			return result.substring(0,result.length()-1);
		}
		return "";
	}

	private static String getCompare(MatchType matchType){
		String result = null;
		switch (matchType) {
		case EQ:
			result = "=";
			break;
		case START:
		case LIKE:
			result= "like";
			break;
		case LE:
			result = "<=";
			break;
		case LT:
			result = "<";
			break;
		case GE:
			result = ">=";
			break;
		case GT:
			result = ">";
			break;
		case NE:
			result = "!=";
			break;
		case IN:
			result = "is null";
			break;
		case NN:
			result = "is not null";
			break;
		case NLIKE:
			result = "not like";
			break;
		case IC:
			result = "in";
			break;
		case NC:
			result= "not in";
			break;
		default:
			result = "=";
			break;
		}
		return result;
	}
}
