package com.smsv2.smsv2.serviceimpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.DeptDTO;
import com.smsv2.smsv2.DTO.TeacherDTO;
import com.smsv2.smsv2.OtpService.EmailService;
import com.smsv2.smsv2.OtpService.PhoneService;
import com.smsv2.smsv2.dao.AdminDao;
import com.smsv2.smsv2.dao.DeptDao;
import com.smsv2.smsv2.dao.SemDao;
import com.smsv2.smsv2.dao.TeacherDao;
import com.smsv2.smsv2.entity.Admin;
import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Sem;
import com.smsv2.smsv2.entity.Student;
import com.smsv2.smsv2.entity.Teacher;
import com.smsv2.smsv2.exception.ResourceBadRequestException;
import com.smsv2.smsv2.exception.ResourceInternalServerErrorException;
import com.smsv2.smsv2.exception.ResourceNotFoundException;
import com.smsv2.smsv2.service.TeacherService;

@Transactional
@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherDao teacherdao;

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private SemDao semdao;

	@Autowired
	private DeptDao deptdao;
	@Autowired
	private EmailService teacheremailservice;

	@Autowired
	private PhoneService teacherphoneservice;

	@Override
	public ResponseEntity<List<Teacher>> getAllTeacher() {
		List<Teacher>teacher= teacherdao.findAll();
		 return new ResponseEntity<>(teacher,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Optional<Teacher>> getAllTeacherById(int id) {
		Optional<Teacher> teacherOptional = teacherdao.findById(id);
		if (teacherOptional.isEmpty()) {
			throw new ResourceNotFoundException("teacher", "id", id);
		}
		 return new ResponseEntity<>(teacherOptional,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Teacher>> getAllTeacherBySemId(int id) {
		List<Teacher>teacher= teacherdao.findBySemId(id);
		 return new ResponseEntity<>(teacher,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Teacher>> getAllTeacherByDeptId(int id) {
		List<Teacher>teacher= teacherdao.findByDeptId(id);
		 return new ResponseEntity<>(teacher,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Teacher>> getAllTeacherBySemDeptId(int semid, int deptid) {
		List<Teacher>teacher= teacherdao.findBySemDeptId(semid, deptid);
		 return new ResponseEntity<>(teacher,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Optional<Teacher>> getAllTeacherByEmail(String email) {
		Optional<Teacher> teacher = teacherdao.findByPhone(email);
		if (teacher.isEmpty()) {
			throw new ResourceNotFoundException("teacher", "email", email);
		}
		 return new ResponseEntity<>(teacher,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Optional<Teacher>> getAllTeacherByPhone(String phone) {
		Optional<Teacher> teacher = teacherdao.findByPhone(phone);
		if (teacher.isEmpty()) {
			throw new ResourceNotFoundException("teacher", "phone", phone);
		}
		 return new ResponseEntity<>(teacher,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Optional<Teacher>> getAllTeacherByEmailVerify(boolean emailVerify) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Optional<Teacher>> getAllTeacherByPhoneVerify(boolean phoneVerify) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> addTeacher(TeacherDTO teacherDTO) {
		Sem sem = semdao.findById(teacherDTO.getSemId())
				.orElseThrow(() -> new ResourceNotFoundException("sem", "id", teacherDTO.getSemId()));
		Dept dept = deptdao.findById(teacherDTO.getDeptId())
				.orElseThrow(() -> new ResourceNotFoundException("dept", "id", teacherDTO.getDeptId()));
		Optional<Teacher> checkteacher = teacherdao.findById(teacherDTO.getUserId());
		Optional<Admin> admin = adminDao.findById(teacherDTO.getUserId());
		Optional<Teacher> emailTeacher=teacherdao.findByEmail(teacherDTO.getEmail());
		if(emailTeacher.isPresent()) {
			throw new ResourceInternalServerErrorException("teacher","email",teacherDTO.getEmail());
		}
		if ((checkteacher.isPresent() && checkteacher.get().getRole().equals("pic"))
				|| (admin.isPresent() && admin.get().getRole().equals("admin"))) {
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			Teacher teacher = new Teacher();
			teacher.setDept(dept);
			teacher.setDeptname(dept.getDeptname());
			teacher.getSem().add(sem);
			teacher.setEmail(teacherDTO.getEmail());
			teacher.setName(teacherDTO.getName());
			teacher.setGender(teacherDTO.getGender());
			teacher.setEmailVerified(true);
			teacher.setPassword(bcrypt.encode(teacherDTO.getPassword()));
			String loginUrl = "https://example.com/verify-email";
			String verificationMsg = "Welcome to our platform!\n\n" + "Your account has been successfully created.\n\n"
					+ "Please use the following credentials for logging in:\n" + "- Email: " + teacherDTO.getEmail()
					+ "\n" + "- Password: " + teacherDTO.getPassword() + ".\n\n"
					+ "To complete your registration, please verify your email by clicking the link below:\n"
					+ loginUrl;
			teacheremailservice.sendVerficationEmail1(teacherDTO.getEmail(), verificationMsg);
			teacherdao.save(teacher);
			 return new ResponseEntity<>(teacher,HttpStatus.OK);
		} else {
			throw new ResourceBadRequestException("your role should be pic or admin");
		}

	}

	@Override
	public ResponseEntity<?> updateTeacher(int id, TeacherDTO teacher) {
		Teacher existTeacher = teacherdao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", id));
		if (id == teacher.getUserId()) {
			existTeacher.setGender(teacher.getGender());
			existTeacher.setName(teacher.getName());
			teacherdao.save(existTeacher);
			 return new ResponseEntity<>(existTeacher,HttpStatus.OK);
		} else {
			throw new ResourceBadRequestException("You are not allowed");
		}

	}

	@Override
	public ResponseEntity<?> updateTeacherOthers(int id, TeacherDTO teacherDTO) {
		Teacher existTeacher = teacherdao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", id));
		Dept dept = deptdao.findById(teacherDTO.getDeptId())
				.orElseThrow(() -> new ResourceNotFoundException("dept", "id", teacherDTO.getDeptId()));
		Sem sem = semdao.findById(teacherDTO.getSemId())
				.orElseThrow(() -> new ResourceNotFoundException("sem", "id", teacherDTO.getSemId()));
		Optional<Teacher> checkteacher = teacherdao.findById(teacherDTO.getUserId());
		Optional<Admin> admin = adminDao.findById(teacherDTO.getUserId());
		if ((checkteacher.isPresent() && checkteacher.get().getRole().equals("pic"))
				|| (admin.isPresent() && admin.get().getRole().equals("admin"))) {
			existTeacher.setDept(dept);
			existTeacher.setDeptname(dept.getDeptname());
			existTeacher.setRole(teacherDTO.getRole());
			if (!existTeacher.getSem().contains(sem)) {
				existTeacher.getSem().add(sem);
			}
			teacherdao.save(existTeacher);
			 return new ResponseEntity<>(existTeacher,HttpStatus.OK);
		} else {
			throw new ResourceBadRequestException("your role should be pic or admin");
		}

	}

	@Override
	public ResponseEntity<?> delteTeacherById(int id, TeacherDTO teacherDTO) {
		Teacher existTeacher = teacherdao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", id));
		Optional<Teacher> checkteacher = teacherdao.findById(teacherDTO.getUserId());
		Optional<Admin> admin = adminDao.findById(teacherDTO.getUserId());
		if ((checkteacher.isPresent() && checkteacher.get().getRole().equals("pic"))
				|| (admin.isPresent() && admin.get().getRole().equals("admin"))) {
			existTeacher.getSub().forEach(sub -> sub.setTeacher(null));
			existTeacher.getSub().clear();
			existTeacher.getAssignment().forEach(asssignment -> asssignment.setTeacherId(null));
			existTeacher.getAssignment().clear();
			existTeacher.getFeedback().forEach(feedback -> feedback.setUser(null));
			existTeacher.getFeedback().clear();
			teacherdao.delete(existTeacher);
			 return new ResponseEntity<>(existTeacher,HttpStatus.OK);
		} else {
			throw new ResourceBadRequestException("your role should be pic or admin");
		}

	}

	@Override
	public ResponseEntity<?> delteTeacherSemById(int id, TeacherDTO teacherDTO) {
		Teacher teacher = teacherdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("teacher", "id", id));

		Sem sem = semdao.findById(teacherDTO.getSemId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", teacherDTO.getSemId()));
		Optional<Teacher> checkteacher = teacherdao.findById(teacherDTO.getUserId());
		Optional<Admin> admin = adminDao.findById(teacherDTO.getUserId());
		if ((checkteacher.isPresent() && checkteacher.get().getRole().equals("pic"))
				|| (admin.isPresent() && admin.get().getRole().equals("admin"))) {
			if (teacher.getSem().contains(sem)) {
				teacher.getSem().remove(sem);
				sem.getTeacher().remove(teacher);
			} else {
				throw new ResourceBadRequestException(
						"Sem with id " + teacherDTO.getSemId() + " is not associated with Teacher with id " + id);
			}

			teacherdao.save(teacher);
			 return new ResponseEntity<>(HttpStatus.OK);
		} else {
			throw new ResourceBadRequestException("your role should be pic or admin");
		}

	}

	@Override
	public ResponseEntity<?> delteTeacherDeptById(int id, TeacherDTO teacherDTO) {
		Dept dept = deptdao.findById(teacherDTO.getDeptId())
				.orElseThrow(() -> new ResourceNotFoundException("dept", "id", teacherDTO.getDeptId()));

		Teacher teacher = teacherdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("teacher", "id", id));

		Optional<Teacher> checkteacher = teacherdao.findById(teacherDTO.getUserId());
		Optional<Admin> admin = adminDao.findById(teacherDTO.getUserId());
		if ((checkteacher.isPresent() && checkteacher.get().getRole().equals("pic"))
				|| (admin.isPresent() && admin.get().getRole().equals("admin"))) {
			if (teacher.getDept().equals(dept)) {
				teacher.setDept(null);
				dept.getTeacher().remove(teacher);
			} else {
				throw new ResourceBadRequestException(
						"dept with id " + teacherDTO.getDeptId() + " is not associated with Teacher with id " + id);
			}

			teacherdao.save(teacher);
			 return new ResponseEntity<>(HttpStatus.OK);
		} else {
			throw new ResourceBadRequestException("your role should be pic or admin");
		}

	}

	@Override
	public ResponseEntity<?> deleteAllTeacher(TeacherDTO teacherDTO) {
		// Fetch all teachers from the database
		List<Teacher> teachers = teacherdao.findAll();
		Optional<Teacher> checkteacher = teacherdao.findById(teacherDTO.getUserId());
		Optional<Admin> admin = adminDao.findById(teacherDTO.getUserId());
		if ((checkteacher.isPresent() && checkteacher.get().getRole().equals("pic"))
				|| (admin.isPresent() && admin.get().getRole().equals("admin"))) {
			// Iterate over each teacher and perform necessary dissociations
			for (Teacher teacher : teachers) {
				// Clear the association between Teacher and Sub
				teacher.getSub().forEach(sub -> sub.setTeacher(null));
				teacher.getSub().clear();

				// Clear the association between Teacher and Assignment
				teacher.getAssignment().forEach(assignment -> assignment.setTeacherId(null));
				teacher.getAssignment().clear();

				// Clear the association between Teacher and Feedback
				teacher.getFeedback().forEach(feedback -> feedback.setUser(null));
				teacher.getFeedback().clear();

			}

			// Delete all teachers from the database
			teacherdao.deleteAll();
			 return new ResponseEntity<>(HttpStatus.OK);
		} else {
			throw new ResourceBadRequestException("your role should be pic or admin");
		}

	}

	@Override
	public ResponseEntity<String> uploadFile(int id, MultipartFile file) {
		try {
			Teacher teacher = teacherdao.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", id));
			teacher.setPic(file.getBytes());
			teacherdao.save(teacher);
			String msg= "File uploaded successfully";
			 return new ResponseEntity<>(msg,HttpStatus.OK);
		} catch (IOException e) {
			throw new RuntimeException("Failed to upload file", e);
		}
	}

	@Override
	public byte[] downloadFile(int id) {
		Teacher teacher = teacherdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("teacher", "id", id));
		return teacher.getPic();
	}

}
