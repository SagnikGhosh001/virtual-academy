package com.smsv2.smsv2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.AttendenceDTO;
import com.smsv2.smsv2.service.AttendenceService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/attendence")
public class AttendenceController {
	@Autowired
	private AttendenceService attendenceService;

	@GetMapping("/getallattendence")
	public ResponseEntity<?> getAllAssignment() {
		return new ResponseEntity<>(attendenceService.getAllAttendence(), HttpStatus.OK);
	}

	@GetMapping("/getattendencebyid/{id}")
	public ResponseEntity<?> getAssignmentById(@PathVariable int id) {
		return new ResponseEntity<>(attendenceService.getAllAttendenceById(id), HttpStatus.OK);
	}

	@GetMapping("/getattendencebysubid/{id}")
	public ResponseEntity<?> getAssignmentBySubId(@PathVariable int subId) {
		return new ResponseEntity<>(attendenceService.getAllAttendenceBySubId(subId), HttpStatus.OK);
	}

	@PostMapping("/addattendence/{role}")
	public ResponseEntity<?> addAttendence(@RequestBody AttendenceDTO attendenceDTO,
			@PathVariable("role") String role) {
		if (role.equals("teacher") || role.equals("hod") || role.equals("pic")) {
			this.attendenceService.addAssignment(attendenceDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);

		} else {
			return new ResponseEntity<String>("you are not allowed for this", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/updateattendence/{id}/{role}")
	public ResponseEntity<?> updateAttendence(@PathVariable int id, @RequestBody AttendenceDTO attendenceDTO,
			@PathVariable("role") String role) {
		if (role.equals("teacher") || role.equals("hod") || role.equals("pic")) {
			this.attendenceService.updateAttendence(id, attendenceDTO);
			return new ResponseEntity<>(HttpStatus.OK);

		} else {
			return new ResponseEntity<String>("you are not allowed for this", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/uploadpdf/{id}/{role}")
	public ResponseEntity<String> uploadPdf(@PathVariable("role") String role, @PathVariable int id,
			@RequestParam("file") MultipartFile file) {
		if (role.equals("teacher") || role.equals("hod") || role.equals("pic")) {

			String message = attendenceService.uploadFile(id, file);
			return ResponseEntity.status(HttpStatus.OK).body(message);

		} else {
			return new ResponseEntity<String>("you are not allowed for this", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/downloadpdf/{id}")
	public ResponseEntity<byte[]> downloadPdf(@PathVariable int id) {
		byte[] fileData = attendenceService.downloadFile(id);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF); // Set content type to PDF
		headers.setContentDispositionFormData("attachment", "attendence_document.pdf"); // Set filename for download

		return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
	}

	@DeleteMapping("/deleteattendencebyId/{id}/{role}")
	public ResponseEntity<?> deleteAttendencebyId(@PathVariable int id, @PathVariable("role") String role,
			@RequestBody AttendenceDTO attendenceDTO) {
		if (role.equals("teacher") || role.equals("hod") || role.equals("pic") ) {
			this.attendenceService.delteAttendenceById(id, attendenceDTO);
			return new ResponseEntity<>(HttpStatus.OK);

		} else {
			return new ResponseEntity<String>("you are not allowed for this", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/deleteattendencebySubId/{role}")
	public ResponseEntity<?> deleteAttendencebySubId(@PathVariable("role") String role, @RequestBody AttendenceDTO attendenceDTO) {
		if (role.equals("teacher") || role.equals("hod") || role.equals("pic")) {
			this.attendenceService.deleteAllAttendenceSub(attendenceDTO);
			return new ResponseEntity<>(HttpStatus.OK);

		} else {
			return new ResponseEntity<String>("you are not allowed for this", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/deleteAllattendence/{role}")
	public ResponseEntity<?> deleteAllAttendence(@PathVariable("role") String role) {
		if (role.equals("pic")) {
			this.attendenceService.deleteAllAttendence();
			return new ResponseEntity<>(HttpStatus.OK);

		} else {
			return new ResponseEntity<String>("you are not allowed for this", HttpStatus.BAD_REQUEST);
		}
	}
}
