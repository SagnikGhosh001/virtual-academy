package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import com.smsv2.smsv2.DTO.SubDTO;
import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Sub;
import com.smsv2.smsv2.entity.Teacher;

public interface SubService {
	// get all Sub
	List<Sub> getAllSub();

	// get Sub by id
	Optional<Sub> getAllSubById(int id);

	// get Sub by teacher id
	List<Sub> getAllSubByTeacherId(int id);

	// get Sub by dept id
	List<Sub> getAllSubBysemdeptId(int semId,int deptId);

	// add new Sub
	void addSub(SubDTO subDTO);

	// update Sub
	void updateSub(int id, SubDTO sub);



	// delete a Sub
	void delteSubById(int id, SubDTO subDTO);

	// delete a Sub by Dept
	void delteSubByDept(int deptid, SubDTO subDTO);

	// delete all Sem
	void deleteAllSub( SubDTO subDTO);
}
