package com.mvc.report.fusionchart;

/**
 * @version 1.0
 * @created 2008-06-23 9:10:28
 * @desc 适用于 Column3D.xml Doughnut3D.xml
 */
public class SingleBean {

	private String label;
	private String value;
	private String isSliced="0";
	/**
	 * @return Returns the isSliced.
	 */
	public String getIsSliced() {
		return isSliced;
	}
	/**
	 * @param isSliced The isSliced to set.
	 */
	public void setIsSliced(String isSliced) {
		this.isSliced = isSliced;
	}
	/**
	 * @return Returns the label.
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label The label to set.
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}


}
