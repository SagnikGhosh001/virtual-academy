package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.smsv2.smsv2.DTO.AssignmentDTO;
import com.smsv2.smsv2.DTO.DeptDTO;
import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Teacher;

public interface DeptService {
	// get all Dept
	ResponseEntity<List<Dept>> getAllDept();

	// get dept by id
	ResponseEntity<Optional<Dept>> getAllDeptById(int id);

	// get Dept by Teacher id
	ResponseEntity<List<Dept>> getAllDeptByTeacherId(int teacherId);

	// get Dept by Teacher id
	ResponseEntity<List<Dept>> getAllDeptBySemId(int semId);

	// add new Dept
	ResponseEntity<?> addDept(DeptDTO dept);

	// update Dept
	ResponseEntity<?> updateDept(int id, DeptDTO deptDTO);

	// delete a Dept Sem
	ResponseEntity<?> delteDeptSemById(int id, DeptDTO deptDTO);

	// delete a Dept
	ResponseEntity<?> delteDeptById(int id,DeptDTO deptDTO);

	// delete all Dept
	ResponseEntity<?> deleteAllDept(DeptDTO deptDTO);
}
