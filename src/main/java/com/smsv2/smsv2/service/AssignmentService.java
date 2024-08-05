package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.AssignmentDTO;
import com.smsv2.smsv2.entity.Assignment;
import com.smsv2.smsv2.entity.Sub;

public interface AssignmentService {
	// get all assignment
	List<Assignment> getAllAssignment();

	// get assignment by id
	Optional<Assignment> getAllAssignmentById(int id);

	// get assignment by sub id
	List<Assignment> getAllAssignmentBySubId(int subId);

	String uploadFile(int id, MultipartFile file);

	byte[] downloadFile(int id);

	// add new assignment
	void addAssignment(AssignmentDTO assignmentDTO);

	// update assignment
	void updateAssignment(int id, AssignmentDTO assignmentDTO);

	
	
	// delete a assignment
	void delteAssignmentById(int id,AssignmentDTO assignmentDTO);

	// delete all assignment of same subject
	void deleteAllAssignmentBySub(int subid,AssignmentDTO assignmentDTO);

	// delete all assignment
	void deleteAllAssignment(AssignmentDTO assignmentDTO);
}
