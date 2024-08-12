package com.smsv2.smsv2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smsv2.smsv2.entity.Teacher;

import java.util.List;
import java.util.Optional;


public interface TeacherDao extends JpaRepository<Teacher, Integer> {
Optional<Teacher> findByEmail(String email);
Optional<Teacher> findByPhone(String phone);
List<Teacher> findBySemId(int id);
List<Teacher> findByDeptId(int id);
Teacher findByRole(String role);

@Query("SELECT t FROM Teacher t JOIN t.sem s WHERE s.id = ?1 AND t.dept.id = ?2")
List<Teacher> findBySemDeptId(int sem,int dept);
}
