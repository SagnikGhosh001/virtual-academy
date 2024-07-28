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
	public ResponseEntity<?> subbyId(@PathVariable int id) {
		return new ResponseEntity<>(subService.getAllSubById(id), HttpStatus.OK);
	}

	@GetMapping("/subbyteacherId/{id}")
	public ResponseEntity<?> subbyTeaacherIdId(@PathVariable int id) {

		return new ResponseEntity<>(subService.getAllSubByTeacherId(id), HttpStatus.OK);
	}
	
	@GetMapping("/subbysemdeptId/{semId}/{deptId}")
	public ResponseEntity<?> subbySemDeptId(@PathVariable int semId,@PathVariable int deptId) {

		return new ResponseEntity<>(subService.getAllSubBysemdeptId(semId,deptId), HttpStatus.OK);
	}

	@PostMapping("/addsub/{role}")
	public ResponseEntity<?> addSub(@PathVariable("role") String role,@RequestBody SubDTO subDTO) {
		if (role.equals("pic")||role.equals("hod")||role.equals("teacher")) {
			this.subService.addSub(subDTO);
			return new ResponseEntity<>( HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
		
	}
	@PutMapping("/updateSub/{id}/{role}")
	public ResponseEntity<?> updateSub(@PathVariable int id,@PathVariable("role") String role,@RequestBody SubDTO subDTO) {
		if (role.equals("pic")||role.equals("teacher")||role.equals("hod")) {
			this.subService.updateSub(id, subDTO);
			return new ResponseEntity<>( HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	
	
	@DeleteMapping("/deleteSubbyId/{id}/{role}")
	public ResponseEntity<?> deleteSubbyId(@PathVariable int id,@PathVariable("role") String role,@RequestBody SubDTO subDTO) {
		if (role.equals("pic")||role.equals("hod")||role.equals("teacher")) {
			this.subService.delteSubById(id,subDTO);
			return new ResponseEntity<>( HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
		
	}
	@DeleteMapping("/deleteAllSub/{role}")
	public ResponseEntity<?> deleteAllSub(@PathVariable("role") String role) {
		if (role.equals("pic")) {
			this.subService.deleteAllSub();
			return new ResponseEntity<>( HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
		
	}
	@DeleteMapping("/deleteAllSubbyDept/{role}")
	public ResponseEntity<?> deleteAllSubbyDept(@PathVariable("role") String role,@RequestBody int deptId,@RequestBody SubDTO subDTO) {
		if (role.equals("pic")||role.equals("hod")||role.equals("teacher")) {
			this.subService.delteSubByDept(deptId,subDTO);
			return new ResponseEntity<>( HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
		
	}
}
