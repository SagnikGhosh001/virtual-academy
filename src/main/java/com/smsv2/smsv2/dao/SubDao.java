package com.smsv2.smsv2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Sub;
import com.smsv2.smsv2.entity.Teacher;

import java.util.List;
import java.util.Optional;

 

public interface SubDao extends JpaRepository<Sub, Integer> {
	
List<Sub>  findByDeptId(int id);
List<Sub>  findByTeacherId(int id);
@Query("SELECT s FROM Sub s WHERE s.sem.id = ?1 AND s.dept.id = ?2")
List<Sub>  findBySemDeptId(int semId,int deptId);
}
