package com.smsv2.smsv2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.StudentDTO;
import com.smsv2.smsv2.entity.Student;
import com.smsv2.smsv2.service.StudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/student")
public class StudentController {
	@Autowired
	private StudentService studentservice;

	@GetMapping("/allStudent")
	public ResponseEntity<?> allStudent() {
		return new ResponseEntity<>(studentservice.getAllStudent(), HttpStatus.OK);
	}

	@GetMapping("/studentbyId/{id}")
	public ResponseEntity<?> studentbyId(@PathVariable int id) {
		return new ResponseEntity<>(studentservice.getAllStudentById(id), HttpStatus.OK);
	}

	@GetMapping("/studentbyDeptId/{id}")
	public ResponseEntity<?> studentbyDeptId(@PathVariable int id) {
		return new ResponseEntity<>(studentservice.getAllStudentByDept(id), HttpStatus.OK);
	}

	@GetMapping("/studentbySemId/{id}")
	public ResponseEntity<?> studentbySemId(@PathVariable int id) {
		return new ResponseEntity<>(studentservice.getAllStudentBySem(id), HttpStatus.OK);
	}

	@GetMapping("/studentbyEmail/{email}")
	public ResponseEntity<?> studentbyEmail(@PathVariable String email) {
		return new ResponseEntity<>(studentservice.getAllStudentByEmail(email), HttpStatus.OK);
	}

	@GetMapping("/studentbyPhone/{phone}")
	public ResponseEntity<?> studentbyPhone(@PathVariable String phone) {
		return new ResponseEntity<>(studentservice.getAllStudentByPhone(phone), HttpStatus.OK);
	}

	@GetMapping("/studentbyReg/{reg}")
	public ResponseEntity<?> studentbyReg(@PathVariable String reg) {
		return new ResponseEntity<>(studentservice.getAllStudentByReg(reg), HttpStatus.OK);
	}

	@GetMapping("/studentbySemDept/{sem}/{dept}")
	public ResponseEntity<?> studentbySemDept(@PathVariable("sem") int sem, @PathVariable("dept") int dept) {
		return new ResponseEntity<>(studentservice.getAllStudentBySemandDept(sem, dept), HttpStatus.OK);
	}

	@PostMapping("/registerstudent")
	public ResponseEntity<?> registerStudent(@RequestBody StudentDTO studentDTO) {
		this.studentservice.addStudent(studentDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@PostMapping("/uploadstudentpic/{studentId}")
	public ResponseEntity<String> uploadFile(@PathVariable int studentId, @RequestParam("file") MultipartFile file) {
		String message = studentservice.uploadFile(studentId, file);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/download/{studentId}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable int studentId) {
		byte[] fileData = studentservice.downloadFile(studentId);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		headers.setContentDispositionFormData("attachment", "student_image.jpg");

		return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
	}

	@PutMapping("/updateStudentDetails/{id}")
	public ResponseEntity<?> updateStudentDetails(@PathVariable int id, @RequestBody StudentDTO studentDTO) {
		this.studentservice.updateStudent(id, studentDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/updateStudentOthers/{id}")
	public ResponseEntity<?> updateStudentOthers(@PathVariable int id, @PathVariable("role") String role,
			@RequestBody StudentDTO studentDTO) {
		if (role.equals("teacher") || role.equals("hod") || role.equals("pic")) {
			this.studentservice.updateStudentOthers(id, studentDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);

		}

	}

	@DeleteMapping("/deleteStudentbyId/{id}/{role}")
	public ResponseEntity<?> deleteStudentbyId(@PathVariable int id, @PathVariable("role") String role) {
		if (role.equals("pic") ) {
			this.studentservice.delteStudentById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);

		}
	}

	@DeleteMapping("/deleteAllStudent/{role}")
	public ResponseEntity<?> deleteAllStudent(@PathVariable("role") String role) {

		if (role.equals("pic") ) {
			this.studentservice.deleteAllStudent();
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);

		}
	}
}
