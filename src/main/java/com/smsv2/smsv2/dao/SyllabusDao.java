package com.smsv2.smsv2.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsv2.smsv2.entity.Syllabus;

public interface SyllabusDao extends JpaRepository<Syllabus, Integer>{
	
	List<Syllabus> findByDeptId(int id);
}
