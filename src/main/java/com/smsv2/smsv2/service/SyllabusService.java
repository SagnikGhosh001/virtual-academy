package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.SyllabusDTO;
import com.smsv2.smsv2.entity.Syllabus;

public interface SyllabusService {
	List<Syllabus> getAllSyllabus();

	Optional<Syllabus> getSyllabusById(int id);

	List<Syllabus> getSyllabusByDeptId(int deptId);

	String uploadFile(int id, MultipartFile file);

	byte[] downloadFile(int id);

	void addSyllabus(SyllabusDTO syllabusDTO);

	void updateSyllabus(int id, SyllabusDTO syllabusDTO);


	void deleteSyllabusById(int id, SyllabusDTO syllabusDTO);

	void deleteSyllabusByDeptId(int deptid, SyllabusDTO syllabusDTO);

	void deleteAllSyllabus(SyllabusDTO syllabusDTO);
}
