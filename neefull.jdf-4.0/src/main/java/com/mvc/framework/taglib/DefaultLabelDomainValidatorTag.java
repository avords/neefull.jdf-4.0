package com.mvc.framework.taglib;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Id;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.mvc.framework.validate.FieldProperty;
import com.mvc.framework.validate.ValidateContainer;

public class DefaultLabelDomainValidatorTag extends DomainValidateTag {
	private static final int DEFAULT_SCRIPT_LENGTH = 100;
	private static final long serialVersionUID = -1951060861357104605L;
	private static final Logger LOGGER = Logger.getLogger(DefaultLabelDomainValidatorTag.class);
	private static final String GET_ELEMENT_BY_ID_BEGIN = "$('#";
	private static final String GET_ELEMENT_BY_ID_END = "')";
	private static final String GET_ELEMENT_BY_NAME_BEGIN = "$('[name=\"";
	private static final String GET_ELEMENT_BY_NAME_END = "\"]')";
	private static final String LENGTH = ".length";
	private static final String FIELD_PREFFIX = "f_";
	private static final String FIELD_VALID_PREFFIX = "f_v_";
	private static final String FIELD_VALID_VALUE_PREFFIX = "f_v_v_";
	private static final String FORM_NAME = "f_0";
	private static final String VALIDATE_RESULT = "f_v_r";
	private static final String VALIDATE_RESULT_APPEND = VALIDATE_RESULT + "+=";
	private static final String DECORATOR_FORM_NAME = "d_f()";
	private static final String FIELD_LABEL = "f_l_";
	private static final String FIELD_LABEL_NAME = "f_l_n_";

	private static final ConcurrentHashMap<String,String> VALIDATE_SCRIPTS = new ConcurrentHashMap<String, String>();

	public static String getValidateScripts(String domainName){
		String result =  VALIDATE_SCRIPTS.get(domainName);
		if(null==result){
			result = generateScripts(domainName);
			VALIDATE_SCRIPTS.put(domainName, result);
		}
		return result;
	}

	private static String generateScripts(String domainName){
		List<FieldProperty> fieldProperties = new ArrayList<FieldProperty>();
		domainName = domainName.trim();
		if(!"".equals(domainName)){
			String[] domains = domainName.split(",");
			domainName = domains[0];
			for(String entity : domains){
				List<FieldProperty> properties = ValidateContainer.getAllFieldsOfTheDomain(entity);
				if(null!=properties){
					fieldProperties.addAll(properties);
				}
			}
		}
		String simpleName = domainName.substring(domainName.lastIndexOf(".") + 1);
		if (fieldProperties.size() > 0) {
			StringBuilder validate = new StringBuilder(DEFAULT_SCRIPT_LENGTH);
			StringBuilder decorator = new StringBuilder(DEFAULT_SCRIPT_LENGTH);
			validate.append("$(document).ready(function(){");
			validate.append("var ").append(FORM_NAME).append("=").append(GET_ELEMENT_BY_ID_BEGIN).append(simpleName).append(GET_ELEMENT_BY_ID_END).append(";");
			validate.append("if(").append(FORM_NAME).append(".val() == undefined){\n");
			validate.append(FORM_NAME).append("=$(\"form:first\");\n");
			validate.append("}\n");
			validate.append("if(").append(FORM_NAME).append(" != undefined){\n");
			validate.append(DECORATOR_FORM_NAME).append(";\n");
			validate.append(FORM_NAME).append(".bind('submit', function(event){return check").append(simpleName).append("();});\n");
			validate.append("}\n");
			validate.append("});");
			decorator.append("function ").append(DECORATOR_FORM_NAME).append("{\n");
			validate.append("var ").append(VALIDATE_RESULT).append(";\n");
			validate.append(" function check" + simpleName + "(){\n");
			validate.append(VALIDATE_RESULT).append("='';\n");
			for (int i=0,n=fieldProperties.size();i<n;i++) {
				 FieldProperty fieldProperty = fieldProperties.get(i);
				String currFieldName = fieldProperty.getFieldName();
				Field field = fieldProperty.getField();
				// Not null
				validate.append("var ").append(FIELD_PREFFIX).append(i).append("=")
					.append(GET_ELEMENT_BY_NAME_BEGIN).append(currFieldName).append(GET_ELEMENT_BY_NAME_END).append(";\n");
				validate.append("if(isNeedValidate(").append(FIELD_PREFFIX).append(i).append(")){\n");
				validate.append("var ").append(FIELD_VALID_VALUE_PREFFIX).append(i).append("=getElementValue(").append(FIELD_PREFFIX).append(i).append(");\n");
				validate.append("var ").append(FIELD_VALID_PREFFIX).append(i).append("=true;\n");

				validate.append("if(").append(FIELD_PREFFIX).append(i).append(".length>1){\n");
				validate.append(FIELD_PREFFIX).append(i).append("=").append(FIELD_PREFFIX).append(i).append(".first();\n");
				validate.append("}\n");
				validate.append("var ").append(FIELD_LABEL).append(i).append("=").append(FIELD_PREFFIX).append(i).append(".prev();\n");

				validate.append("var ").append(FIELD_LABEL_NAME).append(i).append("=").append(FIELD_LABEL).append(i).append(".text();\n");
				validate.append(FIELD_LABEL_NAME).append(i).append("=").append(FIELD_LABEL_NAME).append(i)
						.append(".substring(0,").append(FIELD_LABEL_NAME).append(i).append(".length-1)\n");
				if (field.isAnnotationPresent(NotNull.class)||field.isAnnotationPresent(NotEmpty.class)||field.isAnnotationPresent(Id.class)) {
					validate.append("if(").append(FIELD_VALID_VALUE_PREFFIX).append(i).append("==\"\"){\n");
					validate.append(FIELD_VALID_PREFFIX).append(i).append("=false;\n");
					validate.append(VALIDATE_RESULT_APPEND).append(FIELD_LABEL_NAME).append(i).append("+'不可以为空!\\n';\n}\n");
					decorator.append("var ").append(FIELD_PREFFIX).append(i).append("=")
					.append(GET_ELEMENT_BY_NAME_BEGIN).append(currFieldName).append(GET_ELEMENT_BY_NAME_END).append(";\n");
					decorator.append("if(isNeedValidate(").append(FIELD_PREFFIX).append(i).append(")){\n");
					decorator.append("if(").append(FIELD_PREFFIX).append(i).append(".length>1){\n");
					decorator.append(FIELD_PREFFIX).append(i).append("=").append(FIELD_PREFFIX).append(i).append(".first();");
					decorator.append("}\n");
					decorator.append(FIELD_PREFFIX).append(i).append(".prev().css('color','red').addClass(\"inputReminder\")");
					decorator.append("}\n");
				}

				//Length
				Length isIntegerAnnotation = field.getAnnotation(Length.class);
				if (null!=isIntegerAnnotation) {
					int minWidth = isIntegerAnnotation.min();
					int maxWidth = isIntegerAnnotation.max();
					validate.append("if(").append(FIELD_VALID_PREFFIX).append(i).append("){\n");
					if (minWidth > 0) {
						validate.append("if(").append(FIELD_VALID_VALUE_PREFFIX).append(i).append(LENGTH).append("<").append(minWidth + "){\n");
						validate.append(FIELD_VALID_PREFFIX).append(i).append("=false;\n");
						validate.append(VALIDATE_RESULT_APPEND).append(FIELD_LABEL_NAME).append(i).append("+'宽度不够!\\n';}\n");
					}
					if (maxWidth < Integer.MAX_VALUE) {
						validate.append("if(").append(FIELD_VALID_VALUE_PREFFIX).append(i).append(LENGTH).append(">").append(maxWidth).append("){\n");
						validate.append(FIELD_VALID_PREFFIX).append(i).append("=false;\n");
					    validate.append(VALIDATE_RESULT_APPEND).append(FIELD_LABEL_NAME).append(i).append("+'宽度太长！\\n';}\n");
					}
					validate.append("}\n");
				}
				Class type = field.getType();
				if(type == Integer.class||type == Long.class){
					validate.append("if(").append(FIELD_VALID_PREFFIX).append(i).append("){\n");
					validate.append("if(!isNull(").append(FIELD_VALID_VALUE_PREFFIX).append(i).append(")&&!isInteger(").append(FIELD_VALID_VALUE_PREFFIX).append(i).append(")){\n");
					validate.append(FIELD_VALID_PREFFIX).append(i).append("=false;\n");
				    validate.append(VALIDATE_RESULT_APPEND).append(FIELD_LABEL_NAME).append(i).append("+'只能是整数！\\n';}\n");
					validate.append("}\n");
				}else if(type == Double.class ||type==Float.class){
					validate.append("if(").append(FIELD_VALID_PREFFIX).append(i).append("){\n");
					validate.append("if(!isNull(").append(FIELD_VALID_VALUE_PREFFIX).append(i).append(")&&(!isInteger(").append(FIELD_VALID_VALUE_PREFFIX).append(i).append(")&&!isFloat(").append(FIELD_VALID_VALUE_PREFFIX).append(i).append("))){\n");
					validate.append(FIELD_VALID_PREFFIX).append(i).append("=false;\n");
				    validate.append(VALIDATE_RESULT_APPEND).append(FIELD_LABEL_NAME).append(i).append("+'只能是数字！\\n';}\n");
					validate.append("}\n");
				}
				//Date
				Future future = field.getAnnotation(Future.class);
				if (null!=future) {
					validate.append("if(").append(FIELD_VALID_PREFFIX).append(i).append("){\n");
					validate.append("if(strToDate(").append(FIELD_VALID_VALUE_PREFFIX).append(i).append(")<= new Date()){\n");
					validate.append(FIELD_VALID_PREFFIX).append(i).append("=false;\n");
					validate.append(VALIDATE_RESULT_APPEND).append(FIELD_LABEL_NAME).append(i).append("+'不能晚于今天!\\n';}\n");
					validate.append("}\n");
				}
				validate.append(FIELD_PREFFIX).append(i).append(".removeClass();\n");
				validate.append("if(!").append(FIELD_VALID_PREFFIX).append(i).append("){\n");

				validate.append(FIELD_PREFFIX).append(i).append(".addClass(\"onError\");\n");
				validate.append("}else{\n");
				validate.append(FIELD_PREFFIX).append(i).append(".addClass(\"onSuccess\");\n}\n");
				validate.append("}\n");
			}
			decorator.append("}\n");
			validate.append(" try{\n cumstomerFormValidate();\n}catch(e){}\n");
			validate.append(" if(").append(VALIDATE_RESULT).append("!= ''){\n alert(").append(VALIDATE_RESULT).append(");\n return false;\n}else{\n");
			validate.append("return true;\n}\n}\n");
			return validate.toString() + decorator.toString();
		}
		return "";
	}

	int validate() throws JspTagException {
		if(null!=domain){
			String scripts = getValidateScripts(domain);
			try {
				JspWriter out = pageContext.getOut();
				out.print("<script type=\"text/javascript\">\n");
				out.write(scripts);
				out.print("</script>\n");
			} catch (IOException e) {
				LOGGER.error("validate()", e);
			}
		}
		return EVAL_PAGE;
	}
}
