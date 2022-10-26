package com.capstone.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	//entry point to check if the user is authenticated or throws un-auth ex
	//this method is called when a user tried to access secure rest apis without creds or toeken
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}
}
