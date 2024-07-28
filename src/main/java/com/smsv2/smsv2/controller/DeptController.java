package com.smsv2.smsv2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smsv2.smsv2.DTO.DeptDTO;
import com.smsv2.smsv2.DTO.SemDTO;
import com.smsv2.smsv2.service.DeptService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/dept")
public class DeptController {
	@Autowired
	private DeptService deptservice;
	
	@GetMapping("/allDept")
	public ResponseEntity<?> allDept() {
		return new ResponseEntity<> (deptservice.getAllDept(),HttpStatus.OK);
	}
	
	@GetMapping("/allDeptbyid/{id}")
	public ResponseEntity<?> allDeptbyId(@PathVariable int id) {
		return new ResponseEntity<> (deptservice.getAllDeptById(id),HttpStatus.OK);
	}
	
	@GetMapping("/allDeptbyteacherid/{teacherId}")
	public ResponseEntity<?> allDeptbyTeacherId(@PathVariable int teacherId) {
		return new ResponseEntity<> (deptservice.getAllDeptByTeacherId(teacherId),HttpStatus.OK);
	}
	
	@GetMapping("/allDeptbysemid/{semId}")
	public ResponseEntity<?> allDeptbySemId(@PathVariable int semId) {
		return new ResponseEntity<> (deptservice.getAllDeptBySemId(semId),HttpStatus.OK);
	}
	
	@PostMapping("/addDept/{role}")
	public ResponseEntity<?> addDept(@RequestBody DeptDTO deptDTO, @PathVariable("role") String role) {
		if (role.equals("pic")||role.equals("admin")) {
			this.deptservice.addDept(deptDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
		
	}

	@PutMapping("/updateDept/{id}/{role}")
	public ResponseEntity<?> updateDept(@RequestBody DeptDTO deptDTO, @PathVariable("role") String role,@PathVariable int id) {
		if (role.equals("pic")||role.equals("admin")) {
			this.deptservice.updateDept(id, deptDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
		
	}

	
	@DeleteMapping("/deleteDeptbyId/{id}/{role}")
	public ResponseEntity<?> deleteDeptbyId(@PathVariable("role") String role,@PathVariable int id) {
		if (role.equals("pic")||role.equals("admin")) {
			this.deptservice.delteDeptById(id);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@DeleteMapping("/deleteParticularSemOfDeptbyId/{id}/{role}")
	public ResponseEntity<?> deleteParticularSemOfDeptbyId(@PathVariable("role") String role,@PathVariable int id,@RequestBody DeptDTO deptDTO) {
		if (role.equals("pic")||role.equals("admin")) {
			this.deptservice.delteDeptSemById(id,deptDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@DeleteMapping("/deleteAllDept/{role}")
	public ResponseEntity<?> deleteAllDept(@PathVariable("role") String role) {
		if (role.equals("pic")||role.equals("admin")) {
			this.deptservice.deleteAllDept();
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
		
	}
}

