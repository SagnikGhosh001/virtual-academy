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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subject")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sub {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String subname;
	@ManyToOne
	@JsonBackReference
	private Dept dept;
	
	private String deptname;
	
	@ManyToOne
	@JsonBackReference
	private Sem sem;
	
	private String semname;
	
	@ManyToOne
	@JsonBackReference
	private Teacher teacher;
	
	private String teachername;
	
	@OneToMany(mappedBy = "sub",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonBackReference
	private List<Assignment> assignment= new ArrayList<>();
	@OneToMany(mappedBy = "sub",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonBackReference
	private List<Topic> topics= new ArrayList<>();
	
	@OneToMany(mappedBy = "sub",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonBackReference
	private List<Marks> marks= new ArrayList<>();
	
	@OneToMany(mappedBy = "sub",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonBackReference
	private List<Book> book= new ArrayList<>();
	
	@OneToMany(mappedBy = "sub",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonBackReference
	private List<Notes> notes= new ArrayList<>();
}
