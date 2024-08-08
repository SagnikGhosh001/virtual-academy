package com.smsv2.smsv2.entity;


import java.util.List;
import java.util.ArrayList;

import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
@Table(name = "teacher")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Teacher extends User{



	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String gender;
	@Lob
	@Column(name = "image", columnDefinition = "LONGBLOB")
	private byte[] pic;

	private String role = "teacher";

	@ManyToMany
	private List<Sem> sem = new ArrayList<>();

	

	@OneToMany(mappedBy = "teacher")
	@JsonBackReference
	private List<Sub> sub= new ArrayList<>() ;

	@ManyToOne
	@JsonBackReference
	private Dept dept;

	private String deptname;
	
	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonBackReference
	private List<Inbox> inbox= new ArrayList<>();

	@OneToMany(mappedBy = "teacherId")
	@JsonBackReference
	private List<Assignment> assignment= new ArrayList<>();

	

	// self,google,facebook
//	private Providers provider = Providers.SELF;
//	private String providerId;

}
