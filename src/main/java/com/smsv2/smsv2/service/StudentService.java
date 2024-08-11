package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.StudentDTO;
import com.smsv2.smsv2.entity.Student;

import jakarta.mail.Multipart;

public interface StudentService {
	// get all Student
	ResponseEntity<List<Student>> getAllStudent();

	// get Student by id
	ResponseEntity<Optional<Student>> getAllStudentById(int id);

	// get Student by email
	ResponseEntity<Optional<Student>> getAllStudentByEmail(String email);

	// get Student by phone
	ResponseEntity<Optional<Student>> getAllStudentByPhone(String phone);

	// get Student by reg
	ResponseEntity<Optional<Student>> getAllStudentByReg(String reg);

	// get Student by dept
	ResponseEntity<List<Student>> getAllStudentByDept(int deptId);

	// get Student by Sem
	ResponseEntity<List<Student>> getAllStudentBySem(int semId);

	// get Student by sem and dept
	ResponseEntity<List<Student>> getAllStudentBySemandDept(int semId, int deptId);

	// get Student by email verify
	ResponseEntity<List<Student>> getAllStudentByEmailVerify(boolean emailVerify);

	// get Student by phone verify
	ResponseEntity<List<Student>> getAllStudentByPhoneVerify(boolean phoneVerify);

	// add new Student
	ResponseEntity<?> addStudent(StudentDTO student);

	// update Student
	ResponseEntity<?> updateStudent(int id, StudentDTO student);

	ResponseEntity<String> uploadFile(int studentId, MultipartFile file);

	byte[] downloadFile(int studentId);

	// update Student
	ResponseEntity<?> updateStudentOthers(int id, StudentDTO student);

	// delete a Student
	ResponseEntity<?> delteStudentById(int id,StudentDTO student);

	// delete all Student
	ResponseEntity<?> deleteAllStudent(StudentDTO student);
}
