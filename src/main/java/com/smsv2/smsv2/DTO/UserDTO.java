package com.smsv2.smsv2.DTO;

import javax.validation.constraints.Email;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private String email;
	private String phone;
	private String password;
	private String otp;
	private String changePassword;
	private int currentUserId;
}
