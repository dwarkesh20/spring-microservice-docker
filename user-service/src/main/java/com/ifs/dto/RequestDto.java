package com.ifs.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
	private Long userId;
	
	@NotEmpty(message = "Role Should not be empty/null")
	private String userRole;

	@NotEmpty(message = "Name Should not be empty/null")
	private String userName;
	
	@NotEmpty(message = "Password Should not be empty/null")
	private String userPassword;

	@NotEmpty(message = "Email Should not be empty/null")
	@Email(message = "Email should be in proper format")
	private String userEmail;

	@NotEmpty(message = "Contact No Should not be empty/null")
	private String userContactNo;
	
	@NotEmpty(message = "Designation Should not be empty/null")
	private String userDesignation;

	@NotEmpty(message = "Department Should not be empty/null")
	private String userDepartment;
}
