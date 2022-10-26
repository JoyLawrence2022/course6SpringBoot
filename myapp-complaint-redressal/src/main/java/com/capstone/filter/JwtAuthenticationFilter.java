package com.capstone.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capstone.exceptions.JwtTokenMissingException;
import com.capstone.services.UserAuthService;
import com.capstone.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	//this class provides chain for accessing secure resources
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserAuthService userAuthService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		System.out.println("Hello! from JwtAuthenticationFilter.java");
		System.out.println(header);

		if (header == null || !header.startsWith("HTTP_TOKEN")) {
			throw new JwtTokenMissingException("No JWT token found in the request headers");
		}

		String token = header.substring("HTTP_TOKEN".length() + 1);

		// Optional - verification
		jwtUtil.validateToken(token);

		String userName = jwtUtil.getUserName(token);
		System.out.println("Name of user is "+userName);

		// loaded user details from database and set authentication into SecurityContext.
		
		UserDetails userDetails = userAuthService.loadUserByUsername(userName);

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
		//UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new 
		//		UsernamePasswordAuthenticationToken(userDetails, null);

		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}

		filterChain.doFilter(request, response);
	}

}
