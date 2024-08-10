package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import com.smsv2.smsv2.DTO.SemDTO;
import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Sem;
import com.smsv2.smsv2.entity.Teacher;

public interface SemService {
	// get all Sem
	List<Sem> getAllSem();

	// get Sem by id
	Optional<Sem> getAllSemById(int id);

	// get Sem by teacher id
	List<Sem> getAllSemByTeacherId(int teacherId);

	// get Sem by dept id
	List<Sem> getAllSemBydeptId(int deptId);

	// add new Sem
	void addSem(SemDTO name);

	// update Sem name
	void updateSem(int id, SemDTO semDTO);

		
	// delete a Sem
	void delteSemById(int id,SemDTO semDTO);

	// delete all Sem
	void deleteAllSem(SemDTO semDTO);
}
