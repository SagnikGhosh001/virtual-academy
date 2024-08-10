package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.AssignmentDTO;
import com.smsv2.smsv2.DTO.NotesDTO;
import com.smsv2.smsv2.entity.Notes;

public interface NotesService {
	
	List<Notes> getAllNotes();
	
	Optional<Notes> getNotesById(int id);
	
	List<Notes> getNotesBySubId(int id);
	
	String uploadFile(int id, MultipartFile file);
    byte[] downloadFile(int id);
	
	
	void addNotes(NotesDTO notesDTO);
	
	void updateNotes(int id,NotesDTO notesDTO);

	
	void deleteNotesByid(int id,NotesDTO notesDTO);
	
	void deleteAllNotes(NotesDTO notesDTO);
}
