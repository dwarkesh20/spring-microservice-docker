package com.ifs.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ifs.security.config.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	Logger logger = LoggerFactory.getLogger(JwtFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		logger.info("Inside jwt filter");
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String userName = null;
		logger.info(authHeader);
		if(authHeader != null && authHeader.startsWith("Bearer")) {
			token = authHeader.substring(7);
			userName = jwtService.extractUsername(token);
			logger.info("Extrect username form token : {}",userName);
		}
		
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			   
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
			
			if(Boolean.TRUE.equals(jwtService.validateToken(token, userDetails))) {
				
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}
