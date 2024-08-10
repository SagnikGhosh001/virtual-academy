package com.smsv2.smsv2.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsv2.smsv2.entity.Assignment;
import com.smsv2.smsv2.entity.Sub;


public interface AssignmentDao extends JpaRepository<Assignment, Integer> {
	List<Assignment> findBySubId(int subId);
}
