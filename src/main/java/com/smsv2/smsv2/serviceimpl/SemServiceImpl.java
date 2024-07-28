package com.smsv2.smsv2.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smsv2.smsv2.DTO.SemDTO;
import com.smsv2.smsv2.DTO.TeacherDTO;
import com.smsv2.smsv2.dao.BookDao;
import com.smsv2.smsv2.dao.DeptDao;
import com.smsv2.smsv2.dao.MarksDao;
import com.smsv2.smsv2.dao.SemDao;
import com.smsv2.smsv2.dao.StudentDao;
import com.smsv2.smsv2.dao.SubDao;
import com.smsv2.smsv2.dao.TeacherDao;
import com.smsv2.smsv2.entity.Book;
import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Marks;
import com.smsv2.smsv2.entity.Sem;
import com.smsv2.smsv2.entity.Student;
import com.smsv2.smsv2.entity.Sub;
import com.smsv2.smsv2.entity.Teacher;
import com.smsv2.smsv2.exception.ResourceNotFoundException;
import com.smsv2.smsv2.service.SemService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SemServiceImpl implements SemService {

	@Autowired
	private SemDao semdao;

	@Autowired
	private DeptDao deptdao;

	

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Sem> getAllSem() {
		return semdao.findAll();
	}

	@Override
	public Optional<Sem> getAllSemById(int id) {
	    Optional<Sem> sem = semdao.findById(id);
	    if (sem.isEmpty()) {
	        throw new ResourceNotFoundException("sem","id",id);
	    }
	    return sem;
	}


	@Override
	public List<Sem> getAllSemByTeacherId(int teacherId) {
		return semdao.findByTeacherId(teacherId);
	}

	@Override
	public List<Sem> getAllSemBydeptId(int deptId) {
		return semdao.findByDeptId(deptId);
	}

	@Override
	public void addSem(SemDTO semDTO) {
		Sem sem = new Sem();
		sem.setSemname(semDTO.getSemname());
		semdao.save(sem);

	}

	@Override
	public void updateSem(int id, SemDTO semDTO) {
		Sem existSem = semdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("sem","id",id));
		existSem.setSemname(semDTO.getSemname());
		semdao.save(existSem);
	}

	
	

	@Override
	public void delteSemById(int id) {
		Sem existSem = semdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("sem","id",id));
		for (Dept dept : existSem.getDept()) {
			dept.getSem().remove(existSem);
		}
		existSem.getDept().clear();
		for (Teacher teacher : existSem.getTeacher()) {
			teacher.getSem().remove(existSem);
		}
		existSem.getTeacher().clear();
		for (Student student : existSem.getStudent()) {
			student.setSem(null);
		}
		existSem.getStudent().clear();
		semdao.delete(existSem);

	}
	@Override
	public void deleteAllSem() {
	    // Fetch all semesters from the database
	    List<Sem> semesters = semdao.findAll();

	    // Iterate over each semester and perform necessary dissociations
	    for (Sem sem : semesters) {
	        // Clear the association between Sem and Dept
	        sem.getDept().forEach(dept -> dept.getSem().remove(sem));
	        sem.getDept().clear();

	        // Clear the association between Sem and Teacher
	        sem.getTeacher().forEach(teacher -> teacher.getSem().remove(sem));
	        sem.getTeacher().clear();

	        // Clear the association between Sem and Student
	        sem.getStudent().forEach(student -> student.setSem(null));
	        sem.getStudent().clear();
	    }

	    // Delete all semesters from the database
	    semdao.deleteAll();
	}




}
