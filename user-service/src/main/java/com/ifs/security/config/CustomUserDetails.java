package com.ifs.security.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ifs.entity.User;

@SuppressWarnings("serial")
public class CustomUserDetails implements UserDetails {

	private String userName;
	private String userPassword;
	private List<SimpleGrantedAuthority> authorities;

	public CustomUserDetails(User user) {
		userName = user.getUserName();
		userPassword = user.getUserPassword();
		authorities = Arrays.stream(user.getUserRole().split(",")).map(SimpleGrantedAuthority::new).toList();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return userPassword;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
