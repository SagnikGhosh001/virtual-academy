package com.smsv2.smsv2.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsv2.smsv2.entity.Notes;

public interface NotesDao extends JpaRepository<Notes, Integer>{
	
	List<Notes> findBySubId(int id);
}
