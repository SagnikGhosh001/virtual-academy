package com.smsv2.smsv2.entity;


import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "department")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dept {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false,unique = true)
	private String deptname;
	
	@ManyToMany
	@JsonBackReference
	private List<Sem> sem= new ArrayList<>();
	
	@OneToMany(mappedBy = "dept",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonBackReference
	private List<Sub> sub= new ArrayList<>();
	
	@OneToMany(mappedBy = "dept")
	@JsonBackReference
	private List<Teacher> teacher= new ArrayList<>();
	
	@OneToMany(mappedBy = "dept")
	@JsonBackReference
	private List<Student> student= new ArrayList<>();
	

	@OneToMany(mappedBy = "dept",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonBackReference
	private List<Syllabus> syllabus= new ArrayList<>();
	
	
}
