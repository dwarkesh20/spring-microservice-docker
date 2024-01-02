package com.ifs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
	private Long userId;
	
	private String userRole;

	private String userName;

	private String userEmail;

	private String userContactNo;
	
	private String userDesignation;

	private String userDepartment;
	
	
}
