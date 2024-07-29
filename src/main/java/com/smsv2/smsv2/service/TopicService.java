package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.TopicDTO;
import com.smsv2.smsv2.entity.Sub;
import com.smsv2.smsv2.entity.Topic;

public interface TopicService {
	// get all Topic
	List<Topic> getAllTopic();

	// get Topic by id
	Optional<Topic> getAllTopicById(int id);

	// get Topic by Sub id
	List<Topic> getAllTopicBySubId(int subId);

	String uploadFile(int id, MultipartFile file);

	byte[] downloadFile(int id);

	// add new Topic
	void addTopic(TopicDTO topicDTO);

	// update Topic
	void updateTopic(int id, TopicDTO topic);

	

	// delete a Topic
	void delteTopicById(int id, TopicDTO topicDTO);

	// delete a Topic
	void delteTopicBySubId(int subId, TopicDTO topicDTO);

	// delete all Topic
	void deleteAllTopic(TopicDTO topicDTO);
}
