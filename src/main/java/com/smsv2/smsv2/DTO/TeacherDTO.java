package com.smsv2.smsv2.DTO;

import javax.validation.constraints.Email;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
	private String email;
	private String name;
	private String gender;
	private String role;
	private String password;
	private int deptId;
	private int semId;
	private int userId;
}
