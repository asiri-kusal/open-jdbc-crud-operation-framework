package com.github.asirikusal.entity;


import java.util.Date;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@EntityScan
@Table(value = "Jdbc_template")
@Data
public class JdbcTemplates {
	@Id
	@Column(value = "id")
	private Integer id;
	
	@Column(value="name")
	private String name;
	
	@Column(value="email")
	private String email;
	
	@Column(value="created_at")
	private Date createdAt;
	
}
