package com.smsv2.smsv2.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smsv2.smsv2.DTO.MarksDTO;
import com.smsv2.smsv2.DTO.TeacherDTO;
import com.smsv2.smsv2.dao.DeptDao;
import com.smsv2.smsv2.dao.MarksDao;
import com.smsv2.smsv2.dao.SemDao;
import com.smsv2.smsv2.dao.StudentDao;
import com.smsv2.smsv2.dao.SubDao;
import com.smsv2.smsv2.dao.TeacherDao;
import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Marks;
import com.smsv2.smsv2.entity.Sem;
import com.smsv2.smsv2.entity.Student;
import com.smsv2.smsv2.entity.Sub;
import com.smsv2.smsv2.entity.Teacher;
import com.smsv2.smsv2.exception.ResourceBadRequestException;
import com.smsv2.smsv2.exception.ResourceNotFoundException;
import com.smsv2.smsv2.service.MarksService;

@Transactional
@Service
public class MarksServiceImpl implements MarksService {

	@Autowired
	private MarksDao marksDao;

	@Autowired
	private DeptDao deptDao;

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private SemDao semDao;

	@Autowired
	private SubDao subDao;

	@Autowired
	private TeacherDao teacherDao;

	@Override
	public List<Marks> getAllMarks() {
		return marksDao.findAll();

	}

	@Override
	public Optional<Marks> getAllMarksById(int id) {
		Optional<Marks> marksOptional = marksDao.findById(id);
		if (marksOptional.isEmpty()) {
			throw new ResourceNotFoundException("marks", "id", id);
		}
		return marksOptional;
	}

	@Override
	public List<Marks> getAllMarksByReg(String reg) {
		return marksDao.findByReg_Reg(reg);
	}
	
	@Override
	public List<Marks> getAllMarksByRegSem(MarksDTO marksDTO) {
		return  marksDao.findByReg_RegAndSemname(marksDTO.getReg(),marksDTO.getSemname());
	
	}

	@Override
	public void addMarks(MarksDTO marksDTO) {
		Student student = studentDao.findByReg(marksDTO.getReg())
				.orElseThrow(() -> new ResourceNotFoundException("student", "id", marksDTO.getReg()));
		Sub sub = subDao.findById(marksDTO.getSubId())
				.orElseThrow(() -> new ResourceNotFoundException("sub", "id", marksDTO.getSubId()));
		Sem sem = semDao.findById(sub.getSem().getId())
				.orElseThrow(() -> new ResourceNotFoundException("sem", "id", sub.getSem().getId()));
		Teacher teacher = teacherDao.findById(marksDTO.getTeacher())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", marksDTO.getTeacher()));

		if (teacher.getDept().equals(student.getDept()) && teacher.getSem().contains(sem)) {
			Marks marks = new Marks();
			marks.setReg(student);
			marks.setSub(sub);
			marks.setMark(marksDTO.getMark());
			marks.setDeptName(sub.getDept().getDeptname());
			marks.setSemname(sem.getSemname());
			marks.setRegNo(student.getReg());
			marks.setSubname(sub.getSubname());
			marksDao.save(marks);
		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}

	}

	@Override
	public void updateMarkbyId(int id, MarksDTO marks) {
		Marks existMarks = marksDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("marks", "id", id));
		Teacher teacher = teacherDao.findById(marks.getTeacher())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", marks.getTeacher()));
		Sub sub = subDao.findById(marks.getSubId())
				.orElseThrow(() -> new ResourceNotFoundException("sub", "id", marks.getSubId()));
		Sem sem = semDao.findById(existMarks.getSub().getSem().getId())
				.orElseThrow(() -> new ResourceNotFoundException("sem", "id", existMarks.getSub().getSem().getId()));
		if (teacher.getDept().equals(existMarks.getReg().getDept()) && teacher.getSem().contains(sem)) {
			existMarks.setMark(marks.getMark());
			existMarks.setSub(sub);
			existMarks.setSubname(sub.getSubname());
			existMarks.setDeptName(sub.getDept().getDeptname());
			existMarks.setSemname(sub.getSem().getSemname());
			marksDao.save(existMarks);

		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}

	}

	@Override
	public void delteMarksById(int id, MarksDTO marksDTO) {
		Marks existMarks = marksDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("marks", "id", id));
		Teacher teacher = teacherDao.findById(marksDTO.getTeacher())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", marksDTO.getTeacher()));
		if (teacher.getDept().equals(existMarks.getReg().getDept())
				&& teacher.getSem().contains(existMarks.getSub().getSem())) {
			marksDao.delete(existMarks);
		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}

	}

	@Override
	public void deleteAllMarks(MarksDTO marksDTO) {
		Teacher teacher = teacherDao.findById(marksDTO.getTeacher())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", marksDTO.getTeacher()));

		if (teacher.getRole().equals("pic")) {
			marksDao.deleteAll();
		} else {
			throw new ResourceBadRequestException("your role should be pic");
		}

	}

}