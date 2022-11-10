package com.uet.esxi_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.uet.esxi_api.security.provider.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	@Autowired
	private AuthenEntryPoint authenEntryPoint;
	
	@Bean
	public AuthenticationManager buildAuthenticationManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.authenticationProvider(customAuthenticationProvider)
				.build();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors()
			.and()
			.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint(authenEntryPoint)
			.accessDeniedHandler(customAccessDeniedHandler)
			.and()
			.authorizeHttpRequests(request -> {
				request.antMatchers("/api/authen/**").permitAll().anyRequest().authenticated();
			});
		return http.build();
	}

}
