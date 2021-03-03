package com.github.asirikusal.jdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcQuery {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private List<Map<String, Object>> executeQuery(String sql) {
		List<Map<String, Object>> listOfMap = jdbcTemplate.queryForList(sql);
		return listOfMap;
	}

	public List<Map<String, Object>> selectQueryExecution(String tableName, List<String> selectColumns,
			Map<String, Object> conditions) {
		String columns = selectColumns(selectColumns);
		String condition = selectWhereClause(conditions);
		StringBuilder builder = new StringBuilder();
		builder.append("select " + columns + " from " + tableName + condition);
		System.out.println(builder.toString());
		return executeQuery(builder.toString());
	}

	private String selectColumns(List<String> selectColumns) {
		StringBuilder columns = new StringBuilder();
		boolean flag = false;
		if ((!selectColumns.isEmpty())&&selectColumns != null) {
			for (String column : selectColumns) {
				column = column.replaceAll("([A-Z]+)", "\\_$1").toLowerCase();
				if (flag) {
					columns.append("," + column);
				} else {
					columns.append(column);
					flag = true;
				}
			}
		} else {
			columns.append("*");
		}
		return columns.toString();
	}

	private String selectWhereClause(Map<String, Object> conditions) {
		StringBuilder condition = new StringBuilder();
		if (conditions != null) {
			Map<String, Object> formattedConditions = camelCaseToSnackCase(conditions);
			boolean flag = false;
			for (Map.Entry<String, Object> values : formattedConditions.entrySet()) {
				if (values.getValue() != null) {
					if (values.getValue().getClass().toString().contains("String")||values.getValue().getClass().toString().contains("Date")) {
						if (!flag) {
							condition.append(" where " + values.getKey() + " = \"" + values.getValue()+"\"");
							flag=true;
						} else {
							condition.append(" AND " + values.getKey() + " = '" + values.getValue()+"'");
						}
					}else {
						if (!flag) {
							condition.append(" where " + values.getKey() + " = " + values.getValue());
							flag=true;
						} else {
							condition.append(" AND " + values.getKey() + " = " + values.getValue());
						}
					}
				}
			}
		} else {
			condition.append("");
		}
		return condition.toString();
	}

	private Map<String, Object> camelCaseToSnackCase(Map<String, Object> map) {
		Map<String, Object> newMap = new HashMap<>();
		for (Map.Entry<String, Object> key : map.entrySet()) {
			StringBuilder sb = new StringBuilder(key.getKey());
			String keyString = sb.toString();
			String replacedKey = keyString.replaceAll("([A-Z]+)", "\\_$1").toLowerCase();
			newMap.put(replacedKey, key.getValue());
		}
		return newMap;
	}
}
