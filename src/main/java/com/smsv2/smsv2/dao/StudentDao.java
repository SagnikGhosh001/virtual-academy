package com.smsv2.smsv2.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Sem;
import com.smsv2.smsv2.entity.Student;




public interface StudentDao extends JpaRepository<Student, Integer> {
	Optional<Student> findByEmail(String email);

	Optional<Student> findByPhone(String phone);

	Optional<Student> findByReg(String reg);
	
	List<Student> findByDeptId(int deptId);
	
	List<Student> findBySemId(int semId);
	

	
	@Query("SELECT s FROM Student s WHERE s.sem.id = ?1 AND s.dept.id = ?2")
	List<Student> getByDeptandSem(int sem,int dept);
}
