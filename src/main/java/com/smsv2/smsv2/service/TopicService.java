package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.TopicDTO;
import com.smsv2.smsv2.entity.Sub;
import com.smsv2.smsv2.entity.Topic;

public interface TopicService {
	// get all Topic
	ResponseEntity<List<Topic>> getAllTopic();

	// get Topic by id
	ResponseEntity<Optional<Topic>> getAllTopicById(int id);

	// get Topic by Sub id
	ResponseEntity<List<Topic>> getAllTopicBySubId(int subId);

	ResponseEntity<String> uploadFile(int id, MultipartFile file);

	byte[] downloadFile(int id);

	// add new Topic
	ResponseEntity<?> addTopic(TopicDTO topicDTO);

	// update Topic
	ResponseEntity<?> updateTopic(int id, TopicDTO topic);

	

	// delete a Topic
	ResponseEntity<?> delteTopicById(int id, TopicDTO topicDTO);

	// delete a Topic
	ResponseEntity<?> delteTopicBySubId(int subId, TopicDTO topicDTO);

	// delete all Topic
	ResponseEntity<?> deleteAllTopic(TopicDTO topicDTO);
}
