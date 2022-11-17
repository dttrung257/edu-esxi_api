package com.uet.esxi_api.security.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.uet.esxi_api.entity.WebUser;
import com.uet.esxi_api.repository.UserRepository;
import com.uet.esxi_api.security.jwt.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Bearer") || header.length() <= 8) {
			filterChain.doFilter(request, response);
			return;
		}
		String jwtToken = header.substring(7).trim();

		if (!jwtUtil.verifyJwtToken(jwtToken)) {
			filterChain.doFilter(request, response);
			return;
		}
		String usename = jwtUtil.getUsernameFromToken(jwtToken);
		WebUser user = userRepository.findByUsername(usename).orElse(null);
		if (user == null) {
			filterChain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null,
				new ArrayList<>());
		token.setDetails(new WebAuthenticationDetails(request));
		SecurityContextHolder.getContext().setAuthentication(token);
		filterChain.doFilter(request, response);
	}
}
