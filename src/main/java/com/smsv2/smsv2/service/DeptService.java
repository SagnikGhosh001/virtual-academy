package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import com.smsv2.smsv2.DTO.AssignmentDTO;
import com.smsv2.smsv2.DTO.DeptDTO;
import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Teacher;

public interface DeptService {
	// get all Dept
	List<Dept> getAllDept();

	// get dept by id
	Optional<Dept> getAllDeptById(int id);

	// get Dept by Teacher id
	List<Dept> getAllDeptByTeacherId(int teacherId);

	// get Dept by Teacher id
	List<Dept> getAllDeptBySemId(int semId);

	// add new Dept
	void addDept(DeptDTO dept);

	// update Dept
	void updateDept(int id, DeptDTO deptDTO);

	// delete a Dept Sem
	void delteDeptSemById(int id, DeptDTO deptDTO);

	// delete a Dept
	void delteDeptById(int id,DeptDTO deptDTO);

	// delete all Dept
	void deleteAllDept(DeptDTO deptDTO);
}
