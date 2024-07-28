package com.smsv2.smsv2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smsv2.smsv2.DTO.AdminDTO;
import com.smsv2.smsv2.DTO.StudentDTO;
import com.smsv2.smsv2.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminservice;
	@GetMapping("/allAdmin")
	public ResponseEntity<?> allAdmin() {
		return new ResponseEntity<>(adminservice.getAllAdmin(), HttpStatus.OK);
	}

	@GetMapping("/adminbyId/{id}")
	public ResponseEntity<?> adminbyId(@PathVariable int id) {
		return new ResponseEntity<>(adminservice.getAllAdminById(id), HttpStatus.OK);
	}
	
	@PostMapping("/registeradmin/{role}")
	public ResponseEntity<?> registerStudent(@RequestBody AdminDTO adminDTO,@PathVariable("role") String role) {
		if(role.equals("admin")) {
			this.adminservice.addAdmin(adminDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);

		}

	}
	
	@PutMapping("/updateAdmin/{id}")
	public ResponseEntity<?> updateStudentOthers(@PathVariable int id, @PathVariable("role") String role,
			@RequestBody AdminDTO adminDTO) {
		if (role.equals("admin")) {
			this.adminservice.updateAdmin(id, adminDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);

		}

	}

	@DeleteMapping("/deleteAdminbyId/{id}/{role}")
	public ResponseEntity<?> deleteStudentbyId(@PathVariable int id, @PathVariable("role") String role) {
		if (role.equals("admin")) {
			this.adminservice.delteAdminById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);

		}
	}
}
