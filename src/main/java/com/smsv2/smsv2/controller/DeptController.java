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
	public ResponseEntity<?> allDeptbyId(@PathVariable("id") int id) {
		return new ResponseEntity<> (deptservice.getAllDeptById(id),HttpStatus.OK);
	}
	
	@GetMapping("/allDeptbyteacherid/{teacherId}")
	public ResponseEntity<?> allDeptbyTeacherId(@PathVariable("teacherId") int teacherId) {
		return new ResponseEntity<> (deptservice.getAllDeptByTeacherId(teacherId),HttpStatus.OK);
	}
	
	@GetMapping("/allDeptbysemid/{semId}")
	public ResponseEntity<?> allDeptbySemId(@PathVariable("semId") int semId) {
		return new ResponseEntity<> (deptservice.getAllDeptBySemId(semId),HttpStatus.OK);
	}
	
	@PostMapping("/addDept")
	public ResponseEntity<?> addDept(@RequestBody DeptDTO deptDTO) {

			this.deptservice.addDept(deptDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);

		
	}

	@PutMapping("/updateDept/{id}")
	public ResponseEntity<?> updateDept(@RequestBody DeptDTO deptDTO,@PathVariable("id") int id) {

			this.deptservice.updateDept(id, deptDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		
	}

	
	@DeleteMapping("/deleteDeptbyId/{id}")
	public ResponseEntity<?> deleteDeptbyId(@PathVariable("id") int id,@RequestBody DeptDTO deptDTO) {
	
			this.deptservice.delteDeptById(id,deptDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		
		
	}
	
	@DeleteMapping("/deleteParticularSemOfDeptbyId/{id}")
	public ResponseEntity<?> deleteParticularSemOfDeptbyId(@PathVariable("id") int id,@RequestBody DeptDTO deptDTO) {
			this.deptservice.delteDeptSemById(id,deptDTO);
			return new ResponseEntity<>(HttpStatus.OK);

	}
	
	@DeleteMapping("/deleteAllDept")
	public ResponseEntity<?> deleteAllDept(DeptDTO deptDTO) {
			this.deptservice.deleteAllDept(deptDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		
		
	}
}

