package com.ifs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifs.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	List<User> findByUserRole(String userRole);
	
	Optional<User> findByUserName(String userName);
	
//	String findUserRoleByUserName(String userName);
	
}
