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
	public ResponseEntity<?> adminbyId(@PathVariable("id") int id) {
		return new ResponseEntity<>(adminservice.getAllAdminById(id), HttpStatus.OK);
	}

	@PostMapping("/registeradmin")
	public ResponseEntity<?> registerStudent(@RequestBody AdminDTO adminDTO) {
		this.adminservice.addAdmin(adminDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@PutMapping("/updateAdmin/{id}")
	public ResponseEntity<?> updateStudentOthers(@PathVariable("id") int id, @RequestBody AdminDTO adminDTO) {

		this.adminservice.updateAdmin(id, adminDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/deleteAdminbyId/{id}")
	public ResponseEntity<?> deleteStudentbyId(@PathVariable int id,
			@RequestBody AdminDTO adminDTO) {
		
			this.adminservice.delteAdminById(id, adminDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		
	}
}
