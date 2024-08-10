package com.smsv2.smsv2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "attendence")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attendence {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false, unique = true)
	private String name;

	private String link;

	@Lob
	@Column(name = "pdf", columnDefinition = "LONGBLOB")
	private byte[] pdf;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sub_id", referencedColumnName = "id")
	@JsonBackReference
	private Sub subid;
	
	private String subname;
	
	private String deptname;
	
	private String semname;
	
	private String teachername;
}
