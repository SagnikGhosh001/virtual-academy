package com.smsv2.smsv2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "admin")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User{
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String gender;
	@Lob
	@Column(name = "image", columnDefinition = "LONGBLOB")
	private byte[] pic;

	private String role = "admin";
}
