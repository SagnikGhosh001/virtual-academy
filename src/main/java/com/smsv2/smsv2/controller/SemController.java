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
	public ResponseEntity<?> getAllSembyId(@PathVariable("id") int id) {
		return new ResponseEntity<>(semService.getAllSemById(id), HttpStatus.OK);
	}

	@GetMapping("/allsembyteacherID/{teacherid}")
	public ResponseEntity<?> getAllTeacherId(@PathVariable("teacherid") int id) {
		return new ResponseEntity<>(semService.getAllSemByTeacherId(id), HttpStatus.OK);
	}

	@GetMapping("/allsembydeptID/{deptid}")
	public ResponseEntity<?> getAllDeptId(@PathVariable("deptid") int id) {
		return new ResponseEntity<>(semService.getAllSemBydeptId(id), HttpStatus.OK);
	}

	@PostMapping("/addSem")
	public ResponseEntity<?> addSem(@RequestBody SemDTO semDTO) {

		this.semService.addSem(semDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@PutMapping("/updateSemName/{id}")
	public ResponseEntity<?> updateSem(@RequestBody SemDTO semDTO, @PathVariable("id") int id) {
		this.semService.updateSem(id, semDTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@DeleteMapping("/deletesem/{id}")
	public ResponseEntity<?> deleteSem(@PathVariable("id") int id, @RequestBody SemDTO semDTO) {
		this.semService.delteSemById(id, semDTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@DeleteMapping("/deleteAllSem")
	public ResponseEntity<?> deleteAllSem(@RequestBody SemDTO semDTO) {

		this.semService.deleteAllSem(semDTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}
}
