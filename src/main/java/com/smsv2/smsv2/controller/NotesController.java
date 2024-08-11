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
	public ResponseEntity<?> notesbyId(@PathVariable("id") int id) {
		return new ResponseEntity<>(notesservice.getNotesById(id), HttpStatus.OK);
	}

	@GetMapping("/notesbySubId/{subid}")
	public ResponseEntity<?> notesbySubId(@PathVariable("subid") int subid) {
		return new ResponseEntity<>(notesservice.getNotesBySubId(subid), HttpStatus.OK);
	}

	@PostMapping("/addnotes")
	public ResponseEntity<?> addNotes(@RequestBody NotesDTO notesDTO) {

		this.notesservice.addNotes(notesDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@PutMapping("/updateNotes/{id}")
	public ResponseEntity<?> udateNotes(@PathVariable("id") int id, @RequestBody NotesDTO notesDTO) {
		this.notesservice.updateNotes(id, notesDTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PostMapping("/uploadpdf/{id}/{role}")
	public ResponseEntity<String> uploadPdf(@PathVariable("role") String role, @PathVariable int id,
			@RequestParam("file") MultipartFile file) {
		if (role.equals("admin")) {
			ResponseEntity<String> responseEntity = notesservice.uploadFile(id, file);
			String message = responseEntity.getBody(); 
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

	@DeleteMapping("/deleteNotesbyId/{id}")
	public ResponseEntity<?> deleteTopicbyId(@PathVariable("id") int id, @RequestBody NotesDTO notesDTO) {
		this.notesservice.deleteNotesByid(id, notesDTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@DeleteMapping("/deleteAllNotes")
	public ResponseEntity<?> deleteAllTopicby(@RequestBody NotesDTO notesDTO) {
			this.notesservice.deleteAllNotes(notesDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		

	}
}
