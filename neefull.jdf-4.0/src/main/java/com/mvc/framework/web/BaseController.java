package com.mvc.framework.web;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.framework.FrameworkConstants;
import com.mvc.framework.dao.HibernateWebUtils;
import com.mvc.framework.model.AbstractEntity;
import com.mvc.framework.service.Manager;
import com.mvc.framework.util.LocaleUtils;
import com.mvc.framework.util.MessageUtils;
import com.mvc.framework.validate.FieldProperty;
import com.mvc.framework.validate.ValidateContainer;

/**
 * Base controller for CRUD operation The entrance is
 * create、edit、save、delete、view The subclass can override
 * handelEdit、handelSave、handelDelete
 * 
 * @author pubx 2010-3-29 02:28:33
 */
public abstract class BaseController<T> {
	
	private Class<T> actualArgumentType;
	
	private boolean isAssignableFromBaseEntity = false;
	
	private String entityName;
	
	public BaseController(){
		try{
			ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
			Type type = genericSuperclass.getActualTypeArguments()[0];
			if (type instanceof Class) {
				this.actualArgumentType = (Class) type;
			} else if (type instanceof ParameterizedType) {
				this.actualArgumentType = (Class) ((ParameterizedType) type).getRawType();
			}
		}catch(Exception e){
		}
		if(actualArgumentType!=null){
			isAssignableFromBaseEntity = AbstractEntity.class.isAssignableFrom(actualArgumentType);
			entityName = HibernateWebUtils.lowerFirstName(getActualArgumentType().getSimpleName());
		}
	}

	protected Class getActualArgumentType() {
		return actualArgumentType;
	}
	
	protected boolean isAssignableFromBaseEntity(){
		return isAssignableFromBaseEntity;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// CustomDateEditor dateEditor = new CustomDateEditor(new
		// SimpleDateFormat("yyyy-MM-dd"), true);
		// Date transformer
		binder.registerCustomEditor(Date.class, new JdfCustomDateEditor(true));
	}

	/**
	 * Go into the create page
	 * 
	 * @param request
	 * @param response
	 * @param t
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/create")
	public String create(HttpServletRequest request, HttpServletResponse response, T t) throws Exception {
		return handleEdit(request, response, null);
	}

	/**
	 * Go into the edit page
	 * 
	 * @param request
	 * @param response
	 * @param id
	 *            primary key
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{objectId}")
	public String edit(HttpServletRequest request, HttpServletResponse response, @PathVariable Long objectId)
			throws Exception {
		return handleEdit(request, response, objectId);
	}

	protected String handleEdit(HttpServletRequest request, HttpServletResponse response, Long objectId)
			throws Exception {
		if (null != objectId) {
			Object entity = getManager().getByObjectId(objectId);
			request.setAttribute("entity", entity);
		}
		return getFileBasePath() + "edit" + getActualArgumentType().getSimpleName();
	}

	/**
	 * Save the submit,and return to it's edit page
	 * 
	 * @param request
	 * @param modelMap
	 * @param t
	 *            Entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, ModelMap modelMap, @Valid T t, BindingResult result)
			throws Exception {
		return handleSave(request, modelMap, t);
	}

	protected String handleSave(HttpServletRequest request, ModelMap modelMap, T t) throws Exception {
		getManager().save(t);
		return "redirect:edit/" + ((AbstractEntity) t).getObjectId() + getMessage("common.base.success", request)
				+ "&" + appendAjaxParameter(request) + "&action=" + request.getParameter("action");
	}

	protected String getMessage(String message) {
		return com.mvc.framework.web.MessageUtils.getMessage(message);
	}

	protected String getMessage(String message, HttpServletRequest request) {
		Locale locale = LocaleUtils.getLocale(request);
		return com.mvc.framework.web.MessageUtils.getMessage(MessageUtils.getMessage(message, locale));
	}

	/**
	 * Save the submit,and return to query page
	 * 
	 * @param request
	 * @param modelMap
	 * @param t
	 *            Entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveToPage")
	public String saveToPage(HttpServletRequest request, ModelMap modelMap, T t) throws Exception {
		return handleSaveToPage(request, modelMap, t);
	}

	protected String handleSaveToPage(HttpServletRequest request, ModelMap modelMap, T t) throws Exception {
		getManager().save(t);
		return "redirect:page" + getMessage("common.base.success", request);
	}

	/**
	 * Save the submit,and return to create page
	 * 
	 * @param request
	 * @param modelMap
	 * @param t
	 *            Entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveToCreate")
	public String saveToCreate(HttpServletRequest request, ModelMap modelMap, T t) throws Exception {
		return handleSaveToPage(request, modelMap, t);
	}

	protected String handleSaveToCreate(HttpServletRequest request, ModelMap modelMap, T t) throws Exception {
		getManager().save(t);
		return "redirect:create" + getMessage("common.base.success", request) + "&" + appendAjaxParameter(request);
	}

	/**
	 * Delete entity by primary key
	 * 
	 * @param request
	 * @param response
	 * @param id
	 *            Primary key
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete/{objectId}")
	public String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable Long objectId)
			throws Exception {
		return handleDelete(request, response, objectId);
	}
	

	protected String handleDelete(HttpServletRequest request, HttpServletResponse response, Long objectId)
			throws Exception {
		getManager().delete(objectId);
		return "redirect:../page" + getMessage("common.base.deleted", request) + "&" + appendAjaxParameter(request);
	}

	@RequestMapping(value = "/jsonDelete/{objectId}")
	@ResponseBody
	public String jsonDelete(HttpServletRequest request, HttpServletResponse response, @PathVariable Long objectId)
			throws Exception {
		WebServiceResult result = new WebServiceResult();
		try{
			getManager().delete(objectId);
			result.setResult("true");
			result.setMessage(MessageUtils.getMessage("common.base.deleted", request));
		}catch(Exception e){
			result.setResult("false");
			result.setMessage(e.getLocalizedMessage());
		}
		return result.toJson();
	}
	
	/**
	 * Go into the view page
	 * 
	 * @param request
	 * @param response
	 * @param id
	 *            Primary key
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/view/{objectId}")
	public String view(HttpServletRequest request, HttpServletResponse response, @PathVariable Long objectId)
			throws Exception {
		return handleView(request, response, objectId);
	}

	protected String handleView(HttpServletRequest request, HttpServletResponse response, Long objectId)
			throws Exception {
		request.setAttribute(FrameworkConstants.VIEW, "1");
		return handleEdit(request, response, objectId);
	}
	
	public String appendAjaxParameter(HttpServletRequest request){
		return "ajax=" + request.getParameter("ajax");
	}
	
	@RequestMapping("isUnique")
	@ResponseBody
	public boolean isUnique(String fieldName, String value, Long objectId){
		Object v = null;
		List<FieldProperty> fieldProperties = ValidateContainer.getAllFieldsOfTheDomain(actualArgumentType.getName());
		for(FieldProperty fieldProperty : fieldProperties){
			if(fieldProperty.getField().getName().equals(fieldName)){
				String type = fieldProperty.getField().getType().getName();
				if(type.equals("java.lang.Long")){
					v  = Long.parseLong(value);
				}else if(type.equals("java.lang.Integer")){
					v = Integer.parseInt(value);
				}else if(type.equals("java.util.Date")){
					JdfCustomDateEditor customDateEditor = new JdfCustomDateEditor(true);
					customDateEditor.setAsText(value);
					v = customDateEditor.getValue();
				}else if(type.equals("java.lang.String")){
					v = value;
				}
				break;
			}
		}
		if(v!=null){
			return getManager().isFieldUnique(fieldName, v, objectId);
		}else{
			return false;
		}
	}
	/**
	 * The lower first name of entity name: user(com.mvc.security.model.User.java), menuLink(com.mvc.security.model.MenuLink.java)
	 * @return entityName
	 */
	public String getEntityName(){
		return entityName;
	}

	/**
	 * Return the entity manager
	 * 
	 * @return
	 */
	public abstract Manager<T> getManager();

	/**
	 * Return the parent path of the JSP page
	 * 
	 * @return
	 */
	public abstract String getFileBasePath();
}
