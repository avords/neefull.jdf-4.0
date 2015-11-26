package com.mvc.framework.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.web.util.WebUtils;
import org.springside.modules.beanvalidator.BeanValidators;

import com.mvc.ProjectConstants;
import com.mvc.framework.FrameworkConstants;
import com.mvc.framework.config.GlobalConfig;
import com.mvc.framework.config.ProjectConfig;
import com.mvc.framework.context.FrameworkContextImpl;
import com.mvc.framework.context.ThreadLocalContextHolder;
import com.mvc.framework.util.AjaxUtils;
import com.mvc.framework.util.CookieUtils;
import com.mvc.framework.util.IpUtils;
import com.mvc.framework.util.LocaleUtils;
import com.mvc.framework.util.ThemeUtils;
import com.mvc.security.SecurityConstants;
import com.mvc.security.model.Menu;
import com.mvc.security.model.MenuLink;
import com.mvc.security.model.User;
import com.mvc.security.service.AuthorizationManager;
import com.mvc.security.util.SecurityUtils;

/**
 * Framework filter,including function ad follows:
 * 1、Security check
 * 2、Initialize framework utility
 * 3、Exception handle
 * 4、Log4j parameters set
 * 5、I18n config
 * @author pubx
 *
 */
public class FrameworkFilter implements Filter {
	private static final int AUTHENTICATE_SUCCESS = 1;
	private static final Logger LOGGER = Logger.getLogger(FrameworkFilter.class);
	private static final int ONE_SECOND = 1000;
	private static final int ONE_MINUTE_IN_MILLIS = 60 * 1000;
	private static final String PERMISSION_FILTERED_MENU = "SecurityFilter.filterMenu";
	private static final String PERMISSION_MY_URL = "SecurityFilter.myUrl";

	private static final String REQUEST_AJAX = "ajax";
	private static final String AJAX_TYPE_COMMON = "1";

	private static final String[] MDC_OBJECTS = new String[] {
			SecurityConstants.FULL_NAME, SecurityConstants.USER_ID,
			SecurityConstants.LOGIN_NAME, SecurityConstants.SECURITY_TOKEN,
			SecurityConstants.IP };

	private List<String> notNeedLoginUrls = null;
	private static AuthorizationManager authorizationManager;
	private volatile List<String> allPermissionUrls = new ArrayList<String>();
	private volatile boolean hasPermissionUrl = false;
	private static int refreshPermissionTime;
	private static boolean securityFilter = true;
	private static boolean accessAll = true;

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
	        throws IOException, ServletException {
		if ((servletRequest instanceof HttpServletRequest) && (servletResponse instanceof HttpServletResponse)) {
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			HttpSession session = request.getSession();
			localeChangeFilter(request, response);
			if(securityFilter){
				/**
				 * 1.Need authenticate
				 * 2.Is login URL
				 * 3.Is authenticate success
				 * 4.Has permission
				 */
			    if (isNeedAuthentication(request.getRequestURI())) {
			    	int authenticateResult = authenticateRequest(request, response);
			    	String servletPath = request.getServletPath();
			    	if (AUTHENTICATE_SUCCESS == authenticateResult) {
			    		if (isNeedValidate(servletPath, request)) {
			    			boolean ok = validatePermissionMenu(session, servletPath);
			    			if (!ok) {
			    				LOGGER.info(session.getAttribute(SecurityConstants.FULL_NAME) + " illegal access " + servletPath);
			    				echoIllegalAccess(response);
			    				return;
			    			}
			    		}
			    	} else {
			    		if (!GlobalConfig.getLoginUrl().endsWith(request.getRequestURI())) {
			    			if(!servletPath.endsWith("favicon.ico")){
			    				session.setAttribute(SecurityConstants.LAST_REQUEST_URL, request.getRequestURL().toString());
			    			}
			    			response.sendRedirect(GlobalConfig.getLoginUrl());
			    			return;
			    		}
			    	}
			    }
			}
			try {
				FrameworkContextImpl frameworkContext = new FrameworkContextImpl();
				User user = (User) session.getAttribute(SecurityConstants.SESSION_USER);
				frameworkContext.setCurrentUser(user);
				ThreadLocalContextHolder.setContext(frameworkContext);
				putObjectToLog4jMdc(request, session);
				filterChain.doFilter(servletRequest, servletResponse);
				removeObjectFromLog4jMdc();
			} catch (Exception e) {
				doError(request, response, e);
			}
		}
	}

	private void localeChangeFilter(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = WebUtils.getCookie(request, LocaleUtils.DEFAULT_LOCALE_PARAM_NAME);
		String localeName = null;
		if(cookie==null){
			localeName = request.getParameter(LocaleUtils.DEFAULT_LOCALE_PARAM_NAME);
			if(localeName==null){
				localeName = request.getLocale().toString();
			}
		}else{
			localeName = cookie.getValue();
		}
	    if(localeName != null){
	    	Locale locale = LocaleUtils.getLocale(localeName);
	    	LocaleUtils.setSpringLocale(request.getSession(), locale);
	    	LocaleUtils.setExtremetableLocale(request.getSession(), localeName);
	    }
    }

	private void removeObjectFromLog4jMdc() {
	    for (int i = 0; i < MDC_OBJECTS.length; i++) {
	    	MDC.remove(MDC_OBJECTS[i]);
	    }
    }

	private void putObjectToLog4jMdc(HttpServletRequest request, HttpSession session) {
	    session.setAttribute(SecurityConstants.IP, IpUtils.getRealIp(request));
	    for (int i = 0; i < MDC_OBJECTS.length; i++) {
	    	Object obj = session.getAttribute(MDC_OBJECTS[i]);
	    	if (obj != null) {
	    		MDC.put(MDC_OBJECTS[i], obj);
	    	}
	    }
    }

	private void echoIllegalAccess(HttpServletResponse response) throws IOException {
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	}

	protected boolean isNeedAuthentication(final String requestUrl) {
		if (notNeedLoginUrls == null) {
			return true;
		} else {
			for (String url : notNeedLoginUrls) {
				if (requestUrl.indexOf(url)!=-1) {
					return false;
				}
			}
		}
		return true;
	}

	public static int authenticateRequest(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String sessionToken = (String) session.getAttribute(SecurityConstants.SECURITY_TOKEN);
		int result = 0;
		if (null==sessionToken) {
			String cookieToken = CookieUtils.getEncodedCookieByName(request.getCookies(),SecurityConstants.SECURITY_TOKEN);
			if (SecurityUtils.validateSecurityToken(cookieToken)) {
				result = AUTHENTICATE_SUCCESS;
				setUserToSession(request, response,cookieToken,null);
			}
		} else {
			result = AUTHENTICATE_SUCCESS;
		}
		return result;
	}

	protected boolean isNeedValidate(final String requestUrl, HttpServletRequest request) {
		boolean result = false;
		if(!accessAll){
			result = true;
		}else if (hasPermissionUrl) {
			for(String url : allPermissionUrls){
				if (requestUrl.matches(url)) {
					result = true;
				}
			}
		}
		return result;
	}

	private boolean validatePermissionMenu(HttpSession session, String requestURI) {
		List<String> myPermiddionUrl = (List<String>) session.getAttribute(PERMISSION_MY_URL);
		if(null!=myPermiddionUrl){
			for (String menu : myPermiddionUrl) {
				if (requestURI.matches(menu)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void setUserToSession(HttpServletRequest request, HttpServletResponse response,String cookieToken,String skin) {
		String loginName = SecurityUtils.getLoginNameFromSecurityToke(cookieToken);
		User user = authorizationManager.getUserPermissionByLoginName(loginName, ProjectConstants.PROJECT_NAME);
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute(SecurityConstants.SECURITY_TOKEN, cookieToken);
			session.setAttribute(SecurityConstants.LOGIN_NAME, loginName);
			session.setAttribute(SecurityConstants.FULL_NAME, user.getUserName());
			session.setAttribute(SecurityConstants.USER_ID, user.getObjectId());
			session.setAttribute(SecurityConstants.SESSION_USER, user);
			session.setAttribute(SecurityConstants.MENU_PERMISSION, user.getMenus());
			session.setAttribute(SecurityConstants.OPERATION_PERMISSION, user.getOperations());
			List<Menu> allMenu = user.getMenus();
			List<Menu> filterMenu = new ArrayList<Menu>(allMenu.size());
			List<String> myPermissionUrls = new ArrayList<String>();
			for(Menu menu : allMenu) {
				if(Menu.TYPE_MENU == menu.getType()) {
					filterMenu.add(menu);
					addPermissionUrl(menu,myPermissionUrls);
				}
			}
			session.setAttribute(PERMISSION_FILTERED_MENU, filterMenu);
			session.setAttribute(PERMISSION_MY_URL, myPermissionUrls);
			CookieUtils.setEncodedCookie(response, SecurityConstants.USER_ID, String.valueOf(user.getObjectId()));
			if(null==skin){
				skin = CookieUtils.getCookieByName(request.getCookies(), FrameworkConstants.SKIN);
			}
			if(StringUtils.isBlank(skin)){
				skin = FrameworkConstants.DEFAULT_SKIN;
			}
			session.setAttribute(FrameworkConstants.SKIN, skin);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		FrameworkFactory.init(filterConfig.getServletContext());
		authorizationManager = FrameworkFactory.getAuthorizationManager();
		accessAll = GlobalConfig.isAccessAll();
		String security = filterConfig.getInitParameter("securityFilter");
		if(null!=security){
			securityFilter = Boolean.valueOf(security);
		}
		if(securityFilter){
			String notNeedLoginUrl = filterConfig.getInitParameter(SecurityConstants.NOT_NEED_LOGIN_URLS);
			notNeedLoginUrls = new ArrayList<String>(8);
			if (notNeedLoginUrl != null && notNeedLoginUrl.length() > 0) {
				if (notNeedLoginUrl.indexOf(",") != -1) {
					notNeedLoginUrls.addAll(Arrays.asList(notNeedLoginUrl.split(",")));
				} else {
					notNeedLoginUrls.addAll(Arrays.asList(notNeedLoginUrl.split(" ")));
				}
			}
			//default login in, login out
			notNeedLoginUrls.add(GlobalConfig.getLoginUrl());
			notNeedLoginUrls.add(GlobalConfig.getLoginUrl() + "/out");
			Timer timer1 = new Timer();
			if(ProjectConfig.isEnableRefreshPermission()){
				refreshPermissionTime = GlobalConfig.getRefreshPermissionTime();
				LOGGER.info("The System Permission refresh interval is " + refreshPermissionTime + " minutes");
				timer1.schedule(new RefreshAllPermissionMenuTask(), ONE_SECOND, ONE_MINUTE_IN_MILLIS * refreshPermissionTime);
			}else{
				timer1.schedule(new RefreshAllPermissionMenuTask(), ONE_SECOND);
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("/ddss/edit/1".matches(".*/edit/[0-9]+$"));
	}

	final class RefreshAllPermissionMenuTask extends TimerTask {
		@Override
		public void run() {
			try {
				List<Menu> allMenus = authorizationManager.getAllMenuByContext(ProjectConstants.PROJECT_NAME);
				if (allMenus == null) {
					hasPermissionUrl = false;
					allPermissionUrls.clear();
				} else {
					hasPermissionUrl = true;
					for (Menu menu:allMenus) {
						addPermissionUrl(menu,allPermissionUrls);
					}
				}
				LOGGER.debug("Update application " + ProjectConstants.PROJECT_NAME + "'s permission finished");
			} catch (RemoteAccessException e) {
				LOGGER.error("Update application " + ProjectConstants.PROJECT_NAME + "'s permission failed,retry after 1 minutes:" + e.getMessage());
				try {
					Thread.sleep(ONE_MINUTE_IN_MILLIS);
					this.run();
				} catch (InterruptedException e2) {
					LOGGER.error("run",e2);
				}
			}
		}

	}

	private static void addPermissionUrl(Menu menu, List<String> list) {
		if(menu.isMenu()&&StringUtils.isNotBlank(menu.getUrl())){
			String pageUrl = menu.getUrl();
			list.add(pageUrl);
			String action = pageUrl.substring(pageUrl.lastIndexOf("/"));
			if(menu.getCrud()==null || menu.getCrud()){
				list.add(pageUrl.replace(action, "/create"));
				list.add(pageUrl.replace(action, "/save"));
				list.add(pageUrl.replace(action, "/saveToPage"));
				list.add(pageUrl.replace(action, "/saveToCreate"));
				list.add(pageUrl.replace(action, "/back"));
				
				list.add(pageUrl.replace(action, "/delete") + "/[0-9]+");
				list.add(pageUrl.replace(action, "/edit") + "/[0-9]+");
				list.add(pageUrl.replace(action, "/view") + "/[0-9]+");
			}
			if(menu.getMenuLinks().size()>0){
				for(MenuLink menuLink : menu.getMenuLinks()){
					//The same parent path/relative to this menu URL
					if(!menuLink.getUrl().startsWith("/")){
						list.add(pageUrl.replace(action, "/" + menuLink.getUrl()));
					}else{
						list.add(pageUrl.replace(menu.getUrl(), menuLink.getUrl()));
					}
				}
			}
		}
	}
	
	private void doError(HttpServletRequest request, HttpServletResponse response, Exception exception) {
		PrintWriter writer = null;
		try {
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			writer = response.getWriter();
			String message = null;
			if(exception!=null){
				if(exception instanceof ConstraintViolationException){
					message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList((ConstraintViolationException)exception, " "), "\n");
				}else{
					message = exception.getMessage();
				}
			}else{
				message = "Unknow Problem";
			}
			String requestType = request.getParameter(REQUEST_AJAX);
			if (StringUtils.isNotBlank(requestType) && AJAX_TYPE_COMMON.equals(requestType)) {
				AjaxUtils.doJsonResponse(response, ("{\"status\":500,\"message\":\"" + message + "\"}"));
			} else {
				writer.println("<html><head>");
				writer.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
				writer.println("<link rel=\"stylesheet\" href=\""
				        + ThemeUtils.getFullCssThemePath(request.getSession()) + "/frame.css\" type=\"text/css\"/>");
				writer.println("</head><body>");
				writer.println("<table id=\"err_table\" width=\"780\" height=\"100%\" align=\"left\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">");
				writer.println("<tr>");
				writer.println("<td style=\"line-height:48px;height:48px;padding-left:350px;\">");
				writer.println("<img src=\"" + ThemeUtils.getFullCssThemePath(request.getSession())
				        + "images/warning.gif\">");
				writer
				        .println("<input type=\"button\" class=\"button\" onclick=\"if (detail_error_msg.style.display == 'none'){detail_error_msg.style.display = '';}else{detail_error_msg.style.display = 'none';}\" value=\"Details\">");
				writer.println("&nbsp;&nbsp;");
				writer
				        .println("<input type=\"button\" class=\"button\" onclick=\"javascript:history.go(-1);\" value=\"back\">");
				writer.println("</td></tr>");
				writer.println("<tr height=30>");
				writer.println("<td style=\"padding-left: 10px;\">");
				writer.println("<b>Error： </b><span>" + message + "</span>");
				writer.println("</td></tr>");
				writer.println("<tr><td height=\"100%\" style=\"padding-left: 10px;\">");
				writer
				        .println("<div id=\"detail_error_msg\" style=\"display:none;position: relative;width:100%;word-break:break-all;\">");
				writer.println("<pre>");
				if (exception != null) {
					exception.printStackTrace(writer);
				}
				writer.println("</pre>");
				writer.println("</div>");
				writer.println("</td></tr>");
				writer.println("</table>");
				writer.println("</body></html>");
			}
		} catch (Exception e) {
			LOGGER.error("doError", e);
		} finally {
			LOGGER.error(request.getRequestURL(), exception);
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}
}
