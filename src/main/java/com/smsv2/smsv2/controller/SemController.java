package com.smsv2.smsv2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smsv2.smsv2.DTO.SemDTO;
import com.smsv2.smsv2.service.SemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/sem")
public class SemController {
	@Autowired
	private SemService semService;

	@GetMapping("/allsem")
	public ResponseEntity<?> getAllSem() {
		return new ResponseEntity<>(semService.getAllSem(), HttpStatus.OK);
	}

	@GetMapping("/allsembyid/{id}")
	public ResponseEntity<?> getAllSembyId(@PathVariable int id) {
		return new ResponseEntity<>(semService.getAllSemById(id), HttpStatus.OK);
	}

	@GetMapping("/allsembyteacherID/{id}")
	public ResponseEntity<?> getAllTeacherId(@PathVariable int id) {
		return new ResponseEntity<>(semService.getAllSemByTeacherId(id), HttpStatus.OK);
	}

	@GetMapping("/allsembydeptID/{id}")
	public ResponseEntity<?> getAllDeptId(@PathVariable int id) {
		return new ResponseEntity<>(semService.getAllSemBydeptId(id), HttpStatus.OK);
	}

	@PostMapping("/addSem/{role}")
	public ResponseEntity<?> addSem(@RequestBody SemDTO semDTO, @PathVariable("role") String role) {
		if (role.equals("pic")||role.equals("admin")) {
			this.semService.addSem(semDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/updateSemName/{id}/{role}")
	public ResponseEntity<?> updateSem(@RequestBody SemDTO semDTO, @PathVariable int id, @PathVariable("role") String role) {
		if (role.equals("pic")||role.equals("admin")) {
			this.semService.updateSem(id, semDTO);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.CREATED);
		}
	}

	@DeleteMapping("/deletesem/{id}/{role}")
	public ResponseEntity<?> deleteSem(@PathVariable int id, @PathVariable("role") String role) {
		if (role.equals("pic")||role.equals("admin")) {
			this.semService.delteSemById(id);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/deleteAllSem/{role}")
	public ResponseEntity<?> deleteAllSem(@PathVariable("role") String role) {
		if (role.equals("pic")||role.equals("admin")) {
			this.semService.deleteAllSem();
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
	}
}
