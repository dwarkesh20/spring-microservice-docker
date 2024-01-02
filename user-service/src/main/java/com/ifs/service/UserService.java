package com.ifs.service;

import java.util.List;

import com.ifs.dto.RequestDto;
import com.ifs.dto.ResponseDto;
import com.ifs.entity.User;

public interface UserService {
	ResponseDto createUser(RequestDto newUser);
	
	ResponseDto getUserById(Long id);
	
	List<ResponseDto> getAllUsers();
	
	List<ResponseDto> getUsersByRole(String role);
	
	ResponseDto updateUser(Long id, RequestDto user);
	
	void deleteUser(Long id);
}
