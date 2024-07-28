package com.smsv2.smsv2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.AssignmentDTO;
import com.smsv2.smsv2.entity.Assignment;
import com.smsv2.smsv2.entity.Sub;
import com.smsv2.smsv2.service.AssignmentService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {
	@Autowired
	private AssignmentService assignmentservice;

	@GetMapping("/allassignmnet")
	public ResponseEntity<?> getAllAssignment() {

		return new ResponseEntity<>(assignmentservice.getAllAssignment(), HttpStatus.OK);
	}

	@GetMapping("/assignmnetbyid/{id}")
	public ResponseEntity<?> getAllAssignment(@PathVariable int id) {
		return new ResponseEntity<>(assignmentservice.getAllAssignmentById(id), HttpStatus.OK);
	}

	@GetMapping("/assignmnetbysubid/{subid}")
	public ResponseEntity<?> getAllAssignmentBySubId(@PathVariable int subid) {
		return new ResponseEntity<>(assignmentservice.getAllAssignmentBySubId(subid), HttpStatus.OK);
	}

	@PostMapping("/uploadpdf/{id}/{role}")
	public ResponseEntity<String> uploadPdf(@PathVariable("role") String role, @PathVariable int id,
			@RequestParam("file") MultipartFile file) {
		if (role.equals("teacher") || role.equals("hod") || role.equals("pic")) {
			String message = assignmentservice.uploadFile(id, file);
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/downloadpdf/{id}")
	public ResponseEntity<byte[]> downloadPdf(@PathVariable int id) {
		byte[] fileData = assignmentservice.downloadFile(id);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF); // Set content type to PDF
		headers.setContentDispositionFormData("attachment", "attendence_document.pdf"); // Set filename for download

		return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
	}

	@PostMapping("/addassignmnet/{role}")
	public ResponseEntity<?> addAssignment(@RequestBody AssignmentDTO assignmentDTO,
			@PathVariable("role") String role) {
		if (role.equals("teacher") || role.equals("hod") || role.equals("pic")) {
			this.assignmentservice.addAssignment(assignmentDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/updateassignment/{id}/{role}")
	public ResponseEntity<?> updateAssignment(@PathVariable int id, @RequestBody AssignmentDTO assignmentDTO,
			@PathVariable("role") String role) {
		if (role.equals("teacher") || role.equals("hod") || role.equals("pic")) {
			this.assignmentservice.updateAssignment(id, assignmentDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/deleteassignmentbyId/{id}/{role}")
	public ResponseEntity<?> deleteAssignmentById(@PathVariable int id, @RequestBody AssignmentDTO assignmentDTO,
			@PathVariable("role") String role) {
		if (role.equals("teacher") || role.equals("hod") || role.equals("pic")) {
			this.assignmentservice.delteAssignmentById(id, assignmentDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/deleteAllassignment/{role}")
	public ResponseEntity<?> deleteAllAssignment(@PathVariable("role") String role) {
		if (role.equals("pic") ) {
			this.assignmentservice.deleteAllAssignment();
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/deleteAllassignmentBySub/{role}")
	public ResponseEntity<?> deleteAllAssignmentBySub(@PathVariable("role") String role, @RequestBody AssignmentDTO assignmentDTO) {
		if (role.equals("teacher") || role.equals("hod") || role.equals("pic")) {
			this.assignmentservice.deleteAllAssignmentBySub(assignmentDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
	}

}
