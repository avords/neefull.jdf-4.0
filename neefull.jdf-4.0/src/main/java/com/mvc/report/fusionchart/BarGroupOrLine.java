package com.mvc.report.fusionchart;

import java.util.List;

public class BarGroupOrLine {

	private String seriesName;
	private String color;
	private String showValues="0" ;
	private String parentYAxis="0";
	private String renderAs="";//Col3DLine
	private List valueList;


	/**
	 * @return Returns the renderAs.
	 */
	public String getRenderAs() {
		return renderAs;
	}
	/**
	 * @param renderAs The renderAs to set.
	 */
	public void setRenderAs(String renderAs) {
		this.renderAs = renderAs;
	}
	/**
	 * @return Returns the valueList.
	 */
	public List getValueList() {
		return valueList;
	}
	/**
	 * @param valueList The valueList to set.
	 */
	public void setValueList(List valueList) {
		this.valueList = valueList;
	}
	/**
	 * @return Returns the color.
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color The color to set.
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @return Returns the parentYAxis.
	 */
	public String getParentYAxis() {
		return parentYAxis;
	}
	/**
	 * @param parentYAxis The parentYAxis to set.
	 */
	public void setParentYAxis(String parentYAxis) {
		this.parentYAxis = parentYAxis;
	}
	/**
	 * @return Returns the seriesName.
	 */
	public String getSeriesName() {
		return seriesName;
	}
	/**
	 * @param seriesName The seriesName to set.
	 */
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	/**
	 * @return Returns the showValues.
	 */
	public String getShowValues() {
		return showValues;
	}
	/**
	 * @param showValues The showValues to set.
	 */
	public void setShowValues(String showValues) {
		this.showValues = showValues;
	}


}
