package com.ifs.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifs.dto.AuthUser;
import com.ifs.dto.RequestDto;
import com.ifs.dto.ResponseDto;
import com.ifs.exception.InvalidAccessException;
import com.ifs.security.jwt.JwtService;
import com.ifs.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/create-user")
	public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody RequestDto dto){
		ResponseDto user = service.createUser(dto);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> generateToken(@RequestBody AuthUser authUser) throws Exception  {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authUser.getUserName(), authUser.getUserPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		if (authentication.isAuthenticated()) {
			String token = jwtService.generateToken(authUser.getUserName());	
			Map<String, String> response = new HashMap<>();
			response.put("token", token);
			response.put("user", jwtService.extractUsername(token));
			return ResponseEntity.ok(response);
		}
		else{
			throw new InvalidAccessException("Invalid Access");
		}
	}
}
