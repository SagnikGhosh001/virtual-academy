package com.smsv2.smsv2.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smsv2.smsv2.DTO.InboxDTO;
import com.smsv2.smsv2.dao.InboxDao;
import com.smsv2.smsv2.dao.StudentDao;
import com.smsv2.smsv2.dao.TeacherDao;
import com.smsv2.smsv2.entity.Inbox;
import com.smsv2.smsv2.entity.Student;
import com.smsv2.smsv2.entity.Teacher;
import com.smsv2.smsv2.exception.ResourceBadRequestException;
import com.smsv2.smsv2.exception.ResourceNotFoundException;
import com.smsv2.smsv2.service.InboxService;

@Transactional
@Service
public class InboxServiceImpl implements InboxService {

	@Autowired
	private InboxDao inboxDao;

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private TeacherDao teacherDao;

	@Override
	public List<Inbox> getAllInbox() {
		return inboxDao.findAll();
	}

	@Override
	public Optional<Inbox> getAllInboxById(int id) {
		Optional<Inbox> inboxOptional = inboxDao.findById(id);
		if (inboxOptional.isEmpty()) {
			throw new ResourceNotFoundException("inbox","id",id);
		}
		return inboxOptional;
	}

	@Override
	public List<Inbox> getAllinboxByStudentReg(String reg) {
		return inboxDao.findByStudentReg(reg);

	}
	
	@Override
	public List<Inbox> getAllinboxByStudentId(int id) {
		return inboxDao.findByStudentId(id);

	}

	@Override
	public List<Inbox> getAllinboxByTeacherId(int teacherId) {
		return inboxDao.findByTeacherId(teacherId);
	}

	@Override
	public void addInbox(InboxDTO inboxDTO) {
		Teacher teacher = teacherDao.findById(inboxDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher","id",inboxDTO.getTeacherId()));
		Student student = studentDao.findByReg(inboxDTO.getReg())
				.orElseThrow(() -> new ResourceNotFoundException("student","reg",inboxDTO.getReg()));
		Inbox inbox = new Inbox();
		inbox.setStudent(student);
		inbox.setTeacher(teacher);
		inbox.setMsg(inboxDTO.getMsg());
		inbox.setStudentName(student.getName());
		inbox.setTeacherName(teacher.getName());
		inbox.setTeacherDept(teacher.getDeptname());
		inboxDao.save(inbox);
	}

	@Override
	public void updateInbox(int id, InboxDTO inboxDTO) {
		Inbox existInbox = inboxDao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("feedback","id",id));
		if (existInbox.getTeacher().getId() == inboxDTO.getUser()) {
			existInbox.setMsg(inboxDTO.getMsg());
			inboxDao.save(existInbox);
		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}

	}

	@Override
	public void delteInboxById(int id, InboxDTO inboxDTO) {
		Inbox existInbox = inboxDao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("feedback","id",id));
		if (existInbox.getTeacher().getId() == inboxDTO.getUser()) {
			inboxDao.delete(existInbox);
		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}

	}

	@Override
	public void deleteAllInbox() {
		inboxDao.deleteAll();

	}

}
