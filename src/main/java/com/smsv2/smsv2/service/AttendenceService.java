package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.AssignmentDTO;
import com.smsv2.smsv2.DTO.AttendenceDTO;
import com.smsv2.smsv2.entity.Attendence;
import com.smsv2.smsv2.entity.Sub;

public interface AttendenceService {
	// get all attendence
	List<Attendence> getAllAttendence();

	// get attendence by id
	Optional<Attendence> getAllAttendenceById(int id);

	// get attendence by sub id
	List<Attendence> getAllAttendenceBySubId(int subId);

	// add new attendence
	void addAssignment(AttendenceDTO attendence);

	// update attendence
	void updateAttendence(int id, AttendenceDTO attendence);

	String uploadFile(int id, MultipartFile file);

	byte[] downloadFile(int id);

	// delete a attendence
	void delteAttendenceById(int id, AttendenceDTO attendenceDTO);

	// delete all attendence
	void deleteAllAttendence(AttendenceDTO attendenceDTO);

	// delete all attendence by sub
	void deleteAllAttendenceSub(int subId,AttendenceDTO attendenceDTO);
	
	// delete all attendence by dept
	// void deleteAllAttendenceDept(int deptId);
}
