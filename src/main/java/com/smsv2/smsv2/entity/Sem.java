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
@Table(name = "semester")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true,nullable = false)
	private String semname;
	
	@ManyToMany(mappedBy = "sem",cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JsonBackReference
	private List<Dept> dept= new ArrayList<>();
	
	@ManyToMany(mappedBy = "sem")
	@JsonBackReference
	private List<Teacher> teacher= new ArrayList<>();
	
	@OneToMany(mappedBy = "sem")
	@JsonBackReference
	private List<Student> student= new ArrayList<>();
	
	@OneToMany(mappedBy = "sem",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonBackReference
	private List<Sub> sub= new ArrayList<>();
	
	
}
