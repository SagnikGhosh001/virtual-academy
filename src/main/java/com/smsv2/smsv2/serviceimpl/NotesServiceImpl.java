package com.smsv2.smsv2.serviceimpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.NotesDTO;
import com.smsv2.smsv2.dao.AdminDao;
import com.smsv2.smsv2.dao.NotesDao;
import com.smsv2.smsv2.dao.SubDao;
import com.smsv2.smsv2.dao.TeacherDao;
import com.smsv2.smsv2.entity.Admin;
import com.smsv2.smsv2.entity.Attendence;
import com.smsv2.smsv2.entity.Notes;
import com.smsv2.smsv2.entity.Sub;
import com.smsv2.smsv2.entity.Teacher;
import com.smsv2.smsv2.exception.ResourceBadRequestException;
import com.smsv2.smsv2.exception.ResourceNotFoundException;
import com.smsv2.smsv2.service.NotesService;

@Transactional
@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesDao notesdao;

	@Autowired
	private SubDao subdao;

	@Autowired
	private AdminDao admindao;

	@Override
	public ResponseEntity<List<Notes>> getAllNotes() {

		List<Notes>notes= notesdao.findAll();
		return new ResponseEntity<>(notes,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Optional<Notes>> getNotesById(int id) {
		Optional<Notes> notesOptional = notesdao.findById(id);
		if (notesOptional.isEmpty()) {
			throw new ResourceNotFoundException("notes", "id", id);
		}
		return new ResponseEntity<>(notesOptional,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Notes>> getNotesBySubId(int id) {

		List<Notes>notes= notesdao.findBySubId(id);
		return new ResponseEntity<>(notes,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> addNotes(NotesDTO notesDTO) {
		Sub sub = subdao.findById(notesDTO.getSubId())
				.orElseThrow(() -> new ResourceNotFoundException("sub", "id", notesDTO.getSubId()));
		Admin admin = admindao.findById(notesDTO.getUserid())
				.orElseThrow(() -> new ResourceNotFoundException("admin", "id", notesDTO.getUserid()));
		if (admin.getRole().equals("admin")) {
			Notes notes = new Notes();
			notes.setSub(sub);
			notes.setLink(notesDTO.getLink());
			notes.setName(notesDTO.getName());
			notes.setDeptname(sub.getDept().getDeptname());
			notes.setSubname(sub.getSubname());
			notes.setSemname(sub.getSem().getSemname());
			notesdao.save(notes);
			return new ResponseEntity<>(notes,HttpStatus.CREATED);
		} else {
			throw new ResourceBadRequestException("your role should be admin");
		}

	}

	@Override
	public ResponseEntity<?> updateNotes(int id, NotesDTO notesDTO) {
		Admin admin = admindao.findById(notesDTO.getUserid())
				.orElseThrow(() -> new ResourceNotFoundException("admin", "id", notesDTO.getUserid()));
		if (admin.getRole().equals("admin")) {
			Notes notes = notesdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("notes", "id", id));
			notes.setLink(notesDTO.getLink());
			notes.setName(notesDTO.getName());
			notesdao.save(notes);
			return new ResponseEntity<>(notes,HttpStatus.OK);
		} else {
			throw new ResourceBadRequestException("your role should be admin");
		}

	}

	@Override
	public ResponseEntity<?> deleteNotesByid(int id, NotesDTO notesDTO) {
		Admin admin = admindao.findById(notesDTO.getUserid())
				.orElseThrow(() -> new ResourceNotFoundException("admin", "id", notesDTO.getUserid()));
		if (admin.getRole().equals("admin")) {
			notesdao.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			throw new ResourceBadRequestException("your role should be admin");
		}

	}

	@Override
	public ResponseEntity<?> deleteAllNotes(NotesDTO notesDTO) {
		Admin admin = admindao.findById(notesDTO.getUserid())
				.orElseThrow(() -> new ResourceNotFoundException("admin", "id", notesDTO.getUserid()));
		if (admin.getRole().equals("admin")) {
			notesdao.deleteAll();
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			throw new ResourceBadRequestException("your role should be admin");
		}

	}

	@Override
	public ResponseEntity<String> uploadFile(int id, MultipartFile file) {
		try {
			Notes notes = notesdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("notes", "id", id));
			notes.setPdf(file.getBytes()); // Assuming 'pdf' field can hold PDF bytes
			notesdao.save(notes);
			String msg= "File uploaded successfully";
			return new ResponseEntity<>(msg,HttpStatus.OK);
		} catch (IOException e) {
			throw new RuntimeException("Failed to upload file", e);
		}
	}

	@Override
	public byte[] downloadFile(int id) {
		Notes notes = notesdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("notes", "id", id));
		return notes.getPdf();

	}

}
