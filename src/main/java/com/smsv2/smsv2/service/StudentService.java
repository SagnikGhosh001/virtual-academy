package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.StudentDTO;
import com.smsv2.smsv2.entity.Student;

import jakarta.mail.Multipart;

public interface StudentService {
	// get all Student
	List<Student> getAllStudent();

	// get Student by id
	Optional<Student> getAllStudentById(int id);

	// get Student by email
	Optional<Student> getAllStudentByEmail(String email);

	// get Student by phone
	Optional<Student> getAllStudentByPhone(String phone);

	// get Student by reg
	Optional<Student> getAllStudentByReg(String reg);

	// get Student by dept
	List<Student> getAllStudentByDept(int deptId);

	// get Student by Sem
	List<Student> getAllStudentBySem(int semId);

	// get Student by sem and dept
	List<Student> getAllStudentBySemandDept(int semId, int deptId);

	// get Student by email verify
	List<Student> getAllStudentByEmailVerify(boolean emailVerify);

	// get Student by phone verify
	List<Student> getAllStudentByPhoneVerify(boolean phoneVerify);

	// add new Student
	void addStudent(StudentDTO student);

	// update Student
	void updateStudent(int id, StudentDTO student);

	String uploadFile(int studentId, MultipartFile file);

	byte[] downloadFile(int studentId);

	// update Student
	void updateStudentOthers(int id, StudentDTO student);

	// delete a Student
	void delteStudentById(int id,StudentDTO student);

	// delete all Student
	void deleteAllStudent(StudentDTO student);
}
