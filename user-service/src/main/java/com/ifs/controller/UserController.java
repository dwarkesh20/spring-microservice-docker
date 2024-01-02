package com.ifs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifs.dto.RequestDto;
import com.ifs.dto.ResponseDto;
import com.ifs.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
	private UserService service;
	
	
	@GetMapping("/getall")
	public ResponseEntity<List<ResponseDto>> getUsers() {
		List<ResponseDto> users = service.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseDto> getUserById(@PathVariable Long id) {
		ResponseDto user = service.getUserById(id);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("role/{role}")
	public ResponseEntity<List<ResponseDto>> getUserById(@PathVariable String role) {
		List<ResponseDto> users = service.getUsersByRole(role);
		return ResponseEntity.ok(users);
	}
	
	@PutMapping("/update/{id}")
    public ResponseEntity<ResponseDto> updateInterviewer(@PathVariable Long id,
                                           @RequestBody @Valid RequestDto user){
        ResponseDto updatedUser = service.updateUser(id,user);
        return ResponseEntity.ok(updatedUser);
    }
	
	@DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInterviewer(@PathVariable("id") Long id){
		service.deleteUser(id);
		return ResponseEntity.ok("User successfully deleted!");
    }
	
	
	
}
