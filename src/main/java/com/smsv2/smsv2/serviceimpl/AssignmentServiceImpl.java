package com.smsv2.smsv2.serviceimpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.AssignmentDTO;
import com.smsv2.smsv2.dao.AssignmentDao;
import com.smsv2.smsv2.dao.SubDao;
import com.smsv2.smsv2.dao.TeacherDao;
import com.smsv2.smsv2.entity.Assignment;
import com.smsv2.smsv2.entity.Sub;
import com.smsv2.smsv2.entity.Teacher;
import com.smsv2.smsv2.exception.ResourceBadRequestException;
import com.smsv2.smsv2.exception.ResourceNotFoundException;
import com.smsv2.smsv2.service.AssignmentService;

@Transactional
@Service
public class AssignmentServiceImpl implements AssignmentService {

	@Autowired
	private AssignmentDao assignmentdao;

	@Autowired
	private SubDao subdao;

	@Autowired
	private TeacherDao teacherdao;

	@Override
	public List<Assignment> getAllAssignment() {
		return assignmentdao.findAll();
	}

	@Override
	public Optional<Assignment> getAllAssignmentById(int id) {
		Optional<Assignment> assignment = assignmentdao.findById(id);
		if (assignment.isEmpty()) {
			throw new ResourceNotFoundException("assignment", "id", id);
		}
		return assignment;
	}

	@Override
	public void addAssignment(AssignmentDTO assignmentDTO) {

		Sub sub = subdao.findById(assignmentDTO.getSubId())
				.orElseThrow(() -> new ResourceNotFoundException("sub", "id", assignmentDTO.getSubId()));
		Teacher teacher = teacherdao.findById(assignmentDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", assignmentDTO.getTeacherId()));
		if (teacher.getSub().contains(sub)) {
			Assignment assignment = new Assignment();
			assignment.setName(assignmentDTO.getName());
			assignment.setTeacherId(teacher);
			assignment.setSub(sub);
			assignment.setDeptname(sub.getDept().getDeptname());
			assignment.setTeachername(teacher.getName());
			assignment.setSemname(sub.getSem().getSemname());
			assignment.setSubname(sub.getSubname());
			assignment.setDeadline(assignmentDTO.getDeadline());
			assignment.setDescription(assignmentDTO.getDescription());
			assignmentdao.save(assignment);
		} else {
			throw new ResourceBadRequestException("you are not allwed for this action");
		}

	}

	@Override
	public void updateAssignment(int id, AssignmentDTO assignmentDTO) {
		Assignment existassignment = assignmentdao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("assignment", "id", id));
		Teacher teacher = teacherdao.findById(assignmentDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", assignmentDTO.getTeacherId()));
		if (existassignment.getTeacherId().getId() == assignmentDTO.getTeacherId()) {
			existassignment.setName(assignmentDTO.getName());
			existassignment.setDescription(assignmentDTO.getDescription());
			existassignment.setDeadline(assignmentDTO.getDeadline());
			assignmentdao.save(existassignment);
		} else {
			throw new ResourceBadRequestException("you are not allwed for this action");

		}

	}

	@Override
	public void delteAssignmentById(int id, AssignmentDTO assignmentDTO) {
		Assignment existassignment = assignmentdao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("assignment", "id", id));
		Teacher teacher = teacherdao.findById(assignmentDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", assignmentDTO.getTeacherId()));
		if (existassignment.getTeacherId().getId() == assignmentDTO.getTeacherId()) {
			assignmentdao.delete(existassignment);
		} else {
			throw new ResourceBadRequestException("you are not allwed for this action");

		}

	}

	@Override
	public void deleteAllAssignment(AssignmentDTO assignmentDTO) {
		Teacher teacher = teacherdao.findById(assignmentDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", assignmentDTO.getTeacherId()));
		if (teacher.getRole().equals("pic")) {
			assignmentdao.deleteAll();
		} else {
			throw new ResourceBadRequestException("your role should be pic ");
		}

	}

	@Override
	public List<Assignment> getAllAssignmentBySubId(int subId) {
		return assignmentdao.findBySubId(subId);

	}

	@Override
	public void deleteAllAssignmentBySub(int subid,AssignmentDTO assignmentDTO) {
		Teacher teacher = teacherdao.findById(assignmentDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", assignmentDTO.getTeacherId()));
		Sub sub = subdao.findById(subid)
				.orElseThrow(() -> new ResourceNotFoundException("sub", "id", subid));
		if (teacher.getSub().contains(sub)) {
			List<Assignment> assignment = assignmentdao.findBySubId(subid);
			if (!assignment.isEmpty()) {
				assignmentdao.deleteAll(assignment);
			} else {
				throw new ResourceBadRequestException("no assignment for this subject");

			}
		} else {
			throw new ResourceBadRequestException("you are not allwed");

		}

	}

	@Override
	public String uploadFile(int id, MultipartFile file) {
		try {
			Assignment assignment = assignmentdao.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("assignment", "id", id));
			assignment.setPdf(file.getBytes()); // Assuming 'pdf' field can hold PDF bytes
			assignmentdao.save(assignment);
			return "File uploaded successfully";
		} catch (IOException e) {
			throw new RuntimeException("Failed to upload file", e);
		}
	}

	@Override
	public byte[] downloadFile(int id) {
		Assignment assignment = assignmentdao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("assignment", "id", id));
		return assignment.getPdf();
	}

}
