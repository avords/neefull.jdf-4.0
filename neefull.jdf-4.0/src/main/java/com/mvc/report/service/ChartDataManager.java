package com.mvc.report.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import com.mvc.report.domain.DoubleChartData;
import com.mvc.report.domain.IntegerChartData;

@Service
public class ChartDataManager {
	@Autowired
	private DataSource dataSource;

	public List<DoubleChartData> searchDoubleChartDataBySql(String sql,Object... args) {
		ParameterizedRowMapper<DoubleChartData> mapper = new ParameterizedRowMapper<DoubleChartData>() {
			public DoubleChartData mapRow(ResultSet rs, int rowNum) throws SQLException {
				DoubleChartData chartData = new DoubleChartData();
				chartData.setChartXvalue(rs.getString(1));
				chartData.setChartYvalue(rs.getDouble(2));
				chartData.setLabelX(rs.getMetaData().getColumnLabel(1));
				chartData.setLabelY(rs.getMetaData().getColumnLabel(2));
				if (rs.getMetaData().getColumnCount() == 3) {
					chartData.setChartSvalue(rs.getDouble(3));
					chartData.setLabelS(rs.getMetaData().getColumnLabel(3));
				}
				return chartData;
			}
		};
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate.query(sql,args,mapper);
	}
	
	public List<IntegerChartData> searchIntegerChartDataBySql(String sql,Object... args) {
		ParameterizedRowMapper<IntegerChartData> mapper = new ParameterizedRowMapper<IntegerChartData>() {
			public IntegerChartData mapRow(ResultSet rs, int rowNum) throws SQLException {
				IntegerChartData chartData = new IntegerChartData();
				chartData.setChartXvalue(rs.getString(1));
				chartData.setChartYvalue(rs.getInt(2));
				chartData.setLabelX(rs.getMetaData().getColumnLabel(1));
				chartData.setLabelY(rs.getMetaData().getColumnLabel(2));
				if (rs.getMetaData().getColumnCount() == 3) {
					chartData.setChartSvalue(rs.getInt(3));
					chartData.setLabelS(rs.getMetaData().getColumnLabel(3));
				}
				return chartData;
			}
		};
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate.query(sql,args,mapper);
	}
}
