package com.mvc.framework.validate;

import java.lang.reflect.Field;

public class FieldProperty {
	private String fieldName;
	private String fieldDesc;
	private Field field;
	public Field getField() {
    	return field;
    }

	public FieldProperty(Field field){
		this.field = field;
    	this.fieldName = field.getName();
	}

	public String getFieldName() {
    	return fieldName;
    }
	public String getFieldDesc() {
    	return fieldDesc;
    }

	public void setFieldDesc(String fieldDesc) {
    	this.fieldDesc = fieldDesc;
    }

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
	    return result;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    FieldProperty other = (FieldProperty) obj;
	    if (fieldName == null) {
		    if (other.fieldName != null)
			    return false;
	    } else if (!fieldName.equals(other.fieldName))
		    return false;
	    return true;
    }

}
