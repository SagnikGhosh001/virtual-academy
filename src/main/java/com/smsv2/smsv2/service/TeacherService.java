package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.DeptDTO;
import com.smsv2.smsv2.DTO.SemDTO;
import com.smsv2.smsv2.DTO.TeacherDTO;
import com.smsv2.smsv2.entity.Teacher;

public interface TeacherService {
	// get all Teacher
	ResponseEntity<List<Teacher>>  getAllTeacher();

	// get Teacher by id
	ResponseEntity<Optional<Teacher>> getAllTeacherById(int id);

	// get Teacher by email
	ResponseEntity<Optional<Teacher>> getAllTeacherByEmail(String email);

	// get Teacher by phone
	ResponseEntity<Optional<Teacher>> getAllTeacherByPhone(String phone);

	// get Student by email verify
	ResponseEntity<Optional<Teacher>> getAllTeacherByEmailVerify(boolean emailVerify);

	// get Teacher by phone verify
	ResponseEntity<Optional<Teacher>> getAllTeacherByPhoneVerify(boolean phoneVerify);

	// get Teacher by id
	ResponseEntity<List<Teacher>> getAllTeacherBySemId(int semid);

	// get Teacher by id
	ResponseEntity<List<Teacher>> getAllTeacherByDeptId(int deptid);

	// get Teacher by id
	ResponseEntity<List<Teacher>> getAllTeacherBySemDeptId(int semid, int deptid);

	// add new Teacher
	ResponseEntity<?> addTeacher(TeacherDTO teacher);

	// update Teacher
	ResponseEntity<?> updateTeacher(int id, TeacherDTO teacher);

	// update Teacher
	ResponseEntity<?> updateTeacherOthers(int id, TeacherDTO teacher);

	ResponseEntity<String> uploadFile(int id, MultipartFile file);

	byte[] downloadFile(int id);

	// delete a Teacher
	ResponseEntity<?> delteTeacherById(int id, TeacherDTO teacherDTO);

	// delete a Teacher
	ResponseEntity<?> delteTeacherSemById(int id, TeacherDTO teacherDTO);

	// delete a Teacher
	ResponseEntity<?> delteTeacherDeptById(int id, TeacherDTO teacherDTO);

	// delete all Teacher
	ResponseEntity<?> deleteAllTeacher(TeacherDTO teacherDTO);
}
