package com.example.demo.client;

import com.example.demo.model.GitHubData;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface GitClient {
	
	@GET("/")
	Single<Response<String>> getGitData();

}
