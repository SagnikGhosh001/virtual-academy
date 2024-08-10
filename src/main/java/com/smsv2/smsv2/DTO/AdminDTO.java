package com.smsv2.smsv2.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {
	private String email;
	private String password;	
	private String name;
	private String gender;	
	private String role;
	private String phone;
	private int userid;
}
