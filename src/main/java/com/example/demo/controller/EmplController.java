package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.gateway.EmpGateway;
import com.example.demo.model.*;

@RestController
@RequestMapping("/api/v1/emp")
public class EmplController {
	
	@Autowired
	private EmpGateway gateway;
	
	@RequestMapping
	public List<Employee> getEmp() {
		return gateway.getAllEmployees();
	}
	
	@RequestMapping("/status")
	public String getStatus() {
		return gateway.getStatus();
	}

}
