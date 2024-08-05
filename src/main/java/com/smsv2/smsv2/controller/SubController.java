package com.smsv2.smsv2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smsv2.smsv2.DTO.SubDTO;
import com.smsv2.smsv2.service.SemService;
import com.smsv2.smsv2.service.SubService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/sub")
public class SubController {

	@Autowired
	private SubService subService;

	@GetMapping("/allsub")
	public ResponseEntity<?> allSub() {
		return new ResponseEntity<>(subService.getAllSub(), HttpStatus.OK);
	}

	@GetMapping("/subbyId/{id}")
	public ResponseEntity<?> subbyId(@PathVariable("id") int id) {
		return new ResponseEntity<>(subService.getAllSubById(id), HttpStatus.OK);
	}

	@GetMapping("/subbyteacherId/{id}")
	public ResponseEntity<?> subbyTeaacherIdId(@PathVariable("id") int id) {

		return new ResponseEntity<>(subService.getAllSubByTeacherId(id), HttpStatus.OK);
	}
	
	@GetMapping("/subbysemdeptId/{semId}/{deptId}")
	public ResponseEntity<?> subbySemDeptId(@PathVariable("semId") int semId,@PathVariable("deptId") int deptId) {

		return new ResponseEntity<>(subService.getAllSubBysemdeptId(semId,deptId), HttpStatus.OK);
	}

	@PostMapping("/addsub")
	public ResponseEntity<?> addSub(@RequestBody SubDTO subDTO) {
		this.subService.addSub(subDTO);
			return new ResponseEntity<>( HttpStatus.CREATED);
		
		
	}
	@PutMapping("/updateSub/{id}")
	public ResponseEntity<?> updateSub(@PathVariable("id") int id,@RequestBody SubDTO subDTO) {
			this.subService.updateSub(id, subDTO);
			return new ResponseEntity<>( HttpStatus.OK);
		
		
	}
	
	
	
	
	@DeleteMapping("/deleteSubbyId/{id}")
	public ResponseEntity<?> deleteSubbyId(@PathVariable("id") int id,@RequestBody SubDTO subDTO) {
			this.subService.delteSubById(id,subDTO);
			return new ResponseEntity<>( HttpStatus.OK);
		
		
	}
	@DeleteMapping("/deleteAllSub")
	public ResponseEntity<?> deleteAllSub(@RequestBody SubDTO subDTO) {
			this.subService.deleteAllSub(subDTO);
			return new ResponseEntity<>( HttpStatus.OK);
		
		
	}
	@DeleteMapping("/deleteAllSubbyDept/{deptId}")
	public ResponseEntity<?> deleteAllSubbyDept(@PathVariable int deptId,@RequestBody SubDTO subDTO) {
			this.subService.delteSubByDept(deptId,subDTO);
			return new ResponseEntity<>( HttpStatus.OK);
		
		
	}
}
