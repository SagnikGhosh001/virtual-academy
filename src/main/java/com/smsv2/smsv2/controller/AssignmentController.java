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
	public ResponseEntity<?> getAllAssignment(@PathVariable("id") int id) {
		return new ResponseEntity<>(assignmentservice.getAllAssignmentById(id), HttpStatus.OK);
	}

	@GetMapping("/assignmnetbysubid/{subid}")
	public ResponseEntity<?> getAllAssignmentBySubId(@PathVariable("subid") int subid) {
		return new ResponseEntity<>(assignmentservice.getAllAssignmentBySubId(subid), HttpStatus.OK);
	}

	@PostMapping("/uploadpdf/{id}/{role}")
	public ResponseEntity<String> uploadPdf(@PathVariable("role") String role, @PathVariable("id") int id,
			@RequestParam("file") MultipartFile file) {
		if (role.equals("teacher") || role.equals("hod") || role.equals("pic")) {
			String message = assignmentservice.uploadFile(id, file);
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/downloadpdf/{id}")
	public ResponseEntity<byte[]> downloadPdf(@PathVariable("id") int id) {
		byte[] fileData = assignmentservice.downloadFile(id);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF); // Set content type to PDF
		headers.setContentDispositionFormData("attachment", "attendence_document.pdf"); // Set filename for download

		return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
	}

	@PostMapping("/addassignmnet")
	public ResponseEntity<?> addAssignment(@RequestBody AssignmentDTO assignmentDTO) {
	
			this.assignmentservice.addAssignment(assignmentDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/updateassignment/{id}")
	public ResponseEntity<?> updateAssignment(@PathVariable("id") int id, @RequestBody AssignmentDTO assignmentDTO) {
			this.assignmentservice.updateAssignment(id, assignmentDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		
	}

	@DeleteMapping("/deleteassignmentbyId/{id}")
	public ResponseEntity<?> deleteAssignmentById(@PathVariable("id") int id, @RequestBody AssignmentDTO assignmentDTO) {
			this.assignmentservice.delteAssignmentById(id, assignmentDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		
	}

	@DeleteMapping("/deleteAllassignment")
	public ResponseEntity<?> deleteAllAssignment(@RequestBody AssignmentDTO assignmentDTO) {
		
			this.assignmentservice.deleteAllAssignment(assignmentDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		
	}

	@DeleteMapping("/deleteAllassignmentBySub/{subid}")
	public ResponseEntity<?> deleteAllAssignmentBySub(@PathVariable int subid,@RequestBody AssignmentDTO assignmentDTO) {

			this.assignmentservice.deleteAllAssignmentBySub(subid,assignmentDTO);
			return new ResponseEntity<>(HttpStatus.OK);
	}

}
