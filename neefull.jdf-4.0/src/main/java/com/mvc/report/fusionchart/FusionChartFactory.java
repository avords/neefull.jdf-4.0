package com.mvc.report.fusionchart;

import java.util.List;

import com.mvc.report.domain.DoubleChartData;

public final class FusionChartFactory {

	private FusionChartFactory(){
	}

	private static final String[] COLOR_ARRAY={"AFD8F8","F6BD0F","8BBA00","A8B6A0",
			"Z6BD0F","F6BD0F","8BBA00","A8B6A0","Z6BD0F"};
	
	public static  String getCol3DLineImg(ImageBean imgBean,List<DoubleChartData> chartDatas,List datasetList)throws Exception{
		StringBuilder result = new StringBuilder(300);
		result.append("<chart caption='").append(imgBean.getCaption()).append("' palette='").append(imgBean.getPalette()).append("' ").append(
		"showvalues='").append(imgBean.getShowvalues()).append("' yAxisValuesPadding='").append(imgBean.getyAxisValuesPadding()).append("'>");
			result.append("<categories>");
			for(DoubleChartData chartData : chartDatas){
				result.append("<category label='").append(chartData.getChartXvalue()).append("' />");
			}
			result.append("</categories>");
			for(int i=0;i<datasetList.size();i++){
				BarGroupOrLine bean=(BarGroupOrLine)datasetList.get(i);
				result.append("<dataset seriesName='").append(bean.getSeriesName()).append("' renderAs='").append(bean.getRenderAs()).append("' >");
				List valueList=bean.getValueList();
				for(int j=0;j<valueList.size();j++){
					result.append("<set value='").append(((String)valueList.get(j))).append("' />");
				}
				result.append("</dataset>");
			}
			result.append("</chart>");
			return result.toString();
	}
	
	static public  String getMSColumn3DImg(ImageBean imgBean,List categoriesList,List datasetList)throws Exception{//
		StringBuilder result = new StringBuilder(300);
		result.append("<chart caption='").append(imgBean.getCaption()).append("' xAxisName='" ).append( imgBean.getxAxisName() ).append( "' yAxisName='" ).append( imgBean.getyAxisName() ).append( "' shownames='").append(imgBean.getShownames()).append("' ")
		.append("showvalues='").append(imgBean.getShowvalues()).append("' formatNumberScale='" ).append( imgBean.getFormatNumberScale() ).append( "' decimals='").append(imgBean.getDecimals()).append("' numberPrefix='").append(imgBean.getNumberPrefix()).append("' bgColor='").append( imgBean.getBgColor() ).append(
		"showborder=").append( imgBean.getShowBorder() ).append( "'>");
			result.append("<categories>");
			for(int i=0;i<categoriesList.size();i++){
				result.append("<category label='").append(((String)categoriesList.get(i))).append("' />");
			}
			result.append("</categories>");
			for(int i=0;i<datasetList.size();i++){
				BarGroupOrLine bean=(BarGroupOrLine)datasetList.get(i);
				result.append("<dataset seriesName='").append(bean.getSeriesName()).append("' color='").append(COLOR_ARRAY[i]).append("' ").append(
				"showValues='").append(bean.getShowValues()).append("' >");
				List valueList=bean.getValueList();
				for(int j=0;j<valueList.size();j++){
					result.append("<set value='").append((valueList.get(j))).append("' />");
				}
				result.append("</dataset>");
			}
			result.append("</chart>");
			return result.toString();
	}
	
	static public  String getPie3DImg(ImageBean imgBean,List<DoubleChartData> chartDatas)throws Exception{
		StringBuilder result = new StringBuilder(300);
		result.append("<chart baseFontSize='12' caption='").append(imgBean.getCaption()).append("' palette='").append(imgBean.getPalette()).append("' decimals='").append(imgBean.getDecimals()).append("' ").append(
			"enableSmartLabels='").append(imgBean.getEnableSmartLabels()).append("' enableRotation='").append(imgBean.getEnableRotation()).append("' ").append(
			"bgColor='").append(imgBean.getBgColor()).append("' bgAlpha='").append(imgBean.getBgAlpha()).append("' bgRatio='").append(imgBean.getBgRatio()).append("' ").append(
			"bgAngle='").append(imgBean.getBgAngle()).append("' showBorder='").append(imgBean.getShowBorder()).append("' startingAngle='").append(imgBean.getStartingAngle()).append("' >");
		for(DoubleChartData chartData : chartDatas){
			result.append("<set label='").append(chartData.getChartXvalue()).append("' value='")
			.append(chartData.getChartYvalue()).append("' isSliced='").append("0").append("' />");
		}
		result.append("</chart>");
		return result.toString();
	}
	
	static public  String getColumn3DImg(ImageBean imgBean,List<DoubleChartData> chartDatas)throws Exception{//
		StringBuilder result = new StringBuilder(300);
		result.append("<chart baseFontSize='12' caption='").append(imgBean.getCaption()).append("' xAxisName='").append(imgBean.getxAxisName())
			  .append("' yAxisName='").append(imgBean.getyAxisName()).append("' showValues='").append(imgBean.getShowvalues())
			  .append("' decimals='").append(imgBean.getDecimals()).append("' formatNumberScale='").append(imgBean.getFormatNumberScale()).append("'>");
		for(DoubleChartData chartData : chartDatas){
			result.append("<set label='").append(chartData.getChartXvalue()).append("' value='").append(chartData.getChartYvalue()).append("' />");
		}
		result.append("</chart>");
		return result.toString();
	}
	
	static public  String getDoughnut3DImgTemp(ImageBean imgBean,String[][] strArray)throws Exception{//
		StringBuilder result = new StringBuilder(300);
		result.append("<chart palette='").append(imgBean.getPalette()).append("' showBorder='").append(imgBean.getShowBorder()).append("'>");
		for(int i=0;i<strArray.length;i++){
			//strXML+="<set label='"+bean.getLabel()+"' value='"+bean.getValue()+"' isSliced='"+bean.getIsSliced()+"' />";
			result.append("<set label='").append(strArray[i][0]).append("' value='").append(strArray[i][1]).append("' isSliced='").append(strArray[i][2]).append("' />");
		}
		result.append("</chart>");
		return result.toString();
	}
	
	static public  String getDoughnut3DImg(ImageBean imgBean,List list)throws Exception{//
		StringBuilder strXML = new StringBuilder(300);
		strXML.append("<chart palette='").append(imgBean.getPalette()).append("' showBorder='").append(imgBean.getShowBorder()).append("'>");
		for(int i=0;i<list.size();i++){
			SingleBean bean=(SingleBean)list.get(i);
			if(bean.getValue()!=null&&!bean.getValue().equals(""))
			strXML.append("<set label='").append(bean.getLabel()).append("' value='").append(bean.getValue()).append("' isSliced='").append(bean.getIsSliced()).append("' />");
		}
		strXML.append("</chart>");
		return strXML.toString();
	}
	
	static public  String getCol3DLineDYImg(ImageBean imgBean,List categoriesList,List datasetList,List zheList)throws Exception{//
		StringBuilder result = new StringBuilder(1000);
		result.append("<chart palette='").append(imgBean.getPalette()).append("'").append(
		" caption='").append(imgBean.getCaption()).append("' shownames='").append(imgBean.getShownames()).append("' showvalues='0' numberPrefix='").append(imgBean.getNumberPrefix()).append("'").append(
		" sYAxisValuesDecimals='").append(imgBean.getsYAxisValuesDecimals()).append("' connectNullData='").append(imgBean.getConnectNullData()).append("'").append(
		" PYAxisName='").append(imgBean.getPYAxisName()).append("' SYAxisName='").append(imgBean.getSYAxisName()).append("' numDivLines='").append(imgBean.getNumDivLines()).append("' ").append(
		" formatNumberScale='").append(imgBean.getFormatNumberScale()).append("' bgColor='" ).append( imgBean.getBgColor() ).append( "' XAxisName='" ).append( imgBean.getxAxisName() ).append( "' sNumberSuffix='%25'>");
			result.append("<categories>");
			for(int i=0;i<categoriesList.size();i++){
				result.append("<category label='").append(((String)categoriesList.get(i))).append("' />");
			}
			result.append("</categories>");
			for(int i=0;i<datasetList.size();i++){
				BarGroupOrLine bean=(BarGroupOrLine)datasetList.get(i);
				result.append("<dataset seriesName='").append(bean.getSeriesName()).append("' color='").append(COLOR_ARRAY[i]).append("' ").append(
				"showValues='").append(bean.getShowValues()).append("'>");
				List valueList=bean.getValueList();
				for(int j=0;j<valueList.size();j++){
					result.append("<set value='").append(valueList.get(j)).append("' />");
				}
				result.append("</dataset>");
			}
			for(int i=0;i<zheList.size();i++){
				BarGroupOrLine bean=(BarGroupOrLine)zheList.get(i);
				result.append("<dataset seriesName='").append(bean.getSeriesName()).append("' color='").append(COLOR_ARRAY[i]).append("' ").append(
				"showValues='").append(bean.getShowValues()).append("' parentYAxis='").append(bean.getParentYAxis()).append("'>");
				List valueList=bean.getValueList();
				for(int j=0;j<valueList.size();j++){
					result.append("<set value='").append(valueList.get(j)).append("' />");
				}
				result.append("</dataset>");
			}
			result.append("</chart>");
			return result.toString();
	}
	
    /**
     * Creates the Chart HTML+Javascript to create the FusionCharts object with the given parameters.
     * This method uses JavaScript to overcome the IE browser problem with SWF wherein you have to 'Click to activate' the control
     * @param chartSWF - SWF File Name (and Path) of the chart which you intend to plot
     * @param strURL - If you intend to use dataURL method for this chart, pass the URL as this parameter. Else, set it to "" (in case of dataXML method)
     * @param strXML - If you intend to use dataXML method for this chart, pass the XML data as this parameter. Else, set it to "" (in case of dataURL method)
     * @param chartId - Id for the chart, using which it will be recognized in the HTML page. Each chart on the page needs to have a unique Id.
     * @param chartWidth - Intended width for the chart (in pixels)
     * @param chartHeight - Intended height for the chart (in pixels)
     * @param debugMode - Whether to start the chart in debug mode
     * @param registerWithJS - Whether to ask chart to register itself with JavaScript
     */
    public String createChart(String chartSWF, String strURL, String strXML,
	    String chartId, int chartWidth, int chartHeight, boolean debugMode,
	    boolean registerWithJS) {
		StringBuilder strBuf = new StringBuilder();
		/*
		First we create a new DIV for each chart. We specify the name of DIV as "chartId"Div.
		DIV names are case-sensitive.
		*/
		strBuf.append("<!--START Script Block for Chart -->\n");
		strBuf.append("\t\t<div id='").append(chartId).append( "Div' align='center'>\n");
		strBuf.append("\t\t\t\tChart.\n");

		/*The above text "Chart" is shown to users before the chart has started loading
		 (if there is a lag in relaying SWF from server). This text is also shown to users
		 who do not have Flash Player installed. You can configure it as per your needs.*/

		strBuf.append("\t\t</div>\n");

		/*Now, we render the chart using FusionCharts Class. Each chart's instance (JavaScript) Id
		 is named as chart_"chartId".*/

		strBuf.append("\t\t<script type='text/javascript'>\n");
		//Instantiate the Chart
		Boolean registerWithJSBool = registerWithJS;
		Boolean debugModeBool = Boolean.valueOf(debugMode);
		int regWithJSInt = boolToNum(registerWithJSBool);
		int debugModeInt = boolToNum(debugModeBool);

		strBuf.append("\t\t\t\tvar chart_").append(chartId).append(" = new FusionCharts('")
			.append(chartSWF).append( "', '" ).append(chartId).append( "', '" ).append(chartWidth )
			.append( "', '").append( chartHeight).append( "', '").append(debugModeInt).append("', '")
			.append(regWithJSInt).append("');\n");
		//Check whether we've to provide data using dataXML method or dataURL method
		if (strXML.equals("")) {
		    strBuf.append("\t\t\t\t//Set the dataURL of the chart\n");
		    strBuf.append("\t\t\t\tchart_").append(chartId).append(".setDataURL(\"")
		    .append(strURL).append("\");\n");
		} else {
		    strBuf.append("\t\t\t\t//Provide entire XML data using dataXML method\n");
		    strBuf.append("\t\t\t\tchart_").append(chartId).append(".setDataXML(\"")
		    .append(strXML).append("\");\n");
		}
		strBuf.append("\t\t\t\t//Finally, render the chart.\n");
		strBuf.append("\t\t\t\tchart_" ).append( chartId ).append( ".render(\"" ).append( chartId ).append( "Div\");\n");
		strBuf.append("\t\t</script>\n");
		strBuf.append("\t\t<!--END Script Block for Chart-->\n");
		return strBuf.substring(0);
    }
    /**
     * Encodes the dataURL before it's served to FusionCharts.
     * If you have parameters in your dataURL, you necessarily need to encode it.
     * @param strDataURL - dataURL to be fed to chart
     * @param addNoCacheStr - Whether to add aditional string to URL to disable caching of data
     * @return


    public String encodeDataURL(String strDataURL, String addNoCacheStr,
	    HttpServletResponse response) {
		String encodedURL = strDataURL;
		//Add the no-cache string if required
		if (addNoCacheStr.equals("true")) {
		    //We add ?FCCurrTime=xxyyzz
		    //If the dataURL already contains a ?, we add &FCCurrTime=xxyyzz
		    //We send the date separated with '_', instead of the usual ':' as FusionCharts cannot handle : in URLs
		    java.util.Calendar nowCal = java.util.Calendar.getInstance();
		    java.util.Date now = nowCal.getTime();
		    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
			    "MM/dd/yyyy HH_mm_ss a");
		    String strNow = sdf.format(now);
		    if (strDataURL.indexOf("?") > 0) {
			encodedURL = strDataURL + "&FCCurrTime=" + strNow;
		    } else {
			strDataURL = strDataURL + "?FCCurrTime=" + strNow;
		    }
		    encodedURL = response.encodeURL(strDataURL);

		}
		return encodedURL;
    }
    */
    /**
     * Creates the Chart HTML to embed the swf object with the given parameters
     * @param chartSWF - SWF File Name (and Path) of the chart which you intend to plot
     * @param strURL - If you intend to use dataURL method for this chart, pass the URL as this parameter. Else, set it to "" (in case of dataXML method)
     * @param strXML - If you intend to use dataXML method for this chart, pass the XML data as this parameter. Else, set it to "" (in case of dataURL method)
     * @param chartId - Id for the chart, using which it will be recognized in the HTML page. Each chart on the page needs to have a unique Id.
     * @param chartWidth - Intended width for the chart (in pixels)
     * @param chartHeight - Intended height for the chart (in pixels)
     * @param debugMode - Whether to start the chart in debug mode
     */

    public String createChartHTML(String chartSWF, String strURL,
	    String strXML, String chartId, int chartWidth, int chartHeight,
	    boolean debugMode) {
		/*Generate the FlashVars string based on whether dataURL has been provided
	     or dataXML.*/
		StringBuilder strFlashVars = new StringBuilder(300);
		Boolean debugModeBool = debugMode;

		if (strXML.equals("")) {
		    //DataURL Mode
		    strFlashVars.append("chartWidth=").append( chartWidth ).append( "&chartHeight="
			    ).append( chartHeight ).append( "&debugMode=" ).append( boolToNum(debugModeBool)
			    ).append( "&dataURL=" ).append( strURL );
		} else {
		    //DataXML Mode
		    strFlashVars.append("chartWidth=").append(chartWidth ).append( "&chartHeight="
			    ).append( chartHeight ).append( "&debugMode=" ).append( boolToNum(debugModeBool)
			    ).append( "&dataXML=" ).append( strXML );
		}
		StringBuilder strBuf = new StringBuilder(300);

		// START Code Block for Chart
		strBuf.append("\t\t<!--START Code Block for Chart-->\n");
		strBuf
			.append("\t\t\t\t<object classid='clsid:d27cdb6e-ae6d-11cf-96b8-444553540000' codebase='http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0' width='"
				).append( chartWidth
				).append( "' height='"
				).append( chartHeight
				).append( "' id='"
				).append( chartId ).append( "'>\n");
		strBuf.append("\t\t\t\t	<param name='allowScriptAccess' value='always' />\n");
		strBuf.append("\t\t\t\t	<param name='movie' value='" ).append( chartSWF ).append( "'/>\n");
		strBuf.append("\t\t\t\t<param name='FlashVars' value=\"" ).append( strFlashVars).append( "\" />\n");
		strBuf.append("\t\t\t\t	<param name='quality' value='high' />\n");
		strBuf
			.append("\t\t\t\t<embed src='"
				).append( chartSWF
				).append( "' FlashVars=\""
				).append( strFlashVars).append( "\" quality='high' width='"
				).append( chartWidth
				).append( "' height='"
				).append( chartHeight
				).append( "' name='"
				).append( chartId
				).append( "' allowScriptAccess='always' type='application/x-shockwave-flash' pluginspage='http://www.macromedia.com/go/getflashplayer' />\n");
		strBuf.append("\t\t</object>\n");
		// END Code Block for Chart
		strBuf.append("\t\t<!--END Code Block for Chart-->\n");
		return strBuf.toString();
    }

    /**
     * Converts boolean to corresponding integer
     * @param bool - The boolean that is to be converted to number
     * @return int - 0 or 1 representing the given boolean value
     */
    public int boolToNum(Boolean bool) {
		int num = 0;
		if (bool.booleanValue()) {
		    num = 1;
		}
		return num;
    }
}
