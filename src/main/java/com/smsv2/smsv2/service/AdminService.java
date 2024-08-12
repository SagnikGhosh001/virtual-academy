package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.AdminDTO;
import com.smsv2.smsv2.entity.Admin;

public interface AdminService {
	ResponseEntity<List<Admin>> getAllAdmin();

	ResponseEntity<Optional<Admin>> getAllAdminById(int id);

	ResponseEntity<?> addAdmin(AdminDTO adminDTO);

	ResponseEntity<?> updateAdmin(int id,AdminDTO adminDTO);

	ResponseEntity<String> uploadFile(int studentId, MultipartFile file);

	byte[] downloadFile(int studentId);

	ResponseEntity<?> delteAdminById(int id,AdminDTO adminDTO);
}
