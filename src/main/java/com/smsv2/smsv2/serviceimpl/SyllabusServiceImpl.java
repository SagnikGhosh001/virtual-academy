package com.smsv2.smsv2.serviceimpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.SyllabusDTO;
import com.smsv2.smsv2.dao.DeptDao;
import com.smsv2.smsv2.dao.SyllabusDao;
import com.smsv2.smsv2.dao.TeacherDao;
import com.smsv2.smsv2.entity.Attendence;
import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Syllabus;
import com.smsv2.smsv2.entity.Teacher;
import com.smsv2.smsv2.exception.ResourceBadRequestException;
import com.smsv2.smsv2.exception.ResourceNotFoundException;
import com.smsv2.smsv2.service.SyllabusService;
@Transactional
@Service
public class SyllabusServiceImpl implements SyllabusService {

	@Autowired
	private SyllabusDao syllabusdao;

	@Autowired
	private DeptDao deptdao;

	@Autowired
	private TeacherDao teacherdao;

	@Override
	public List<Syllabus> getAllSyllabus() {
		return syllabusdao.findAll();
	}

	@Override
	public Optional<Syllabus> getSyllabusById(int id) {
	    Optional<Syllabus> syllabusOptional = syllabusdao.findById(id);
	    if (syllabusOptional.isEmpty()) {
	        throw new ResourceNotFoundException("syllabus","id", id);
	    }
	    return syllabusOptional;
	}


	@Override
	public List<Syllabus> getSyllabusByDeptId(int deptId) {
		return syllabusdao.findByDeptId(deptId);
	}

	@Override
	public void addSyllabus(SyllabusDTO syllabusDTO) {
		Dept dept = deptdao.findById(syllabusDTO.getDept())
				.orElseThrow(() -> new ResourceNotFoundException("dept","id", syllabusDTO.getDept()));
		Teacher teacher = teacherdao.findById(syllabusDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher","id", syllabusDTO.getTeacherId()));
		if (teacher.getDept() == null) {
	        throw new ResourceBadRequestException("Teacher's department not found");
	    }
		
		if (teacher.getDept().getId() == dept.getId()) {
			Syllabus syllabus = new Syllabus();
			syllabus.setDept(dept);
			syllabus.setLink(syllabusDTO.getLink());
			syllabus.setName(syllabusDTO.getName());
			syllabus.setDeptname(dept.getDeptname());
			syllabusdao.save(syllabus);
		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}

	}

	@Override
	public void updateSyllabus(int id, SyllabusDTO syllabusDTO) {
		Syllabus syllabus = syllabusdao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("syllabus","id", id));
		Teacher teacher = teacherdao.findById(syllabusDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher","id", syllabusDTO.getTeacherId()));
		if (teacher.getDept().getId() == syllabus.getDept().getId()) {
			syllabus.setName(syllabusDTO.getName());
			syllabus.setLink(syllabusDTO.getLink());
			syllabusdao.save(syllabus);
		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}
		
	}



	@Override
	public void deleteSyllabusById(int id, SyllabusDTO syllabusDTO) {
		Syllabus syllabus = syllabusdao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("syllabus","id", id));
		Teacher teacher = teacherdao.findById(syllabusDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher","id", syllabusDTO.getTeacherId()));
		if (teacher.getDept() == null) {
	        throw new ResourceBadRequestException("Teacher's department not found");
	    }
		if (teacher.getDept().getId() == syllabus.getDept().getId()) {
			syllabusdao.delete(syllabus);
		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}

	}

	@Override
	public void deleteSyllabusByDeptId(int deptId, SyllabusDTO syllabusDTO) {
		List<Syllabus> syllabus = syllabusdao.findByDeptId(deptId);
		Teacher teacher = teacherdao.findById(syllabusDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher","id", syllabusDTO.getTeacherId()));
		if (teacher.getDept() == null) {
	        throw new ResourceBadRequestException("Teacher's department not found");
	    }
		if (teacher.getDept().getId()==deptId) {
			syllabusdao.deleteAll(syllabus);
		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}

	}

	@Override
	public void deleteAllSyllabus(SyllabusDTO syllabusDTO) {
		Teacher teacher = teacherdao.findById(syllabusDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher","id", syllabusDTO.getTeacherId()));
		
		if(teacher.getRole().equals("pic")) {
			syllabusdao.deleteAll();
		}else {
			throw new ResourceBadRequestException("you are not pic");
		}
		

	}
	
	@Override
    public String uploadFile(int id, MultipartFile file) {
        try {
            Syllabus syllabus = syllabusdao.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("syllabus","id", id));
            syllabus.setPdf(file.getBytes()); // Assuming 'pdf' field can hold PDF bytes
            syllabusdao.save(syllabus);
            return "File uploaded successfully";
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @Override
    public byte[] downloadFile(int id) {
        Syllabus syllabus = syllabusdao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("syllabus","id", id));
        return syllabus.getPdf();
    }

}
