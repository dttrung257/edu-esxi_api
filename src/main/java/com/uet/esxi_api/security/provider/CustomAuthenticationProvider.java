package com.uet.esxi_api.security.provider;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.uet.esxi_api.entity.WebUser;
import com.uet.esxi_api.exception.user.IncorrectPasswordException;
import com.uet.esxi_api.repository.UserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();
		WebUser user = userRepository.findByUsername(username).orElse(null);
		if (user == null) {
			throw new UsernameNotFoundException("Not found user with username: " +  username);
		}
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new IncorrectPasswordException("Incorrect password");
		}
		return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}

}
