package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.smsv2.smsv2.DTO.SemDTO;
import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Sem;
import com.smsv2.smsv2.entity.Teacher;

public interface SemService {
	// get all Sem
	ResponseEntity<List<Sem>> getAllSem();

	// get Sem by id
	ResponseEntity<Optional<Sem>> getAllSemById(int id);

	// get Sem by teacher id
	ResponseEntity<List<Sem>> getAllSemByTeacherId(int teacherId);

	// get Sem by dept id
	ResponseEntity<List<Sem>> getAllSemBydeptId(int deptId);

	// add new Sem
	ResponseEntity<?> addSem(SemDTO name);

	// update Sem name
	ResponseEntity<?> updateSem(int id, SemDTO semDTO);

		
	// delete a Sem
	ResponseEntity<?> delteSemById(int id,SemDTO semDTO);

	// delete all Sem
	ResponseEntity<?> deleteAllSem(SemDTO semDTO);
}
