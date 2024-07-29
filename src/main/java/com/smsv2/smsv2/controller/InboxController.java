package com.smsv2.smsv2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smsv2.smsv2.DTO.InboxDTO;
import com.smsv2.smsv2.service.InboxService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/inbox")
public class InboxController {

	@Autowired
	private InboxService inboxService;

	@GetMapping("/allInbox")
	public ResponseEntity<?> allInbox() {
		return new ResponseEntity<>(inboxService.getAllInbox(), HttpStatus.OK);
	}

	@GetMapping("/inboxById/{id}")
	public ResponseEntity<?> inboxByid(@PathVariable("id") int id) {
		return new ResponseEntity<>(inboxService.getAllInboxById(id), HttpStatus.OK);
	}

	@GetMapping("/inboxByStudentReg/{reg}")
	public ResponseEntity<?> inboxByStudentReg(@PathVariable("reg") String reg) {
		return new ResponseEntity<>(inboxService.getAllinboxByStudentReg(reg), HttpStatus.OK);
	}

	@GetMapping("/inboxByStudentId/{id}")
	public ResponseEntity<?> inboxByStudentReg(@PathVariable("id") int id) {
		return new ResponseEntity<>(inboxService.getAllinboxByStudentId(id), HttpStatus.OK);
	}

	@GetMapping("/inboxByTeacherId/{id}")
	public ResponseEntity<?> inboxByTeacherid(@PathVariable("id") int id) {
		return new ResponseEntity<>(inboxService.getAllinboxByTeacherId(id), HttpStatus.OK);
	}

	@PostMapping("/addinbox")
	public ResponseEntity<?> addinbox(@RequestBody InboxDTO inboxDTO) {
		this.inboxService.addInbox(inboxDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@PutMapping("/updateinbox/{id}")
	public ResponseEntity<?> updateinbox(@RequestBody InboxDTO inboxDTO, @PathVariable("id") int id) {
		this.inboxService.updateInbox(id, inboxDTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@DeleteMapping("/deleteinbox/{id}")
	public ResponseEntity<?> deleteinbox(@RequestBody InboxDTO inboxDTO, @PathVariable("id") int id) {
		this.inboxService.delteInboxById(id, inboxDTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@DeleteMapping("/deleteAllinbox")
	public ResponseEntity<?> deleteAllinbox(@RequestBody InboxDTO inboxDTO) {
		this.inboxService.deleteAllInbox(inboxDTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}

}
