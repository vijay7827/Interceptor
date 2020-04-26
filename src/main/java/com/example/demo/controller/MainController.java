package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.gateway.DummyGateway;
import com.example.demo.model.*;
import com.example.demo.model.service.PropertiesCache;

@RestController
@RequestMapping("/api/v1")
public class MainController {
	
	@Autowired
	DummyGateway gateway;
	
	@GetMapping("/employee")
	public String getAllEmployee() {
		return gateway.getAllEmployees();
	}
	
	@GetMapping("/hello")
	public String sayHello() {
		System.out.println(PropertiesCache.getInstance().getProperty("app.token"));
		return "heelo";
	}
	

}
