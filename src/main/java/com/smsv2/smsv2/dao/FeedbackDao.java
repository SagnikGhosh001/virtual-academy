package com.smsv2.smsv2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsv2.smsv2.entity.Feedback;

public interface FeedbackDao extends JpaRepository<Feedback, Integer>{

}
