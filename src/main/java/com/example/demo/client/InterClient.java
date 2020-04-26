package com.example.demo.client;

import retrofit2.Response;
import retrofit2.http.GET;
import com.example.demo.model.*;

import io.reactivex.Single;

import java.util.*;

public interface InterClient {
	
	@GET("/hello")
	Single<Response<String>> getAllEmployees();
	

}
