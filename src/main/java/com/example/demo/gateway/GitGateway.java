package com.example.demo.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.client.GitClient;

import retrofit2.Response;

@Component
public class GitGateway {
	
	@Autowired
	GitClient client;
	
	public String gitResponse() {
		return client.getGitData().blockingGet().body();
	}

}
