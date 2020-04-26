package com.example.demo.config;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.client.InterClient;
import com.example.demo.gateway.GitGateway;
import com.example.demo.model.service.PropertiesCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.Interceptor.Chain;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Configuration
public class dummyConfig {
	
	@Value("${base-url}")
	private String url;
	
	@Autowired
	GitGateway gitGateway;
	
	
	//new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
//	.connectTimeout(60*4, TimeUnit.SECONDS).build()
	
	HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
	
	@Bean
	public InterClient client() {
		return new Retrofit.Builder()
				.client(okHttpClient)
				.baseUrl(url)
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			//	.addConverterFactory(JacksonConverterFactory.create(buildDefaultMapper()))
				.addConverterFactory(ScalarsConverterFactory.create())
				.build()
				.create(InterClient.class);				
	}
	
	OkHttpClient okHttpClient = new OkHttpClient.Builder()
			.addInterceptor(new Interceptor() {
				
				@Override
				public Response intercept(Chain chain) throws IOException {
					// TODO Auto-generated method stub
					String token = PropertiesCache.getInstance().getProperty("app.token");
					Request request = chain.request().newBuilder().addHeader("Authorization", token).build();
					System.out.println(request.headers());
					Response response = chain.proceed(request);
					System.out.println(response);
					if(response.code() == 401) {
						System.out.println("response code - "+ response.code());
						response.body().close();
						System.out.println("dummy request successful");
						Response r = null;
						r = makeToken(request, chain, 0);
						return r;
					}
					return response;
				}

				private Response makeToken(Request req, Chain chain, int count) throws IOException {
					// TODO Auto-generated method stub
					if(count>5) {
						Response response = chain.proceed(req);
						return response;
					}
					String gitResponse = gitGateway.gitResponse();
					System.out.println(gitResponse);
					String token = "Basic dmlqYXk6dmlqYXk=";
					PropertiesCache.getInstance().setProperty("app.token", token);
					String newToken = PropertiesCache.getInstance().getProperty("app.token");
					System.out.println(newToken);
					Request newRequest;
					newRequest = req.newBuilder().header("Authorization", newToken).build();
					System.out.println(newRequest.headers());
					Response another = chain.proceed(newRequest);
					if(another.code()!=200 && count<5) {
						System.out.println("count = " +count);
						count++;
						makeToken(newRequest, chain, count);
					}
					return another;
					
				}
			})
			.build();
	
	private ObjectMapper buildDefaultMapper() {
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return objectMapper;
	}
	

}
