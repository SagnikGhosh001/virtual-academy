package com.smsv2.smsv2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.DeptDTO;
import com.smsv2.smsv2.DTO.SemDTO;
import com.smsv2.smsv2.DTO.TeacherDTO;
import com.smsv2.smsv2.service.StudentService;
import com.smsv2.smsv2.service.TeacherService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

	@Autowired
	private TeacherService teacherservice;

	@GetMapping("/allteacher")
	public ResponseEntity<?> getAllTeacher() {
		return new ResponseEntity<>(teacherservice.getAllTeacher(), HttpStatus.OK);
	}

	@GetMapping("/teacherById/{id}")
	public ResponseEntity<?> teacherById(@PathVariable("id") int id) {
		return new ResponseEntity<>(teacherservice.getAllTeacherById(id), HttpStatus.OK);
	}

	@GetMapping("/teacherBysemId/{id}")
	public ResponseEntity<?> teacherBySemId(@PathVariable("id") int id) {
		return new ResponseEntity<>(teacherservice.getAllTeacherBySemId(id), HttpStatus.OK);
	}

	@GetMapping("/teacherBydeptId/{id}")
	public ResponseEntity<?> teacherByDeptId(@PathVariable("id") int id) {
		return new ResponseEntity<>(teacherservice.getAllTeacherByDeptId(id), HttpStatus.OK);
	}

	@GetMapping("/teacherBysemdeptId/{semid}/{deptid}")
	public ResponseEntity<?> teacherBysemDeptId(@PathVariable int semid, @PathVariable int deptid) {
		return new ResponseEntity<>(teacherservice.getAllTeacherBySemDeptId(semid, deptid), HttpStatus.OK);
	}

	@GetMapping("/teacherByEmail/{email}")
	public ResponseEntity<?> teacherByEmail(@PathVariable String email) {
		return new ResponseEntity<>(teacherservice.getAllTeacherByEmail(email), HttpStatus.OK);
	}

	@GetMapping("/teacherByPhone/{phone}")
	public ResponseEntity<?> teacherByphone(@PathVariable String phone) {
		return new ResponseEntity<>(teacherservice.getAllTeacherByPhone(phone), HttpStatus.OK);
	}

	@PostMapping("/uploadteacherpic/{teacherId}")
	public ResponseEntity<String> uploadFile(@PathVariable int teacherId, @RequestParam("file") MultipartFile file) {
		String message = teacherservice.uploadFile(teacherId, file);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/download/{teacherId}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable int teacherId) {
		byte[] fileData = teacherservice.downloadFile(teacherId);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		headers.setContentDispositionFormData("attachment", "student_image.jpg");

		return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
	}

	@PostMapping("/registerTeacher")
	public ResponseEntity<?> registerTeacher(@RequestBody TeacherDTO teacherDTO) {
		this.teacherservice.addTeacher(teacherDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@PutMapping("/updateteacherdetails/{id}")
	public ResponseEntity<?> updateteacherdetails(@PathVariable int id, @RequestBody TeacherDTO teacherDTO) {
		this.teacherservice.updateTeacher(id, teacherDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/updateteacherOthers/{id}")
	public ResponseEntity<?> updateteacherOthers(@PathVariable int id, @RequestBody TeacherDTO teacherDTO) {
		this.teacherservice.updateTeacherOthers(id, teacherDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/deleteteacherbyId/{id}")
	public ResponseEntity<?> deleteteacherbyId(@PathVariable int id,@RequestBody TeacherDTO teacherDTO) {
			this.teacherservice.delteTeacherById(id,teacherDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		
	}

	@DeleteMapping("/deleteParticularSemOfTeacherbyId/{id}")
	public ResponseEntity<?> deleteParticularSemOfTeacherbyId(@PathVariable int id,
			@RequestBody TeacherDTO teacherDTO) {
			this.teacherservice.delteTeacherSemById(id, teacherDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		
	}

	@DeleteMapping("/deleteParticularDeptOfTeacherbyId/{id}")
	public ResponseEntity<?> deleteParticularDeptOfTeacherbyId(@PathVariable int id,
			@RequestBody TeacherDTO teacherDTO) {
			this.teacherservice.delteTeacherDeptById(id, teacherDTO);
			return new ResponseEntity<>(HttpStatus.OK);

	}

	@DeleteMapping("/deleteAllteacher")
	public ResponseEntity<?> deleteAllteacher(@RequestBody TeacherDTO teacherDTO) {
			this.teacherservice.deleteAllTeacher(teacherDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		
	}
}
