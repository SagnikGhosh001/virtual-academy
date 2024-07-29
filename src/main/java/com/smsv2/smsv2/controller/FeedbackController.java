package com.smsv2.smsv2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smsv2.smsv2.DTO.FeedbackDTO;
import com.smsv2.smsv2.service.FeedbackService;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
	@Autowired
	private FeedbackService feedbackservice;
	
	
	@GetMapping("/allfeedback")
	public ResponseEntity<?> allFeedback() {
		return new ResponseEntity<>(feedbackservice.getAllFeedback(),HttpStatus.OK);
	}
	
	@GetMapping("/feedbackbyId/{id}")
	public ResponseEntity<?> topicbyId(@PathVariable("id") int id) {
		return new ResponseEntity<>(feedbackservice.getFeedbackById(id),HttpStatus.OK);
	}
	
	@PostMapping("/addfeedback")
	public ResponseEntity<?> addTopic(@RequestBody FeedbackDTO feedbackDTO) {
			this.feedbackservice.addFeedback(feedbackDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
		
		
	}
	@PutMapping("/updatefeedback/{id}")
	public ResponseEntity<?> udateTopic(@PathVariable("id") int id,@RequestBody FeedbackDTO feedbackDTO) {
			this.feedbackservice.updateFeedback(id, feedbackDTO);
		return new ResponseEntity<>(HttpStatus.OK);
		
		
	}

	
	@DeleteMapping("/deleteFeedbackbyId/{id}")
	public ResponseEntity<?> deleteFeedbackbyId(@PathVariable("id") int id,@RequestBody FeedbackDTO feedbackDTO) {
			this.feedbackservice.deleteFeedbackById(id, feedbackDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteAllFeedback")
	public ResponseEntity<?> deleteAllFeedback(@RequestBody FeedbackDTO feedbackDTO) {
			this.feedbackservice.deleteAllFeedback(feedbackDTO);
		return new ResponseEntity<>(HttpStatus.OK);
		
		
	}
}
