package com.smsv2.smsv2.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Sem;
import com.smsv2.smsv2.entity.Sub;
import com.smsv2.smsv2.entity.Teacher;



public interface DeptDao extends JpaRepository<Dept, Integer> {
List<Dept> findByTeacherId(int teacher);
List<Dept> findBySemId(int sem);
Optional<Dept>  findBySem(List<Sem> sem);
Optional<Dept>  findBySub(List<Sub> sub);
Dept findByDeptname(String deptname);
}
