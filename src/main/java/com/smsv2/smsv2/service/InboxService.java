package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import com.smsv2.smsv2.DTO.AssignmentDTO;
import com.smsv2.smsv2.DTO.InboxDTO;
import com.smsv2.smsv2.entity.Inbox;
import com.smsv2.smsv2.entity.Student;
import com.smsv2.smsv2.entity.Teacher;

public interface InboxService {
	// get all inbox
	List<Inbox> getAllInbox();

	// get inbox by id
	Optional<Inbox> getAllInboxById(int id);

	// get inbox by student id
	List<Inbox> getAllinboxByStudentId(int id);

	// get inbox by student reg
	List<Inbox> getAllinboxByStudentReg(String reg);

	// get inbox by teacher id
	List<Inbox> getAllinboxByTeacherId(int teacherId);

	// add new Inbox
	void addInbox(InboxDTO inbox);

	// update inbox
	void updateInbox(int id, InboxDTO inbox);

	// delete a inbox
	void delteInboxById(int id, InboxDTO inboxDTO);

	// delete all inbox
	void deleteAllInbox(InboxDTO inboxDTO);
}
