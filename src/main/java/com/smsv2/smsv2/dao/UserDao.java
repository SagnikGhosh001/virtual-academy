package com.smsv2.smsv2.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsv2.smsv2.entity.User;

public interface UserDao extends JpaRepository<User, Integer>{
	Optional<User> findByEmail(String email);
	Optional<User> findByPhone(String phone);
}
