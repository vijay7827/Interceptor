package com.example.demo.gateway;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.client.EmployeeClient;
import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeResponse;

import io.reactivex.Single;
import retrofit2.Response;
@Component
public class EmpGateway {
	
	@Autowired
	private EmployeeClient client;
	
	public List<Employee> getAllEmployees() {
		Response<EmployeeResponse> response = client.getAllEmplyee().blockingGet();
		return response.body().getData();
	//	return null;
		
	}
	
	public String getStatus() {
		Response<EmployeeResponse> response = client.getAllEmplyee().blockingGet();
		return response.body().getStatus();
	}

}
