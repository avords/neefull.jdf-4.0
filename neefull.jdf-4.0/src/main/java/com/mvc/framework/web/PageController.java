package com.mvc.framework.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.framework.service.Manager;
import com.mvc.framework.service.PageManager;
import com.mvc.framework.util.PageSearch;
import com.mvc.framework.util.PageUtils;

/**
 * Base controller for page query class
 * The entrance is page method
 * The sub class can override preparePage、handelPage
 * @author pubx 2010-3-29 02:27:45
 */
public abstract class PageController<T> extends BaseController<T> {

	private static final Logger LOGGER = Logger.getLogger(PageController.class);
	@Deprecated
	public static final int IS_NOT_BACK = PageUtils.IS_NOT_BACK;
	@Deprecated
	public static final int IS_BACK = PageUtils.IS_BACK;
	@Deprecated
	public static final String TOTAL_ROWS = PageUtils.TOTAL_ROWS;
	@Deprecated
	public static final String SEARCH_PREFIX = PageUtils.SEARCH_PREFIX;
	@Deprecated
	public static final String RELATIONSHIP_PREFIX = PageUtils.RELATIONSHIP_PREFIX;
	@Deprecated
	public static final String PAGE_SUFFIX = PageUtils.PAGE_SUFFIX;
	
	/**
	 * Go into the list page
	 * @param request
	 * @param t
	 * @param backPage 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/page")
	public String page(HttpServletRequest request, T t, Integer backPage) throws Exception {
		PageSearch page  = preparePage(request);
		String result = handlePage(request, page);
		afterPage(request, page,backPage);
		return result;
	}

	protected void afterPage(HttpServletRequest request, PageSearch page, Integer backPage) {
	    PageUtils.afterPage(request, page, PageUtils.IS_NOT_BACK);
    }

//	/**
//	 * Query history data
//	 * @param request
//	 * @param t
//	 * @param backPage
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/pageHistory")
//	public String pageHistory(HttpServletRequest request, T t, Integer backPage) throws Exception {
//		PageSearch page  = preparePageHistory(request);
//		String result = handlePageHistory(request, page);
//		request.setAttribute(LIST_ITEMS, page.getList());
//		request.setAttribute(TOTAL_ROWS, page.getTotalCount());
//		return result;
//	}

	protected PageSearch preparePage(HttpServletRequest request) {
		return PageUtils.preparePage(request, getActualArgumentType(), isAssignableFromBaseEntity());
    }

//	protected PageSearch preparePageHistory(HttpServletRequest request) {
//	    PageSearch page = PageUtils.getPageSearch(request);
//	    List<PropertyFilter> propertyFilters = HibernateWebUtils.buildPropertyFilters(request, SEARCH_PREFIX);
//	    Class entityClass = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//	    page.setEntityClass(entityClass);
//	    try{
//	    	if(null==page.getSortProperty()){
//				page.setSortProperty("objectId");
//				page.setSortOrder("desc");
//			}
//	    }catch (Exception e) {
//	    	LOGGER.error("preparePageHistory",e);
//		}
//	    page.setFilters(propertyFilters);
//	    return page;
//    }
	
	/**
	 * Go back to list page 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/back")
	public String back(HttpServletRequest request) throws Exception {
		//TODO support back to any page
		String requestUrl = request.getRequestURI();
		requestUrl = requestUrl.replace("/back", "/page");
		//Get page search from session
		String parameters = (String) request.getSession().getAttribute(requestUrl + PageUtils.PAGE_SUFFIX);
		if (parameters != null) {
			return "redirect:page?" + parameters;
		}
		return page(request, null, PageUtils.IS_BACK);
	}

	protected String handlePage(HttpServletRequest request, PageSearch page) {
		getEntityManager().find(page);
		request.setAttribute("action", "page");
		return getFileBasePath() + "list" + getActualArgumentType().getSimpleName();
	}
	
	public Manager<T> getManager(){
		return getEntityManager();
	}
	
	public abstract PageManager<T> getEntityManager();

}
