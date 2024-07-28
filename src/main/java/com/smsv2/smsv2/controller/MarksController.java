package com.smsv2.smsv2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smsv2.smsv2.DTO.MarksDTO;
import com.smsv2.smsv2.service.MarksService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/marks")
public class MarksController {

	@Autowired
	private MarksService marksservice;
	
	@GetMapping("/allMarks")
	public ResponseEntity<?> allMarks() {
		return new ResponseEntity<>(marksservice.getAllMarks(),HttpStatus.OK);
	}
	
	@GetMapping("/marksById/{id}")
	public ResponseEntity<?> marksById(@PathVariable int id) {
		return new ResponseEntity<>(marksservice.getAllMarksById(id),HttpStatus.OK);
	}
	
	@GetMapping("/marksByReg/{reg}")
	public ResponseEntity<?> marksByReg(@PathVariable String reg) {
		return new ResponseEntity<>(marksservice.getAllMarksByReg(reg),HttpStatus.OK);
	}
	
	@PostMapping("/addmarks/{role}")
	public ResponseEntity<?> addmarks(@RequestBody MarksDTO marksDTO,@PathVariable("role") String role) {
		if(role.equals("teacher")||role.equals("hod")||role.equals("pic")) {
			this.marksservice.addMarks(marksDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);

		}else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);

		}
	}
	
	@PutMapping("/updatemarks/{id}/{role}")
	public ResponseEntity<?> updatemarks(@RequestBody MarksDTO marksDTO,@PathVariable("role") String role,@PathVariable int id) {
		if(role.equals("teacher")||role.equals("hod")||role.equals("pic")) {
			this.marksservice.updateMarkbyId(id, marksDTO);
			return new ResponseEntity<>(HttpStatus.OK);

		}else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);

		}
	}
	
	@DeleteMapping("/deletemarks/{id}/{role}")
	public ResponseEntity<?> deletemarks(@PathVariable("role") String role,@PathVariable int id,@RequestBody MarksDTO marksDTO) {
		if(role.equals("teacher")||role.equals("hod")||role.equals("pic")) {
			this.marksservice.delteMarksById(id,marksDTO);
			return new ResponseEntity<>(HttpStatus.OK);

		}else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);

		}
	}
	
	@DeleteMapping("/deleteAllmarks/{role}")
	public ResponseEntity<?> deleteAllmarks(@PathVariable("role") String role) {
		if(role.equals("pic")) {
			this.marksservice.deleteAllMarks();
			return new ResponseEntity<>(HttpStatus.OK);

		}else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);

		}
	}
	
}
