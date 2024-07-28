package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.AdminDTO;
import com.smsv2.smsv2.entity.Admin;

public interface AdminService {
	List<Admin> getAllAdmin();

	Optional<Admin> getAllAdminById(int id);

	void addAdmin(AdminDTO adminDTO);

	void updateAdmin(int id,AdminDTO adminDTO);

	String uploadFile(int studentId, MultipartFile file);

	byte[] downloadFile(int studentId);

	void delteAdminById(int id,AdminDTO adminDTO);
}
