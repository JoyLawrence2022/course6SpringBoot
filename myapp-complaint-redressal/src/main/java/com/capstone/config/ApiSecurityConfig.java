package com.capstone.config;

import com.capstone.filter.JwtAuthenticationFilter;
import com.capstone.services.UserAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserAuthService userAuthService;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private ApiAuthenticationEntryPoint authenticationEntryPoint;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/signin", "/signup", "/addCustomer", "/raiseComplaint", "/manager", "/manager/**", "/engineer/**", "/admin/**", "/customer/**");  //these URLs will be ignored by Spring Security filters
	}
	
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests().antMatchers("/signin", "/signup", "/addCustomer").permitAll()//don't authenticate these requests
				//all other requests are to be authenticated
				.anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public RegistrationBean jwtAuthFilterRegister(JwtAuthenticationFilter filter) {
		FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>(filter);  //used to register jwt filter in servletContext initializer
		registrationBean.setEnabled(false);
		return registrationBean;
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    //return NoOpPasswordEncoder.getInstance();
	    return new BCryptPasswordEncoder();
	}
}
