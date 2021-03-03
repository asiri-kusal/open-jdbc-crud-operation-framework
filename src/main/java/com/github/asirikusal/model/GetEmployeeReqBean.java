package com.github.asirikusal.model;

import java.util.Date;

import lombok.Data;

@Data
public class GetEmployeeReqBean {
	
	public Integer id;
	
	public String firstName;
	
	public String lastName;
	
	public Integer salary;
	
	public Date dob;
	
	public Date createdAt;
	
	public Date updatedAt;
}
