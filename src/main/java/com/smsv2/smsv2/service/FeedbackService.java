package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import com.smsv2.smsv2.DTO.AssignmentDTO;
import com.smsv2.smsv2.DTO.FeedbackDTO;
import com.smsv2.smsv2.entity.Feedback;

public interface FeedbackService {
	List<Feedback> getAllFeedback();

	Optional<Feedback> getFeedbackById(int id);

	void addFeedback(FeedbackDTO feedbackDTO);

	void updateFeedback(int id, FeedbackDTO feedbackDTO);

	void deleteFeedbackById(int id, FeedbackDTO feedbackDTO);

	void deleteAllFeedback(FeedbackDTO feedbackDTO);
}
