package com.smsv2.smsv2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "syllabus")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Syllabus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String name;
	private String link;
	@Lob
	@Column(name = "pdf", columnDefinition = "LONGBLOB")
	private byte[] pdf;
	@ManyToOne
	@JsonBackReference
	private Dept dept;
	
	private String deptname;
}
