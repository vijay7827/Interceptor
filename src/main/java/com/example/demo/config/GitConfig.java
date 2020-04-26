package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.client.GitClient;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Configuration
public class GitConfig {
	
	@Value("${app.git-url}")
	private String url;
	
	@Bean
	public GitClient gitclient() {
		return new Retrofit.Builder()
				.client(new OkHttpClient.Builder().build())
				.baseUrl(url)
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(ScalarsConverterFactory.create())
				.build()
				.create(GitClient.class);	
	}
	

}
