package com.example.demo.gateway;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.client.InterClient;
import com.example.demo.model.*;

import retrofit2.Response;
@Component
public class DummyGateway {
	
	@Autowired
	InterClient client;
	
	public String getAllEmployees() {
		Response<String> response = client.getAllEmployees().blockingGet();
		if(Objects.nonNull(response)) {
			return response.body();
		}
		else throw new RuntimeException("reponse is null");
		
	}

}
