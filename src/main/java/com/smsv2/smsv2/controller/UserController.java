package com.smsv2.smsv2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smsv2.smsv2.DTO.StudentDTO;
import com.smsv2.smsv2.DTO.UserDTO;
import com.smsv2.smsv2.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userservice;

	@PostMapping("/login")
	public ResponseEntity<?> loginStudent(@RequestBody UserDTO userDTO) {

		return new ResponseEntity<>(userservice.login(userDTO), HttpStatus.OK);

	}

	@PostMapping("/verifyEmail")
	public ResponseEntity<?> verifyStudentEmail(@RequestBody UserDTO userDTO) {
		this.userservice.emailVerify(userDTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PutMapping("/updatePhone/{id}")
	public ResponseEntity<?> updatePhoneStudent(@PathVariable int id, @RequestBody UserDTO userDTO) {
		this.userservice.addPhone(id, userDTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PostMapping("/verifyPhone")
	public ResponseEntity<?> verifyStudentPhone(@RequestBody UserDTO userDTO) {
		this.userservice.phoneVerify(userDTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PutMapping("/updateEmail/{id}")
	public ResponseEntity<?> updateStudentEmail(@PathVariable int id, @RequestBody UserDTO userDTO) {
		this.userservice.updateEmail(id, userDTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PutMapping("/updatePassword/{id}")
	public ResponseEntity<?> updateStudentPassword(@PathVariable int id, @RequestBody UserDTO userDTO) {
		this.userservice.updatePassword(id, userDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/forgetPassword")
	public ResponseEntity<?> forgetStudentPassword(@RequestBody UserDTO userDTO) {
		this.userservice.forgetPassword(userDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/sendOtpEmail")
	public ResponseEntity<?> senOtpStudent(@RequestBody UserDTO userDTO) {
		this.userservice.sendOtpTOEmaail(userDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
