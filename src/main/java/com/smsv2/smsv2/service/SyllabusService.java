package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.SyllabusDTO;
import com.smsv2.smsv2.entity.Syllabus;
import com.twilio.http.Response;

public interface SyllabusService {
	ResponseEntity<List<Syllabus>> getAllSyllabus();

	ResponseEntity<Optional<Syllabus>> getSyllabusById(int id);

	ResponseEntity<List<Syllabus>> getSyllabusByDeptId(int deptId);

	ResponseEntity<String> uploadFile(int id, MultipartFile file);

	byte[] downloadFile(int id);

	ResponseEntity<?> addSyllabus(SyllabusDTO syllabusDTO);

	ResponseEntity<?> updateSyllabus(int id, SyllabusDTO syllabusDTO);


	ResponseEntity<?> deleteSyllabusById(int id, SyllabusDTO syllabusDTO);

	ResponseEntity<?> deleteSyllabusByDeptId(int deptid, SyllabusDTO syllabusDTO);

	ResponseEntity<?> deleteAllSyllabus(SyllabusDTO syllabusDTO);
}
