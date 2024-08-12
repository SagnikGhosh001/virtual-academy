package com.smsv2.smsv2.serviceimpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.AdminDTO;
import com.smsv2.smsv2.dao.AdminDao;
import com.smsv2.smsv2.dao.UserDao;
import com.smsv2.smsv2.entity.Admin;
import com.smsv2.smsv2.entity.Student;
import com.smsv2.smsv2.entity.User;
import com.smsv2.smsv2.exception.ResourceBadRequestException;
import com.smsv2.smsv2.exception.ResourceInternalServerErrorException;
import com.smsv2.smsv2.exception.ResourceNotFoundException;
import com.smsv2.smsv2.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao admindao;
	@Autowired
	private UserDao userdao;

	@Override
	public ResponseEntity<List<Admin>> getAllAdmin() {
		List<Admin> admin= admindao.findAll();
		return new ResponseEntity<>(admin,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Optional<Admin>> getAllAdminById(int id) {
		Optional<Admin> admin = admindao.findById(id);
		if (admin.isPresent())
			return new ResponseEntity<>(admin,HttpStatus.OK);
		else
			throw new ResourceNotFoundException("admin", "id", id);
	}

	@Override
	public ResponseEntity<?> addAdmin(AdminDTO adminDTO) {
		Admin checkadmin = admindao.findById(adminDTO.getUserid())
				.orElseThrow(() -> new ResourceNotFoundException("admin", "id", adminDTO.getUserid()));
		Optional<User> emailAdmin=userdao.findByEmail(adminDTO.getEmail());
		Optional<User> phoneAdmin=userdao.findByPhone(adminDTO.getPhone());
		if(emailAdmin.isPresent()) {
			throw new ResourceInternalServerErrorException("admin","email",adminDTO.getEmail());
		}
		
		if(phoneAdmin.isPresent()) {
			throw new ResourceInternalServerErrorException("admin","phone",adminDTO.getPhone());
		}

		if (checkadmin.getRole().equals("admin")) {
			Admin admin = new Admin();
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			admin.setEmail(adminDTO.getEmail());
			admin.setEmailVerified(true);
			admin.setGender(adminDTO.getGender());
			admin.setName(adminDTO.getName());
			admin.setRole("admin");
			admin.setPassword(bcrypt.encode(adminDTO.getPassword()));
			admin.setPhone(adminDTO.getPhone());
			admin.setPhoneverified(true);
			admindao.save(admin);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			throw new ResourceBadRequestException("you are not admin");
		}

	}

	@Override
	public ResponseEntity<?> updateAdmin(int id, AdminDTO adminDTO) {
		Admin admin = admindao.findById(id).orElseThrow(() -> new ResourceNotFoundException("admin", "id", id));
		Optional<User> emailAdmin=userdao.findByEmail(adminDTO.getEmail());
		Optional<User> phoneAdmin=userdao.findByPhone(adminDTO.getPhone());
		if(emailAdmin.isPresent()) {
			throw new ResourceInternalServerErrorException("admin","email",adminDTO.getEmail());
		}
		
		if(phoneAdmin.isPresent()) {
			throw new ResourceInternalServerErrorException("admin","phone",adminDTO.getPhone());
		}
		if (adminDTO.getUserid() == admin.getId()) {
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			admin.setEmail(adminDTO.getEmail());
			admin.setEmailVerified(true);
			admin.setGender(adminDTO.getGender());
			admin.setName(adminDTO.getName());
			admin.setPassword(bcrypt.encode(adminDTO.getPassword()));
			admin.setPhone(adminDTO.getPhone());
			admin.setPhoneverified(true);
			admindao.save(admin);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}

	}

	@Override
	public ResponseEntity<String> uploadFile(int id, MultipartFile file) {
		try {
			Admin admin = admindao.findById(id).orElseThrow(() -> new ResourceNotFoundException("admin", "id", id));
			admin.setPic(file.getBytes());
			admindao.save(admin);
			String msg= "File uploaded successfully";
			return new ResponseEntity<>(msg,HttpStatus.OK);
		} catch (IOException e) {
			throw new RuntimeException("Failed to upload file", e);
		}
	}

	@Override
	public byte[] downloadFile(int id) {
		Admin admin = admindao.findById(id).orElseThrow(() -> new ResourceNotFoundException("admin", "id", id));
		return admin.getPic();
		
	}

	@Override
	public ResponseEntity<?> delteAdminById(int id, AdminDTO adminDTO) {
		Admin admin = admindao.findById(id).orElseThrow(() -> new ResourceNotFoundException("admin", "id", id));
		if (id == adminDTO.getUserid()) {
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			if (bcrypt.matches(adminDTO.getPassword(), admin.getPassword())) {
				admin.getFeedback().forEach(feedback -> feedback.setUser(null));
				admin.getFeedback().clear();
				admindao.delete(admin);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				throw new ResourceBadRequestException("use valid password");

			}

		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}

	}

}
