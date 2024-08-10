package com.smsv2.smsv2.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smsv2.smsv2.DTO.SemDTO;
import com.smsv2.smsv2.DTO.TeacherDTO;
import com.smsv2.smsv2.dao.AdminDao;
import com.smsv2.smsv2.dao.BookDao;
import com.smsv2.smsv2.dao.DeptDao;
import com.smsv2.smsv2.dao.MarksDao;
import com.smsv2.smsv2.dao.SemDao;
import com.smsv2.smsv2.dao.StudentDao;
import com.smsv2.smsv2.dao.SubDao;
import com.smsv2.smsv2.dao.TeacherDao;
import com.smsv2.smsv2.entity.Admin;
import com.smsv2.smsv2.entity.Book;
import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Marks;
import com.smsv2.smsv2.entity.Sem;
import com.smsv2.smsv2.entity.Student;
import com.smsv2.smsv2.entity.Sub;
import com.smsv2.smsv2.entity.Teacher;
import com.smsv2.smsv2.exception.ResourceBadRequestException;
import com.smsv2.smsv2.exception.ResourceInternalServerErrorException;
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

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private TeacherDao teacherDao;

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
			throw new ResourceNotFoundException("sem", "id", id);
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
		Optional<Teacher> teacher = teacherDao.findById(semDTO.getUserid());
		Optional<Admin> admin = adminDao.findById(semDTO.getUserid());
		Sem nameSem=semdao.findBySemname(semDTO.getSemname());
		if(nameSem!=null) {
			throw new ResourceInternalServerErrorException("sem","name",nameSem.getSemname());
		}
		if ((teacher.isPresent() && teacher.get().getRole().equals("pic"))
				|| (admin.isPresent() && admin.get().getRole().equals("admin"))) {
			Sem sem = new Sem();
			sem.setSemname(semDTO.getSemname());
			semdao.save(sem);
		} else {
			throw new ResourceBadRequestException("your role should be pic or admin");
		}

	}

	@Override
	public void updateSem(int id, SemDTO semDTO) {
		Sem existSem = semdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("sem", "id", id));
		Optional<Teacher> teacher = teacherDao.findById(semDTO.getUserid());
		Optional<Admin> admin = adminDao.findById(semDTO.getUserid());
		Sem nameSem=semdao.findBySemname(semDTO.getSemname());
		if(nameSem!=null) {
			throw new ResourceInternalServerErrorException("sem","name",nameSem.getSemname());
		}
		if ((teacher.isPresent() && teacher.get().getRole().equals("pic"))
				|| (admin.isPresent() && admin.get().getRole().equals("admin"))) {
			existSem.setSemname(semDTO.getSemname());
			semdao.save(existSem);
		} else {
			throw new ResourceBadRequestException("your role should be pic or admin");
		}

	}

	@Override
	public void delteSemById(int id, SemDTO semDTO) {
		Sem existSem = semdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("sem", "id", id));
		Optional<Teacher> checkteacher = teacherDao.findById(semDTO.getUserid());
		Optional<Admin> admin = adminDao.findById(semDTO.getUserid());
		if ((checkteacher.isPresent() && checkteacher.get().getRole().equals("pic"))
				|| (admin.isPresent() && admin.get().getRole().equals("admin"))) {
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
		} else {
			throw new ResourceBadRequestException("your role should be pic or admin");
		}

	}

	@Override
	public void deleteAllSem(SemDTO semDTO) {
		// Fetch all semesters from the database
		List<Sem> semesters = semdao.findAll();

		Optional<Teacher> checkteacher = teacherDao.findById(semDTO.getUserid());
		Optional<Admin> admin = adminDao.findById(semDTO.getUserid());
		if ((checkteacher.isPresent() && checkteacher.get().getRole().equals("pic"))
				|| (admin.isPresent() && admin.get().getRole().equals("admin"))) {

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

		} else {
			throw new ResourceBadRequestException("your role should be pic or admin");
		}

	}

}
