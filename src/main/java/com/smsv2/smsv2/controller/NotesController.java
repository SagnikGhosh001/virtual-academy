package com.smsv2.smsv2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.NotesDTO;
import com.smsv2.smsv2.DTO.TopicDTO;
import com.smsv2.smsv2.service.NotesService;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

	@Autowired
	private NotesService notesservice;

	@GetMapping("/allnotes")
	public ResponseEntity<?> allNotes() {
		return new ResponseEntity<>(notesservice.getAllNotes(), HttpStatus.OK);
	}

	@GetMapping("/notesbyId/{id}")
	public ResponseEntity<?> notesbyId(@PathVariable int id) {
		return new ResponseEntity<>(notesservice.getNotesById(id), HttpStatus.OK);
	}

	@GetMapping("/notesbySubId/{id}")
	public ResponseEntity<?> notesbySubId(@PathVariable int id) {
		return new ResponseEntity<>(notesservice.getNotesBySubId(id), HttpStatus.OK);
	}

	@PostMapping("/addnotes/{role}")
	public ResponseEntity<?> addNotes(@PathVariable("role") String role, @RequestBody NotesDTO notesDTO) {
		if (role.equals("admin")) {
			this.notesservice.addNotes(notesDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/updateNotesName/{id}/{role}")
	public ResponseEntity<?> udateNotes(@PathVariable int id, @PathVariable("role") String role,
			@RequestBody NotesDTO notesDTO) {
		if (role.equals("admin")) {
			this.notesservice.updateNotes(id, notesDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}

	}



	@PostMapping("/uploadpdf/{id}/{role}")
    public ResponseEntity<String> uploadPdf(@PathVariable("role") String role,@PathVariable int id, @RequestParam("file") MultipartFile file) {
		if (role.equals("admin")) {
			String message = notesservice.uploadFile(id, file);
	        return ResponseEntity.status(HttpStatus.OK).body(message);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}
		
    }

    @GetMapping("/downloadpdf/{id}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable int id) {
        byte[] fileData = notesservice.downloadFile(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF); // Set content type to PDF
        headers.setContentDispositionFormData("attachment", "attendence_document.pdf"); // Set filename for download

        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }
	
	
	@DeleteMapping("/deleteNotesbyId/{id}/{role}")
	public ResponseEntity<?> deleteTopicbyId(@PathVariable int id, @PathVariable("role") String role) {
		if (role.equals("admin")) {
			this.notesservice.deleteNotesByid(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}

	}



	@DeleteMapping("/deleteAllNotes/{role}")
	public ResponseEntity<?> deleteAllTopicby(@PathVariable("role") String role) {
		if (role.equals("admin")) {
			this.notesservice.deleteAllNotes();
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}

	}
}
