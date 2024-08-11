package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.smsv2.smsv2.DTO.AssignmentDTO;
import com.smsv2.smsv2.DTO.MarksDTO;
import com.smsv2.smsv2.entity.Marks;
import com.smsv2.smsv2.entity.Student;

public interface MarksService {
	// get all Marks
	ResponseEntity<List<Marks>> getAllMarks();

	// get Marks by id
	ResponseEntity<Optional<Marks>> getAllMarksById(int id);

	// get Marks by reg
	ResponseEntity<List<Marks>> getAllMarksByReg(String reg);
	
	ResponseEntity<List<Marks>> getAllMarksByRegSem(MarksDTO marksDTO);

	// add new Marks
	ResponseEntity<?> addMarks(MarksDTO marks);

	// update Marks
	ResponseEntity<?> updateMarkbyId(int id, MarksDTO marks);

	

	// delete a Marks
	ResponseEntity<?> delteMarksById(int id,MarksDTO marksDTO);

	// delete all inbox
	ResponseEntity<?> deleteAllMarks(MarksDTO marksDTO);
}
