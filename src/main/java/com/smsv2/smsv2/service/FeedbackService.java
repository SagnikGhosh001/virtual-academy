package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.smsv2.smsv2.DTO.AssignmentDTO;
import com.smsv2.smsv2.DTO.FeedbackDTO;
import com.smsv2.smsv2.entity.Feedback;

public interface FeedbackService {
	ResponseEntity<List<Feedback>> getAllFeedback();

	ResponseEntity<Optional<Feedback>> getFeedbackById(int id);

	ResponseEntity<?> addFeedback(FeedbackDTO feedbackDTO);

	ResponseEntity<?> updateFeedback(int id, FeedbackDTO feedbackDTO);

	ResponseEntity<?> deleteFeedbackById(int id, FeedbackDTO feedbackDTO);

	ResponseEntity<?> deleteAllFeedback(FeedbackDTO feedbackDTO);
}
