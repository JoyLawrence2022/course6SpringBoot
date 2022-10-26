package com.capstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableTransactionManagement
public class MyappComplaintRedressalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyappComplaintRedressalApplication.class, args);
		System.out.println("Application has started");
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedMethods("GET","POST","PUT","DELETE")
				.allowedOrigins("http://localhost:4200");
			}
		};
	}

}
