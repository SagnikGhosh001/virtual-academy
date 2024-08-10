package com.smsv2.smsv2.serviceimpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.TopicDTO;
import com.smsv2.smsv2.dao.SubDao;
import com.smsv2.smsv2.dao.TeacherDao;
import com.smsv2.smsv2.dao.TopicDao;
import com.smsv2.smsv2.entity.Attendence;
import com.smsv2.smsv2.entity.Sub;
import com.smsv2.smsv2.entity.Teacher;
import com.smsv2.smsv2.entity.Topic;
import com.smsv2.smsv2.exception.ResourceBadRequestException;
import com.smsv2.smsv2.exception.ResourceNotFoundException;
import com.smsv2.smsv2.service.TopicService;

@Transactional
@Service
public class TopicServiceImpl implements TopicService {

	@Autowired
	private TopicDao topicdao;

	@Autowired
	private TeacherDao teacherdao;

	@Autowired
	private SubDao subdao;

	@Override
	public List<Topic> getAllTopic() {
		return topicdao.findAll();
	}

	@Override
	public Optional<Topic> getAllTopicById(int id) {
		Optional<Topic> topic = topicdao.findById(id);
		if (topic.isEmpty()) {
			throw new ResourceNotFoundException("topic", "id", id);
		}
		return topic;
	}

	@Override
	public List<Topic> getAllTopicBySubId(int subId) {
		return topicdao.findBySubId(subId);
	}

	@Override
	public void addTopic(TopicDTO topicDTO) {
		Teacher teacher = teacherdao.findById(topicDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", topicDTO.getTeacherId()));
		Sub sub = subdao.findById(topicDTO.getSubId())
				.orElseThrow(() -> new ResourceNotFoundException("sub", "id", topicDTO.getSubId()));
		if (teacher.getSub().contains(sub)) {
			Topic topic = new Topic();
			topic.setName(topicDTO.getName());
			topic.setSub(sub);
			topic.setLink(topicDTO.getLink());
			topic.setDeptname(sub.getDeptname());
			topic.setSemname(sub.getSemname());
			topic.setSubname(sub.getSubname());
			topicdao.save(topic);
		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}
	}

	@Override
	public void updateTopic(int id, TopicDTO topicDTO) {
		Topic existTopic = topicdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("topic", "id", id));
		Teacher teacher = teacherdao.findById(topicDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", topicDTO.getTeacherId()));

		if (teacher.getSub().contains(existTopic.getSub())) {
			existTopic.setName(topicDTO.getName());
			existTopic.setLink(topicDTO.getLink());
			topicdao.save(existTopic);

		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}

	}

	@Override
	public void delteTopicById(int id, TopicDTO topicDTO) {
		Topic existTopic = topicdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("topic", "id", id));
		Teacher teacher = teacherdao.findById(topicDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", topicDTO.getTeacherId()));

		if (teacher.getSub().contains(existTopic.getSub())) {
			topicdao.delete(existTopic);
		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}

	}

	@Override
	public void delteTopicBySubId(int subId, TopicDTO topicDTO) {
		List<Topic> existTopic = topicdao.findBySubId(subId);
		Teacher teacher = teacherdao.findById(topicDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", topicDTO.getTeacherId()));
		Sub sub = subdao.findById(topicDTO.getSubId())
				.orElseThrow(() -> new ResourceNotFoundException("sub", "id", topicDTO.getSubId()));
		if (teacher.getSub().contains(sub)) {
			topicdao.deleteAll(existTopic);
		} else {
			throw new ResourceBadRequestException("you are not allowed");
		}

	}

	@Override
	public void deleteAllTopic(TopicDTO topicDTO) {
		Teacher teacher = teacherdao.findById(topicDTO.getTeacherId())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", topicDTO.getTeacherId()));
		if (teacher.getRole().equals("pic")) {
			topicdao.deleteAll();
		} else {
			throw new ResourceBadRequestException("you are not pic");
		}

	}

	@Override
	public String uploadFile(int id, MultipartFile file) {
		try {
			Topic topic = topicdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("topic", "id", id));
			topic.setPdf(file.getBytes());
			topicdao.save(topic);
			return "File uploaded successfully";
		} catch (IOException e) {
			throw new RuntimeException("Failed to upload file", e);
		}
	}

	@Override
	public byte[] downloadFile(int id) {
		Topic topic = topicdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("topic", "id", id));
		return topic.getPdf();
	}
}