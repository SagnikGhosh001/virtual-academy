package com.smsv2.smsv2.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Feedback> getAllFeedback() {

		return feedbackdao.findAll();
	}

	@Override
	public Optional<Feedback> getFeedbackById(int id) {
	    Optional<Feedback> feedbackOpt = feedbackdao.findById(id);
	    if (feedbackOpt.isPresent()) {
	        return feedbackOpt;
	    } else {
	        throw new ResourceNotFoundException("feedback","id",id);
	    }
	}


	@Override
	public void addFeedback(FeedbackDTO feedbackDTO) {
		User user = userdao.findByEmail(feedbackDTO.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("user","email",feedbackDTO.getEmail()));
		Feedback feedback = new Feedback();
		feedback.setUser(user);
		feedback.setMsg(feedbackDTO.getMsg());
		feedback.setRating(feedbackDTO.getRating());
		feedback.setEmailId(user.getEmail());
		feedbackdao.save(feedback);

	}

	@Override
	public void updateFeedback(int id, FeedbackDTO feedbackDTO) {
		Feedback feedback = feedbackdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("feedback","id",id));
		if (feedback.getUser().getEmail() == feedbackDTO.getEmail()) {
			feedback.setMsg(feedbackDTO.getMsg());
			feedback.setRating(feedbackDTO.getRating());
			feedbackdao.save(feedback);
		} else {
			throw new ResourceBadRequestException("you are not allwed");
		}

	}

	

	@Override
	public void deleteFeedbackById(int id,FeedbackDTO feedbackDTO) {
		Feedback feedback = feedbackdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("feedback","id",id));
		Optional<Admin> admin=admindao.findByEmail(feedbackDTO.getEmail());
		if (feedback.getUser().getEmail() == feedbackDTO.getEmail() || (admin.isPresent()&&admin.get().getRole().equals("admin"))) {
			feedbackdao.deleteById(id);
		} else {
			throw new ResourceBadRequestException("you are not allwed");
		}

	}

	@Override
	public void deleteAllFeedback(FeedbackDTO feedbackDTO) {
		Optional<Admin> admin=admindao.findByEmail(feedbackDTO.getEmail());
		if(admin.isPresent()&&admin.get().getRole().equals("admin")) {
			feedbackdao.deleteAll();
		}else {
			throw new ResourceBadRequestException("you are not admin");
			
		}
		

	}

}
