package com.smsv2.smsv2.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smsv2.smsv2.entity.Inbox;
import com.smsv2.smsv2.entity.Student;
import com.smsv2.smsv2.entity.Teacher;

import java.util.List;


public interface InboxDao extends JpaRepository<Inbox, Integer> {
	List<Inbox> findByStudentId(int studentId);
	List<Inbox> findByTeacherId(int teacherId);
	
	List<Inbox> findByStudentReg(String reg);
}
