package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.AssignmentDTO;
import com.smsv2.smsv2.entity.Assignment;
import com.smsv2.smsv2.entity.Sub;

public interface AssignmentService {
	// get all assignment
	ResponseEntity<List<Assignment>> getAllAssignment();

	// get assignment by id
	ResponseEntity<Optional<Assignment>> getAllAssignmentById(int id);

	// get assignment by sub id
	ResponseEntity<List<Assignment>> getAllAssignmentBySubId(int subId);

	ResponseEntity<String> uploadFile(int id, MultipartFile file);

	byte[] downloadFile(int id);

	// add new assignment
	ResponseEntity<?> addAssignment(AssignmentDTO assignmentDTO);

	// update assignment
	ResponseEntity<?> updateAssignment(int id, AssignmentDTO assignmentDTO);

	
	
	// delete a assignment
	ResponseEntity<?> delteAssignmentById(int id,AssignmentDTO assignmentDTO);

	// delete all assignment of same subject
	ResponseEntity<?> deleteAllAssignmentBySub(int subid,AssignmentDTO assignmentDTO);

	// delete all assignment
	ResponseEntity<?> deleteAllAssignment(AssignmentDTO assignmentDTO);
}
