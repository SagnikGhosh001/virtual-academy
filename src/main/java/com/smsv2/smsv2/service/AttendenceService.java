package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.AssignmentDTO;
import com.smsv2.smsv2.DTO.AttendenceDTO;
import com.smsv2.smsv2.entity.Attendence;
import com.smsv2.smsv2.entity.Sub;

public interface AttendenceService {
	// get all attendence
	ResponseEntity<List<Attendence>> getAllAttendence();

	// get attendence by id
	ResponseEntity<Optional<Attendence>> getAllAttendenceById(int id);

	// get attendence by sub id
	ResponseEntity<List<Attendence>> getAllAttendenceBySubId(int subId);

	// add new attendence
	ResponseEntity<?> addAssignment(AttendenceDTO attendence);

	// update attendence
	ResponseEntity<?> updateAttendence(int id, AttendenceDTO attendence);

	ResponseEntity<String> uploadFile(int id, MultipartFile file);

	byte[] downloadFile(int id);

	// delete a attendence
	ResponseEntity<?> delteAttendenceById(int id, AttendenceDTO attendenceDTO);

	// delete all attendence
	ResponseEntity<?> deleteAllAttendence(AttendenceDTO attendenceDTO);

	// delete all attendence by sub
	ResponseEntity<?> deleteAllAttendenceSub(int subId,AttendenceDTO attendenceDTO);
	
	// delete all attendence by dept
	// void deleteAllAttendenceDept(int deptId);
}
