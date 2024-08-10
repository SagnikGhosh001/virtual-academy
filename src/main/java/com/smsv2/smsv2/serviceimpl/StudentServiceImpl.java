package com.smsv2.smsv2.serviceimpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.StudentDTO;
import com.smsv2.smsv2.OtpService.EmailService;
import com.smsv2.smsv2.dao.DeptDao;
import com.smsv2.smsv2.dao.SemDao;
import com.smsv2.smsv2.dao.StudentDao;
import com.smsv2.smsv2.dao.TeacherDao;
import com.smsv2.smsv2.dao.UserDao;
import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Sem;
import com.smsv2.smsv2.entity.Student;
import com.smsv2.smsv2.entity.Teacher;
import com.smsv2.smsv2.entity.User;
import com.smsv2.smsv2.exception.ResourceBadRequestException;
import com.smsv2.smsv2.exception.ResourceInternalServerErrorException;
import com.smsv2.smsv2.exception.ResourceNotFoundException;
import com.smsv2.smsv2.service.StudentService;

@Transactional
@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao studentdao;

	@Autowired
	private DeptDao deptdao;

	@Autowired
	private SemDao semdao;

	@Autowired
	private TeacherDao teacherdao;
	@Autowired
	private UserDao userdao;

	@Autowired
	private EmailService studentemailservice;

	@Override
	public List<Student> getAllStudent() {
		return studentdao.findAll();
	}

	@Override
	public Optional<Student> getAllStudentById(int id) {
		Optional<Student> studentOptional = studentdao.findById(id);
		if (studentOptional.isEmpty()) {
			throw new ResourceNotFoundException("student", "id", id);
		}
		return studentOptional;
	}

	@Override
	public Optional<Student> getAllStudentByEmail(String email) {
		studentdao.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("student", "email", email));
		return studentdao.findByEmail(email);
	}

	@Override
	public Optional<Student> getAllStudentByPhone(String phone) {
		studentdao.findByPhone(phone).orElseThrow(() -> new ResourceNotFoundException("student", "phone", phone));
		return studentdao.findByEmail(phone);
	}

	@Override
	public Optional<Student> getAllStudentByReg(String reg) {
		studentdao.findByReg(reg).orElseThrow(() -> new ResourceNotFoundException("student", "reg", reg));
		return studentdao.findByReg(reg);
	}

	@Override
	public List<Student> getAllStudentByDept(int deptId) {
		return studentdao.findByDeptId(deptId);
	}

	@Override
	public List<Student> getAllStudentBySem(int semId) {
		return studentdao.findBySemId(semId);
	}

	@Override
	public List<Student> getAllStudentBySemandDept(int semId, int deptId) {
		return studentdao.getByDeptandSem(semId, deptId);

	}

	@Override
	public List<Student> getAllStudentByEmailVerify(boolean emailVerify) {
		return null;

	}

	@Override
	public List<Student> getAllStudentByPhoneVerify(boolean phoneVerify) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addStudent(StudentDTO studentDTO) {
		Sem sem = semdao.findById(studentDTO.getSemId())
				.orElseThrow(() -> new ResourceNotFoundException("sem", "id", studentDTO.getSemId()));
		Dept dept = deptdao.findById(studentDTO.getDeptId())
				.orElseThrow(() -> new ResourceNotFoundException("dept", "id", studentDTO.getDeptId()));
	
	    Optional<User> existingStudent = userdao.findByEmail(studentDTO.getEmail());
	    if (existingStudent.isPresent()) {
	        throw new ResourceInternalServerErrorException("student","email",studentDTO.getEmail());
	    }

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		Student student = new Student();
		student.setDept(dept);
		student.setSem(sem);
		student.setEmail(studentDTO.getEmail());
		student.setName(studentDTO.getName());
		student.setGender(studentDTO.getGender());
		student.setPassword(bcrypt.encode(studentDTO.getPassword()));
		student.setEmailVerified(false);
		student.setDeptname(dept.getDeptname());
		student.setSemname(sem.getSemname());
		String otp = studentemailservice.genereteOtp();
		student.setExpiryDateEmailOtp(LocalDateTime.now().plusMinutes(10));
		student.setEmailotp(otp);
		studentemailservice.sendVerficationEmail(studentDTO.getEmail(), otp);
		String verificationUrl = "https://example.com/verify-email?otp=" + otp;
		String verificationMsg = "Thank you for registering!\n\n"
				+ "Please verify your email to complete the registration process and start using our website.\n"
				+ "Click the link below to verify:\n" + verificationUrl;
		studentemailservice.sendVerficationEmail1(studentDTO.getEmail(), verificationMsg);
		studentdao.save(student);
	}

	@Override
	public void updateStudent(int id, StudentDTO studentDTO) {
		Student existStudent = studentdao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("student", "id", id));
		if (existStudent.getId() == studentDTO.getUserId()) {
			existStudent.setGender(studentDTO.getGender());
			existStudent.setName(studentDTO.getName());
			studentdao.save(existStudent);
		} else {
			throw new ResourceBadRequestException("You are not allowed");
		}

	}

	@Override
	public void updateStudentOthers(int id, StudentDTO studentDTO) {
		Student existStudent = studentdao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("student", "id", id));
		Teacher teacher = teacherdao.findById(studentDTO.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", studentDTO.getUserId()));
		Sem sem = semdao.findById(studentDTO.getSemId())
				.orElseThrow(() -> new ResourceNotFoundException("sem", "id", studentDTO.getSemId()));
		Dept dept = deptdao.findById(studentDTO.getDeptId())
				.orElseThrow(() -> new ResourceNotFoundException("dept", "id", studentDTO.getDeptId()));
		if ((teacher.getRole().equals("hod") && teacher.getDept().equals(existStudent.getDept())
				&& teacher.getSem().contains(existStudent.getSem())) || teacher.getRole().equals("pic")) {
			existStudent.setRole(studentDTO.getRole());
			existStudent.setReg(studentDTO.getReg());
			existStudent.setSem(sem);
			existStudent.setDept(dept);
			existStudent.setDeptname(dept.getDeptname());
			existStudent.setSemname(sem.getSemname());
			studentdao.save(existStudent);
		}else {
			throw new ResourceBadRequestException("your role is "+teacher.getRole()+" you are not allowed only HOD and PIC is allowed");
		}

	}

	@Override
	public void delteStudentById(int id, StudentDTO studentDTO) {
		Student existStudent = studentdao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("student", "id", id));
		Teacher teacher = teacherdao.findById(studentDTO.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", studentDTO.getUserId()));

		if ((teacher.getRole().equals("hod") && teacher.getDept().equals(existStudent.getDept())
				&& teacher.getSem().contains(existStudent.getSem())) || teacher.getRole().equals("pic")) {
			existStudent.getFeedback().forEach(feedback -> feedback.setUser(null));
			existStudent.getFeedback().clear();
			studentdao.delete(existStudent);
		}else {
			throw new ResourceBadRequestException("your role is "+teacher.getRole()+" you are not allowed only HOD and PIC is allowed");
		}

	}

	@Override
	public void deleteAllStudent(StudentDTO studentDTO) {

		Teacher teacher = teacherdao.findById(studentDTO.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", studentDTO.getUserId()));

		if (teacher.getRole().equals("pic")) {
			// Fetch all students from the database
			List<Student> students = studentdao.findAll();

			// Iterate over each student and perform necessary dissociations
			for (Student student : students) {
				// Clear the association between Student and Feedback
				student.getFeedback().forEach(feedback -> feedback.setUser(null));
				student.getFeedback().clear();

			}

			// Delete all students from the database
			studentdao.deleteAll();
		}  else {
			throw new ResourceBadRequestException("your are not pic");
		}

	}

	@Override
	public String uploadFile(int id, MultipartFile file) {
		try {
			Student student = studentdao.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("student", "id", id));
			student.setPic(file.getBytes());
			studentdao.save(student);
			return "File uploaded successfully";
		} catch (IOException e) {
			throw new RuntimeException("Failed to upload file", e);
		}
	}

	@Override
	public byte[] downloadFile(int studentId) {
		Student student = studentdao.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("student", "id", studentId));
		return student.getPic();
	}

}
