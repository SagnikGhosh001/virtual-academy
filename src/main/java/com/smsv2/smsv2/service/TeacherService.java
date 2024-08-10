package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.DeptDTO;
import com.smsv2.smsv2.DTO.SemDTO;
import com.smsv2.smsv2.DTO.TeacherDTO;
import com.smsv2.smsv2.entity.Teacher;

public interface TeacherService {
	// get all Teacher
	List<Teacher> getAllTeacher();

	// get Teacher by id
	Optional<Teacher> getAllTeacherById(int id);

	// get Teacher by email
	Optional<Teacher> getAllTeacherByEmail(String email);

	// get Teacher by phone
	Optional<Teacher> getAllTeacherByPhone(String phone);

	// get Student by email verify
	Optional<Teacher> getAllTeacherByEmailVerify(boolean emailVerify);

	// get Teacher by phone verify
	Optional<Teacher> getAllTeacherByPhoneVerify(boolean phoneVerify);

	// get Teacher by id
	List<Teacher> getAllTeacherBySemId(int semid);

	// get Teacher by id
	List<Teacher> getAllTeacherByDeptId(int deptid);

	// get Teacher by id
	List<Teacher> getAllTeacherBySemDeptId(int semid, int deptid);

	// add new Teacher
	void addTeacher(TeacherDTO teacher);

	// update Teacher
	void updateTeacher(int id, TeacherDTO teacher);

	// update Teacher
	void updateTeacherOthers(int id, TeacherDTO teacher);

	String uploadFile(int id, MultipartFile file);

	byte[] downloadFile(int id);

	// delete a Teacher
	void delteTeacherById(int id, TeacherDTO teacherDTO);

	// delete a Teacher
	void delteTeacherSemById(int id, TeacherDTO teacherDTO);

	// delete a Teacher
	void delteTeacherDeptById(int id, TeacherDTO teacherDTO);

	// delete all Teacher
	void deleteAllTeacher(TeacherDTO teacherDTO);
}
