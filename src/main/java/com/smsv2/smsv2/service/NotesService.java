package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.AssignmentDTO;
import com.smsv2.smsv2.DTO.NotesDTO;
import com.smsv2.smsv2.entity.Notes;

public interface NotesService {
	
	ResponseEntity<List<Notes>> getAllNotes();
	
	ResponseEntity<Optional<Notes>> getNotesById(int id);
	
	ResponseEntity<List<Notes>> getNotesBySubId(int id);
	
	ResponseEntity<String> uploadFile(int id, MultipartFile file);
	byte[] downloadFile(int id);
	
	
	ResponseEntity<?> addNotes(NotesDTO notesDTO);
	
	ResponseEntity<?> updateNotes(int id,NotesDTO notesDTO);

	
	ResponseEntity<?> deleteNotesByid(int id,NotesDTO notesDTO);
	
	ResponseEntity<?> deleteAllNotes(NotesDTO notesDTO);
}
