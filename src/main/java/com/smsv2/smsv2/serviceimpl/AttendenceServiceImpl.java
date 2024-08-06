package com.smsv2.smsv2.serviceimpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.AttendenceDTO;
import com.smsv2.smsv2.dao.AttendenceDao;
import com.smsv2.smsv2.dao.SubDao;
import com.smsv2.smsv2.dao.TeacherDao;
import com.smsv2.smsv2.entity.Assignment;
import com.smsv2.smsv2.entity.Attendence;
import com.smsv2.smsv2.entity.Sub;
import com.smsv2.smsv2.entity.Teacher;
import com.smsv2.smsv2.exception.ResourceBadRequestException;
import com.smsv2.smsv2.exception.ResourceInternalServerErrorException;
import com.smsv2.smsv2.exception.ResourceNotFoundException;
import com.smsv2.smsv2.service.AttendenceService;

@Transactional
@Service
public class AttendenceServiceImpl implements AttendenceService {
	@Autowired
	private AttendenceDao attendencedao;
	@Autowired
	private SubDao subdao;
	@Autowired
	private TeacherDao teacherdao;

	@Override
	public List<Attendence> getAllAttendence() {
		return attendencedao.findAll();
	}

	@Override
	public Optional<Attendence> getAllAttendenceById(int id) {
		Optional<Attendence> attendence = attendencedao.findById(id);
		if (attendence.isEmpty()) {
			throw new ResourceNotFoundException("attendence", "id", id);
		}
		return attendence;
	}

	@Override
	public List<Attendence> getAllAttendenceBySubId(int subId) {

		return attendencedao.findBySubid_Id(subId);

	}

	@Override
	public void addAssignment(AttendenceDTO attendenceDTO) {
		Sub sub = subdao.findById(attendenceDTO.getSubId())
				.orElseThrow(() -> new ResourceNotFoundException("sub", "id", attendenceDTO.getSubId()));
		Teacher teacher = teacherdao.findById(attendenceDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", attendenceDTO.getTeacherId()));
		Optional<Attendence> nameattendence=attendencedao.findByName(attendenceDTO.getName());
		if(nameattendence.isPresent()) {
			throw new ResourceInternalServerErrorException("attendence","name",attendenceDTO.getName());
		}
		if (attendenceDTO.getTeacherId() == sub.getTeacher().getId()) {
			Attendence attendence = new Attendence();
			attendence.setLink(attendenceDTO.getLink());
			attendence.setName(attendenceDTO.getName());
			attendence.setSubid(sub);
			attendence.setDeptname(sub.getDept().getDeptname());
			attendence.setSemname(sub.getSem().getSemname());
			attendence.setTeachername(sub.getTeacher().getName());
			attendence.setSubname(sub.getSubname());
			attendencedao.save(attendence);
		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}

	}

	@Override
	public void updateAttendence(int id, AttendenceDTO attendenceDTO) {

		Attendence existAttendence = attendencedao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("attendence", "id", id));
		Teacher teacher = teacherdao.findById(attendenceDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", attendenceDTO.getTeacherId()));
		Optional<Attendence> nameattendence=attendencedao.findByName(attendenceDTO.getName());
		if(nameattendence.isPresent()) {
			throw new ResourceInternalServerErrorException("attendence","name",attendenceDTO.getName());
		}
		if (existAttendence.getSubid().getTeacher().getId() == attendenceDTO.getTeacherId()) {
			existAttendence.setName(attendenceDTO.getName());
			existAttendence.setLink(attendenceDTO.getLink());
			attendencedao.save(existAttendence);
		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}

	}

	@Override
	public void delteAttendenceById(int id, AttendenceDTO attendenceDTO) {
		Attendence existAttendence = attendencedao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("attendence", "id", id));
		Teacher teacher = teacherdao.findById(attendenceDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", attendenceDTO.getTeacherId()));
		if (existAttendence.getSubid().getTeacher().getId() == attendenceDTO.getTeacherId()) {
			attendencedao.delete(existAttendence);
		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}

	}

	@Override
	public void deleteAllAttendence(AttendenceDTO attendenceDTO) {
		Teacher teacher = teacherdao.findById(attendenceDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", attendenceDTO.getTeacherId()));
		if (teacher.getRole().equals("pic")) {
			attendencedao.deleteAll();
		} else {
			throw new ResourceBadRequestException("your role should be pic ");
		}

	}

	@Override
	public void deleteAllAttendenceSub(int subId,AttendenceDTO attendenceDTO) {
		Teacher teacher = teacherdao.findById(attendenceDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", attendenceDTO.getTeacherId()));
		Sub sub = subdao.findById(subId)
				.orElseThrow(() -> new ResourceNotFoundException("sub", "id", attendenceDTO.getSubId()));
		if (teacher.getSub().contains(sub)) {
			List<Attendence> existAttendence = attendencedao.findBySubid_Id(subId);
			attendencedao.deleteAll(existAttendence);
		} else {
			throw new ResourceBadRequestException("you are not allowed");

		}

	}

	@Override
	public String uploadFile(int id, MultipartFile file) {
		try {
			Attendence attendence = attendencedao.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("attendence", "id", id));
			attendence.setPdf(file.getBytes()); // Assuming 'pdf' field can hold PDF bytes
			attendencedao.save(attendence);
			return "File uploaded successfully";
		} catch (IOException e) {
			throw new RuntimeException("Failed to upload file", e);
		}
	}

	@Override
	public byte[] downloadFile(int id) {
		Attendence attendence = attendencedao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("attendence", "id", id));
		return attendence.getPdf();
	}

}
