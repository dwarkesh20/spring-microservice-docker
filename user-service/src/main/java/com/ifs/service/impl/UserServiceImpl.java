package com.ifs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ifs.dto.RequestDto;
import com.ifs.dto.ResponseDto;
import com.ifs.entity.User;
import com.ifs.mapper.UserMapper;
import com.ifs.repository.UserRepository;
import com.ifs.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public ResponseDto createUser(RequestDto newUser) {
		newUser.setUserPassword(encoder.encode(newUser.getUserPassword()));
		User user = UserMapper.toUser(newUser);

		User savedUser = repository.save(user);

		return UserMapper.toDto(savedUser);
	}

	@Override
	public ResponseDto getUserById(Long id) {
		User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found with Id" + id));

		return UserMapper.toDto(user);
	}

	@Override
	public List<ResponseDto> getAllUsers() {
		List<User> users = repository.findAll();

		return users.stream().map(UserMapper::toDto).toList();
	}

	@Override
	public List<ResponseDto> getUsersByRole(String role) {
		List<User> users = repository.findByUserRole(role);

		return users.stream().map(UserMapper::toDto).toList();
	}

	@Override
	public ResponseDto updateUser(Long id, RequestDto userdto) {
		User extuser = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found with Id" + id));

		extuser.setUserName(userdto.getUserName());
		extuser.setUserEmail(userdto.getUserEmail());
		extuser.setUserContactNo(userdto.getUserContactNo());
		extuser.setUserRole(userdto.getUserRole());
		extuser.setUserDepartment(userdto.getUserDepartment());
		extuser.setUserDesignation(userdto.getUserDesignation());
		
		User updtUser = repository.save(extuser);
		
		return UserMapper.toDto(updtUser);
		
	}

	@Override
	public void deleteUser(Long id) {
		repository.findById(id).orElseThrow(()->new RuntimeException("User not found with Id"+id));
		repository.deleteById(id);
	}

}
