package com.smsv2.smsv2.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsv2.smsv2.entity.Marks;

public interface MarksDao extends JpaRepository<Marks, Integer> {

	List<Marks> findByReg_Reg(String reg);
	List<Marks> findByReg_RegAndSemname(String reg,String sem);

}
