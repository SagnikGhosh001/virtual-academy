package com.smsv2.smsv2.service;

import java.util.Map;

import com.smsv2.smsv2.DTO.StudentDTO;
import com.smsv2.smsv2.DTO.UserDTO;
import com.smsv2.smsv2.entity.LoginResponse;
import com.smsv2.smsv2.entity.User;

public interface UserService {
	LoginResponse login(UserDTO userDTO);

	void emailVerify(UserDTO userDTO);

	void updateEmail(int id, UserDTO userDTO);

	// update Student
	void addPhone(int id, UserDTO userDTO);

	// phone verify
	void phoneVerify(UserDTO userDTO);

	// send otp to email
	void sendOtpTOEmaail(UserDTO email);

	// update Student password
	void updatePassword(int id, UserDTO userDTO);

	// forget password
	void forgetPassword(UserDTO userDTO);

}
