package com.mvc.report.domain;

import java.io.Serializable;

public class IntegerChartData implements Serializable {
	private String chartXvalue;
	private Integer chartYvalue;
	private Integer chartSvalue;
	private String labelX;
	private String labelY;
	private String labelS;
	/**
	 * @return the chartXvalue
	 */
	public String getChartXvalue() {
		return chartXvalue;
	}
	/**
	 * @param chartXvalue the chartXvalue to set
	 */
	public void setChartXvalue(String chartXvalue) {
		this.chartXvalue = chartXvalue;
	}
	/**
	 * @return the chartYvalue
	 */
	public Integer getChartYvalue() {
		return chartYvalue;
	}
	/**
	 * @param chartYvalue the chartYvalue to set
	 */
	public void setChartYvalue(Integer chartYvalue) {
		this.chartYvalue = chartYvalue;
	}
	/**
	 * @return the chartSvalue
	 */
	public Integer getChartSvalue() {
		return chartSvalue;
	}
	/**
	 * @param chartSvalue the chartSvalue to set
	 */
	public void setChartSvalue(Integer chartSvalue) {
		this.chartSvalue = chartSvalue;
	}
	/**
	 * @return the labelY
	 */
	public String getLabelY() {
		return labelY;
	}
	/**
	 * @param labelY the labelY to set
	 */
	public void setLabelY(String labelY) {
		this.labelY = labelY;
	}
	/**
	 * @return the labelS
	 */
	public String getLabelS() {
		return labelS;
	}
	/**
	 * @param labelS the labelS to set
	 */
	public void setLabelS(String labelS) {
		this.labelS = labelS;
	}
	public String getLabelX() {
		return labelX;
	}
	public void setLabelX(String labelX) {
		this.labelX = labelX;
	}
}
