package com.smsv2.smsv2.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smsv2.smsv2.DTO.SubDTO;
import com.smsv2.smsv2.dao.AdminDao;
import com.smsv2.smsv2.dao.DeptDao;
import com.smsv2.smsv2.dao.SemDao;
import com.smsv2.smsv2.dao.SubDao;
import com.smsv2.smsv2.dao.TeacherDao;
import com.smsv2.smsv2.entity.Admin;
import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Sem;
import com.smsv2.smsv2.entity.Sub;
import com.smsv2.smsv2.entity.Teacher;
import com.smsv2.smsv2.exception.ResourceBadRequestException;
import com.smsv2.smsv2.exception.ResourceNotFoundException;
import com.smsv2.smsv2.service.SubService;

@Transactional
@Service
public class SubServiceImpl implements SubService {

	@Autowired
	private SubDao subdao;

	@Autowired
	private TeacherDao teacherDao;

	@Autowired
	private DeptDao deptDao;

	@Autowired
	private SemDao semDao;

	@Autowired
	private AdminDao admindao;

	@Override
	public ResponseEntity<List<Sub>> getAllSub() {
		List<Sub> sub= subdao.findAll();
		return new ResponseEntity<>(sub,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Optional<Sub>> getAllSubById(int id) {
		Optional<Sub> sub = subdao.findById(id);
		if (sub.isEmpty()) {
			throw new ResourceNotFoundException("sub", "id", id);
		}
		return new ResponseEntity<>(sub,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Sub>> getAllSubByTeacherId(int id) {
		List<Sub>sub= subdao.findByTeacherId(id);
		return new ResponseEntity<>(sub,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Sub>> getAllSubBysemdeptId(int semId, int deptId) {
		List<Sub>sub= subdao.findBySemDeptId(semId, deptId);
		return new ResponseEntity<>(sub,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> addSub(SubDTO subDTO) {

		Dept dept = deptDao.findById(subDTO.getDeptId())
				.orElseThrow(() -> new ResourceNotFoundException("dept", "id", subDTO.getDeptId()));
		Sem sem = semDao.findById(subDTO.getSemId())
				.orElseThrow(() -> new ResourceNotFoundException("sem", "id", subDTO.getSemId()));
		Teacher teacher = teacherDao.findById(subDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", subDTO.getTeacherId()));
		if (sem.getDept().contains(dept)) {
			if (teacher.getRole().equals("pic")) {
				Sub sub = new Sub();
				sub.setSubname(subDTO.getSubname());
				sub.setDept(dept);
				sub.setSem(sem);
				sub.setSemname(sem.getSemname());
				sub.setDeptname(dept.getDeptname());

				subdao.save(sub);
				return new ResponseEntity<>(sub,HttpStatus.CREATED);
			} else if (teacher.getRole().equals("hod") && teacher.getDept().getId() == dept.getId()
					&& teacher.getSem().contains(sem)) {
				Sub sub = new Sub();
				sub.setSubname(subDTO.getSubname());
				sub.setDept(dept);
				sub.setSem(sem);
				sub.setSemname(sem.getSemname());
				sub.setDeptname(dept.getDeptname());
				subdao.save(sub);
				return new ResponseEntity<>(sub,HttpStatus.CREATED);
			} else {
				throw new ResourceBadRequestException("you are not allowed");
			}
		} else {
			throw new ResourceBadRequestException("Sem with id " + sem.getId() + " does not have dept " + dept.getId());

		}

	}

	@Override
	public ResponseEntity<?> updateSub(int id, SubDTO subDTO) {
		Sub existSub = subdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("sub", "id", id));
		Teacher teacher = teacherDao.findById(subDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", subDTO.getTeacherId()));
		Teacher teacherChange = teacherDao.findById(subDTO.getTeacherChange())
				.orElseThrow(() -> new ResourceNotFoundException("teacher change", "id", subDTO.getTeacherChange()));
		if (teacher.getRole().equals("pic")) {
			existSub.setSubname(subDTO.getSubname());
			existSub.setTeacher(teacherChange);
			existSub.setTeachername(teacherChange.getName());
			subdao.save(existSub);
			return new ResponseEntity<>(existSub,HttpStatus.OK);
		} else if (teacher.getRole().equals("hod") && teacher.getDept().getId() == existSub.getDept().getId()
				&& teacher.getSem().contains(existSub.getSem())) {
			existSub.setTeacher(teacherChange);
			existSub.setSubname(subDTO.getSubname());
			existSub.setTeacher(teacherChange);
			existSub.setTeachername(teacherChange.getName());
			subdao.save(existSub);
			return new ResponseEntity<>(existSub,HttpStatus.OK);
		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}

	}

	@Override
	public ResponseEntity<?> delteSubById(int id, SubDTO subDTO) {
		Sub existSub = subdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("sub", "id", id));
		Teacher teacher = teacherDao.findById(subDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", subDTO.getTeacherId()));
		if (teacher.getRole().equals("pic")) {
			subdao.delete(existSub);
			return new ResponseEntity<>(HttpStatus.OK);
		} else if (teacher.getRole().equals("hod") && teacher.getDept().getId() == existSub.getDept().getId()
				&& teacher.getSem().contains(existSub.getSem())) {
			subdao.delete(existSub);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}

	}

	@Override
	public ResponseEntity<?> delteSubByDept(int deptId, SubDTO subDTO) {
		List<Sub> existSub = subdao.findByDeptId(deptId);
		Teacher teacher = teacherDao.findById(subDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", subDTO.getTeacherId()));
		if (teacher.getRole().equals("pic")) {
			subdao.deleteAll(existSub);
			return new ResponseEntity<>(HttpStatus.OK);
		} else if (teacher.getRole().equals("hod") && !existSub.isEmpty()
				&& teacher.getDept().getId() == existSub.get(0).getDept().getId()) {
			subdao.deleteAll(existSub);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}

	}

	@Override
	public ResponseEntity<?> deleteAllSub(SubDTO subDTO) {
		Teacher teacher = teacherDao.findById(subDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", subDTO.getTeacherId()));
		if (teacher.getRole().equals("pic")) {
			subdao.deleteAll();
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			throw new ResourceBadRequestException("you are not pic");
		}

	}

}
