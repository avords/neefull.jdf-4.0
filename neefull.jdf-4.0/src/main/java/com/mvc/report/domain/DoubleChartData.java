package com.mvc.report.domain;

import java.io.Serializable;

public class DoubleChartData implements Serializable{
	private String chartXvalue;
	private Double chartYvalue;
	private Double chartSvalue;
	private String labelX;
	private String labelY;
	private String labelS;
	public String getChartXvalue() {
    	return chartXvalue;
    }
	public void setChartXvalue(String chartXvalue) {
    	this.chartXvalue = chartXvalue;
    }
	public Double getChartYvalue() {
    	return chartYvalue;
    }
	public void setChartYvalue(Double chartYvalue) {
    	this.chartYvalue = chartYvalue;
    }
	public Double getChartSvalue() {
    	return chartSvalue;
    }
	public void setChartSvalue(Double chartSvalue) {
    	this.chartSvalue = chartSvalue;
    }
	public String getLabelY() {
    	return labelY;
    }
	public void setLabelY(String labelY) {
    	this.labelY = labelY;
    }
	public String getLabelS() {
    	return labelS;
    }
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
