package com.github.asirikusal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.asirikusal.entity.JdbcTemplates;
import com.github.asirikusal.jdbc.JdbcQuery;
import com.github.asirikusal.model.GetEmployeeReqBean;
import com.github.asirikusal.repo.JdbcTemplateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JdbcTemplateService {

	
	@Autowired
	JdbcTemplateRepo jdbcTemplateRepo;
	
	@Autowired
	JdbcQuery jdbcQuery;
	
	
	public List<JdbcTemplates> fetchAll() {
		return jdbcTemplateRepo.findAll();
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> findselectedEmploye(GetEmployeeReqBean bean){
		ObjectMapper objectMapper=new ObjectMapper();
		List<String> columns=new ArrayList<>();
		columns.add("id");
		Map<String,Object> conditionMap=objectMapper.convertValue(bean, Map.class);
		return jdbcQuery.selectQueryExecution("employee", columns, conditionMap);
	}
}
