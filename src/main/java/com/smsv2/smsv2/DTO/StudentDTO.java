package com.smsv2.smsv2.DTO;

import javax.validation.constraints.Email;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
	private String email;
	private String password;	
	private String name;
	private String gender;	
	private String role;
	private String reg;	
	private int deptId;
	private int semId;	
	private int userId;
}
