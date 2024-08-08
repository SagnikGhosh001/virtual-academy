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
		return new ResponseEntity<>(marksservice.getAllMarks(), HttpStatus.OK);
	}

	@GetMapping("/marksById/{id}")
	public ResponseEntity<?> marksById(@PathVariable("id") int id) {
		return new ResponseEntity<>(marksservice.getAllMarksById(id), HttpStatus.OK);
	}

	@GetMapping("/marksByReg/{reg}")
	public ResponseEntity<?> marksByReg(@PathVariable("reg") String reg) {
		return new ResponseEntity<>(marksservice.getAllMarksByReg(reg), HttpStatus.OK);
	}
	@PostMapping("/marksByRegSem")
	public ResponseEntity<?> marksByRegSem(@RequestBody MarksDTO marksDTO) {
		return new ResponseEntity<>(marksservice.getAllMarksByRegSem(marksDTO), HttpStatus.OK);
	}

	@PostMapping("/addmarks")
	public ResponseEntity<?> addmarks(@RequestBody MarksDTO marksDTO) {
		this.marksservice.addMarks(marksDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/updatemarks/{id}")
	public ResponseEntity<?> updatemarks(@RequestBody MarksDTO marksDTO, @PathVariable int id) {
		this.marksservice.updateMarkbyId(id, marksDTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@DeleteMapping("/deletemarks/{id}")
	public ResponseEntity<?> deletemarks(@PathVariable int id, @RequestBody MarksDTO marksDTO) {
		this.marksservice.delteMarksById(id, marksDTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@DeleteMapping("/deleteAllmarks")
	public ResponseEntity<?> deleteAllmarks(@RequestBody MarksDTO marksDTO) {
		this.marksservice.deleteAllMarks(marksDTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}

}
