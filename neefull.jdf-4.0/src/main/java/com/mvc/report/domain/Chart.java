package com.mvc.report.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chart implements Serializable {
	private String title;
	private Float width = 400F;
	private Float height = 300F;
	private String linkUrl;
	private List<DoubleChartData> chartDatas = new ArrayList<DoubleChartData>();
	public String getTitle() {
    	return title;
    }
	public void setTitle(String title) {
    	this.title = title;
    }
	public Float getWidth() {
    	return width;
    }
	public void setWidth(Float width) {
    	this.width = width;
    }
	public Float getHeight() {
    	return height;
    }
	public void setHeight(Float height) {
    	this.height = height;
    }
	public String getLinkUrl() {
    	return linkUrl;
    }
	public void setLinkUrl(String linkUrl) {
    	this.linkUrl = linkUrl;
    }
	public List<DoubleChartData> getChartDatas() {
    	return chartDatas;
    }
	public void setChartDatas(List<DoubleChartData> chartDatas) {
    	this.chartDatas = chartDatas;
    }
}
