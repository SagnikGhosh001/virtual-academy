package com.smsv2.smsv2.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "marks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Marks {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JsonBackReference
	private Sub sub;
	
	private String subname;
	
	private String deptName;
	
	private String semname;
	
	
	@Column(nullable = false)
	private int mark;
	
	
	
	@ManyToOne
	@JsonBackReference
	private Student reg;
	
	private String regNo;
	
	
	
	
	
}
