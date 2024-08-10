package com.smsv2.smsv2.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsv2.smsv2.entity.Admin;

public interface AdminDao extends JpaRepository<Admin, Integer>{
	Optional<Admin> findByEmail(String email);
}
