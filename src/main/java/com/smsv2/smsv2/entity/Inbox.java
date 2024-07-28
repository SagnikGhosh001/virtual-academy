package com.smsv2.smsv2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inbox")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inbox {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JsonBackReference
	private Teacher teacher;
	
	private String teacherName;
	
	private String teacherDept;
	
	private String studentName; 
	
	@ManyToOne
	@JsonBackReference
	private Student student;
	private String msg;
}
