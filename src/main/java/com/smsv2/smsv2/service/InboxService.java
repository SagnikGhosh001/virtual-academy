package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.smsv2.smsv2.DTO.AssignmentDTO;
import com.smsv2.smsv2.DTO.InboxDTO;
import com.smsv2.smsv2.entity.Inbox;
import com.smsv2.smsv2.entity.Student;
import com.smsv2.smsv2.entity.Teacher;

public interface InboxService {
	// get all inbox
	ResponseEntity<List<Inbox>> getAllInbox();

	// get inbox by id
	ResponseEntity<Optional<Inbox>> getAllInboxById(int id);

	// get inbox by student id
	ResponseEntity<List<Inbox>> getAllinboxByStudentId(int id);

	// get inbox by student reg
	ResponseEntity<List<Inbox>> getAllinboxByStudentReg(String reg);

	// get inbox by teacher id
	ResponseEntity<List<Inbox>> getAllinboxByTeacherId(int teacherId);

	// add new Inbox
	ResponseEntity<?> addInbox(InboxDTO inbox);

	// update inbox
	ResponseEntity<?> updateInbox(int id, InboxDTO inbox);

	// delete a inbox
	ResponseEntity<?> delteInboxById(int id, InboxDTO inboxDTO);

	// delete all inbox
	ResponseEntity<?> deleteAllInbox(InboxDTO inboxDTO);
}
