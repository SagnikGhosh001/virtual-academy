package com.smsv2.smsv2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.TopicDTO;
import com.smsv2.smsv2.service.TopicService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/topic")
public class TopicController {

	@Autowired
	private TopicService topicService;

	@GetMapping("/alltopic")
	public ResponseEntity<?> allTopic() {
		return new ResponseEntity<>(topicService.getAllTopic(), HttpStatus.OK);
	}

	@GetMapping("/topicbyId/{id}")
	public ResponseEntity<?> topicbyId(@PathVariable int id) {
		return new ResponseEntity<>(topicService.getAllTopicById(id), HttpStatus.OK);
	}

	@GetMapping("/topicbySubId/{id}")
	public ResponseEntity<?> topicbySubId(@PathVariable int id) {
		return new ResponseEntity<>(topicService.getAllTopicBySubId(id), HttpStatus.OK);
	}

	@PostMapping("/addtopic")
	public ResponseEntity<?> addTopic( @RequestBody TopicDTO topicDTO) {
			this.topicService.addTopic(topicDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		

	}

	@PutMapping("/updatetopic/{id}")
	public ResponseEntity<?> udateTopic(@PathVariable int id,
			@RequestBody TopicDTO topicDTO) {
			this.topicService.updateTopic(id, topicDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		

	}

	@PostMapping("/uploadpdf/{id}/{role}")
	public ResponseEntity<String> uploadPdf(@PathVariable("role") String role, @PathVariable int id,
			@RequestParam("file") MultipartFile file) {
		if (role.equals("teacher") || role.equals("hod") || role.equals("pic") ) {
			String message = topicService.uploadFile(id, file);
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/downloadpdf/{id}")
	public ResponseEntity<byte[]> downloadPdf(@PathVariable int id) {
		byte[] fileData = topicService.downloadFile(id);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF); // Set content type to PDF
		headers.setContentDispositionFormData("attachment", "attendence_document.pdf"); // Set filename for download

		return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
	}

	@DeleteMapping("/deleteTopicbyId/{id}")
	public ResponseEntity<?> deleteTopicbyId(@PathVariable int id,
			@RequestBody TopicDTO topicDTO) {
			this.topicService.delteTopicById(id, topicDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		

	}

	@DeleteMapping("/deleteTopicbySubId/{subId}")
	public ResponseEntity<?> deleteTopicbySubId(@PathVariable int subId, 
			@RequestBody TopicDTO topicDTO) {
			this.topicService.delteTopicBySubId(subId, topicDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		

	}

	@DeleteMapping("/deleteAllTopic")
	public ResponseEntity<?> deleteAllTopicby(@RequestBody TopicDTO topicDTO) {
			this.topicService.deleteAllTopic(topicDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		

	}

}
