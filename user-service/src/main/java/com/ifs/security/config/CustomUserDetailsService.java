package com.ifs.security.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.ifs.entity.User;
import com.ifs.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws RuntimeException {
		Optional<User> user = userRepository.findByUserName(username);
		return user.map(CustomUserDetails::new)
				.orElseThrow(() -> new RuntimeException(username + " not found in Database!"));

	}

}
