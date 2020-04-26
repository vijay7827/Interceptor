package com.example.demo.client;

import java.util.List;

import org.springframework.stereotype.Component;
import com.example.demo.model.*;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface EmployeeClient {
	
	@GET("api/v1/employees")
	Single<Response<EmployeeResponse>> getAllEmplyee();

}
