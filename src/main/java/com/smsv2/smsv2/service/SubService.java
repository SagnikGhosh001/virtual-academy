package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.smsv2.smsv2.DTO.SubDTO;
import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Sub;
import com.smsv2.smsv2.entity.Teacher;

public interface SubService {
	// get all Sub
	ResponseEntity<List<Sub>> getAllSub();

	// get Sub by id
	ResponseEntity<Optional<Sub>> getAllSubById(int id);

	// get Sub by teacher id
	ResponseEntity<List<Sub>> getAllSubByTeacherId(int id);

	// get Sub by dept id
	ResponseEntity<List<Sub>> getAllSubBysemdeptId(int semId,int deptId);

	// add new Sub
	ResponseEntity<?> addSub(SubDTO subDTO);

	// update Sub
	ResponseEntity<?> updateSub(int id, SubDTO sub);



	// delete a Sub
	ResponseEntity<?> delteSubById(int id, SubDTO subDTO);

	// delete a Sub by Dept
	ResponseEntity<?> delteSubByDept(int deptid, SubDTO subDTO);

	// delete all Sem
	ResponseEntity<?> deleteAllSub( SubDTO subDTO);
}
