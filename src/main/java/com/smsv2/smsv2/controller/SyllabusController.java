package com.smsv2.smsv2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.SyllabusDTO;
import com.smsv2.smsv2.DTO.TopicDTO;
import com.smsv2.smsv2.service.SyllabusService;

@RestController
@RequestMapping("/api/syllabus")
public class SyllabusController {

	@Autowired
	private SyllabusService syllabusservice;

	@GetMapping("/allsyllabus")
	public ResponseEntity<?> allsyllabus() {
		return new ResponseEntity<>(syllabusservice.getAllSyllabus(), HttpStatus.OK);
	}

	@GetMapping("/syllabusbyId/{id}")
	public ResponseEntity<?> syllabusbyId(@PathVariable int id) {
		return new ResponseEntity<>(syllabusservice.getSyllabusById(id), HttpStatus.OK);
	}

	@GetMapping("/syllabusbyDeptId/{id}")
	public ResponseEntity<?> syllabusbyDeptId(@PathVariable int id) {
		return new ResponseEntity<>(syllabusservice.getSyllabusByDeptId(id), HttpStatus.OK);
	}

	@PostMapping("/addsyllabus/{role}")
	public ResponseEntity<?> addsyllabus(@PathVariable("role") String role, @RequestBody SyllabusDTO syllabusDTO) {
		if (role.equals("teacher") || role.equals("hod") || role.equals("pic")) {
			this.syllabusservice.addSyllabus(syllabusDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/updatesyllabus/{id}/{role}")
	public ResponseEntity<?> updatesyllabus(@PathVariable int id, @PathVariable("role") String role,
			@RequestBody SyllabusDTO syllabusDTO) {
		if (role.equals("teacher") || role.equals("hod") || role.equals("pic")) {
			this.syllabusservice.updateSyllabus(id, syllabusDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/uploadpdf/{id}/{role}")
	public ResponseEntity<String> uploadPdf(@PathVariable("role") String role, @PathVariable int id,
			@RequestParam("file") MultipartFile file) {
		if (role.equals("teacher") || role.equals("hod") || role.equals("pic")) {
			String message = syllabusservice.uploadFile(id, file);
			return ResponseEntity.status(HttpStatus.OK).body(message);

		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/downloadpdf/{id}")
	public ResponseEntity<byte[]> downloadPdf(@PathVariable int id) {
		byte[] fileData = syllabusservice.downloadFile(id);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF); // Set content type to PDF
		headers.setContentDispositionFormData("attachment", "attendence_document.pdf"); // Set filename for download

		return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
	}

	@DeleteMapping("/deleteSyllabusbyId/{id}/{role}")
	public ResponseEntity<?> deleteSyllabusbyId(@PathVariable int id, @PathVariable("role") String role,
			@RequestBody SyllabusDTO syllabusDTO) {
		if (role.equals("teacher") || role.equals("hod") || role.equals("pic") ) {
			this.syllabusservice.deleteSyllabusById(id, syllabusDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}

	}

	@DeleteMapping("/deleteSyllabusbyDeptId/{deptId}/{role}")
	public ResponseEntity<?> deleteSyllabusbyDeptId(@PathVariable int deptId, @PathVariable("role") String role,
			@RequestBody SyllabusDTO syllabusDTO) {
		if (role.equals("teacher") || role.equals("hod") || role.equals("pic") ) {
			this.syllabusservice.deleteSyllabusByDeptId(deptId, syllabusDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}

	}

	@DeleteMapping("/deleteAllSyllabus/{role}")
	public ResponseEntity<?> deleteAllSyllabus(@PathVariable("role") String role) {
		if (role.equals("pic")) {
			this.syllabusservice.deleteAllSyllabus();
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}

	}
}
