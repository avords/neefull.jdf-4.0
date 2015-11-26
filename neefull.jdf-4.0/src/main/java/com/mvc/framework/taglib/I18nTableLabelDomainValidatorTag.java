package com.mvc.framework.taglib;

import java.util.Locale;

import com.mvc.framework.util.MessageUtils;
/**
 * i18N support form validation base on table label
 */
public class I18nTableLabelDomainValidatorTag extends TableLabelDomainValidatorTag {
	
	protected static String noLaterThenToday(Locale locale) {
	    return MessageUtils.getMessage("common.validator.noLaterThanToday",locale);
    }

	protected static String mustBeNumber(Locale locale) {
	    return MessageUtils.getMessage("common.validator.mustBeNumber",locale);
    }

	protected static String mustBeInteger(Locale locale) {
	    return MessageUtils.getMessage("common.validator.mustBeInteger",locale);
    }

	protected static String widthTooLong(Locale locale) {
	    return MessageUtils.getMessage("common.validator.widthTooLong",locale);
    }

	protected static String widthNotEnough(Locale locale) {
	    return MessageUtils.getMessage("common.validator.widthNotEnough",locale);
    }

	protected static String cannontBeBull(Locale locale) {
	    return MessageUtils.getMessage("common.validator.cannotBeNull",locale);
    }
}
