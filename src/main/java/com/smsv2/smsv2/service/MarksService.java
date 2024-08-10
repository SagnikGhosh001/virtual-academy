package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import com.smsv2.smsv2.DTO.AssignmentDTO;
import com.smsv2.smsv2.DTO.MarksDTO;
import com.smsv2.smsv2.entity.Marks;
import com.smsv2.smsv2.entity.Student;

public interface MarksService {
	// get all Marks
	List<Marks> getAllMarks();

	// get Marks by id
	Optional<Marks> getAllMarksById(int id);

	// get Marks by reg
	List<Marks> getAllMarksByReg(String reg);
	
	List<Marks> getAllMarksByRegSem(MarksDTO marksDTO);

	// add new Marks
	void addMarks(MarksDTO marks);

	// update Marks
	void updateMarkbyId(int id, MarksDTO marks);

	

	// delete a Marks
	void delteMarksById(int id,MarksDTO marksDTO);

	// delete all inbox
	void deleteAllMarks(MarksDTO marksDTO);
}
