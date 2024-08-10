package com.smsv2.smsv2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Sem;
import com.smsv2.smsv2.entity.Teacher;

import java.util.List;
import java.util.Optional;




public interface SemDao extends JpaRepository<Sem, Integer> {
List<Sem> findByTeacherId(int teacher);
List<Sem> findByDeptId(int dept);
Sem findBySemname(String semname);

}
