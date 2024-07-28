package com.smsv2.smsv2.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsv2.smsv2.entity.Sub;
import com.smsv2.smsv2.entity.Topic;

import java.util.List;


public interface TopicDao extends JpaRepository<Topic, Integer> {
List<Topic> findBySubId(int subId);
}
