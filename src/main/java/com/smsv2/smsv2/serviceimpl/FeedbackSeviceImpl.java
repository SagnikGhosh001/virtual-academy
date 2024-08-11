package com.smsv2.smsv2.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smsv2.smsv2.DTO.FeedbackDTO;
import com.smsv2.smsv2.dao.AdminDao;
import com.smsv2.smsv2.dao.FeedbackDao;
import com.smsv2.smsv2.dao.UserDao;
import com.smsv2.smsv2.entity.Admin;
import com.smsv2.smsv2.entity.Feedback;
import com.smsv2.smsv2.entity.Teacher;
import com.smsv2.smsv2.entity.User;
import com.smsv2.smsv2.exception.ResourceBadRequestException;
import com.smsv2.smsv2.exception.ResourceNotFoundException;
import com.smsv2.smsv2.service.FeedbackService;
@Transactional
@Service
public class FeedbackSeviceImpl implements FeedbackService {

	@Autowired
	private FeedbackDao feedbackdao;

	@Autowired
	private UserDao userdao;
	
	@Autowired
	private AdminDao admindao;
	
	
	@Override
	public ResponseEntity<List<Feedback>> getAllFeedback() {

		List<Feedback> feedback= feedbackdao.findAll();
		return new ResponseEntity<>(feedback,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Optional<Feedback>> getFeedbackById(int id) {
	    Optional<Feedback> feedbackOpt = feedbackdao.findById(id);
	    if (feedbackOpt.isPresent()) {
	    	return new ResponseEntity<>(feedbackOpt,HttpStatus.OK);
	        
	    } else {
	        throw new ResourceNotFoundException("feedback","id",id);
	    }
	}


	@Override
	public ResponseEntity<?> addFeedback(FeedbackDTO feedbackDTO) {
		User user = userdao.findByEmail(feedbackDTO.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("user","email",feedbackDTO.getEmail()));
		Feedback feedback = new Feedback();
		feedback.setUser(user);
		feedback.setMsg(feedbackDTO.getMsg());
		feedback.setRating(feedbackDTO.getRating());
		feedback.setEmailId(user.getEmail());
		feedbackdao.save(feedback);
		return new ResponseEntity<>(feedback,HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> updateFeedback(int id, FeedbackDTO feedbackDTO) {
		Feedback feedback = feedbackdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("feedback","id",id));
		if (feedback.getUser().getEmail() == feedbackDTO.getEmail()) {
			feedback.setMsg(feedbackDTO.getMsg());
			feedback.setRating(feedbackDTO.getRating());
			feedbackdao.save(feedback);
			return new ResponseEntity<>(feedback,HttpStatus.OK);
		} else {
			throw new ResourceBadRequestException("you are not allwed");
		}

	}

	

	@Override
	public ResponseEntity<?> deleteFeedbackById(int id,FeedbackDTO feedbackDTO) {
		Feedback feedback = feedbackdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("feedback","id",id));
		Optional<Admin> admin=admindao.findByEmail(feedbackDTO.getEmail());
		if (feedback.getUser().getEmail() == feedbackDTO.getEmail() || (admin.isPresent()&&admin.get().getRole().equals("admin"))) {
			feedbackdao.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			throw new ResourceBadRequestException("you are not allwed");
		}

	}

	@Override
	public ResponseEntity<?> deleteAllFeedback(FeedbackDTO feedbackDTO) {
		Optional<Admin> admin=admindao.findByEmail(feedbackDTO.getEmail());
		if(admin.isPresent()&&admin.get().getRole().equals("admin")) {
			feedbackdao.deleteAll();
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			throw new ResourceBadRequestException("you are not admin");
			
		}
		

	}

}
