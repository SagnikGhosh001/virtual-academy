package com.smsv2.smsv2.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_reg")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false, unique = true)
	@Email(message = "Email should be valid")
	private String email;
//	@Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
	@Column(unique = true)
	private String phone;

	@Column(nullable = false)
//	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must be at least 8 characters long, contain at least one digit, one lowercase letter, one uppercase letter, one special character, and no whitespace")
	private String password;

	@OneToMany(mappedBy = "user")
	@JsonBackReference
	private List<Feedback> feedback= new ArrayList<>();
	
	private final String college="Central Calcutta Polytechnic";
	// verified
	private String emailotp;
	private String phoneotp;
	private boolean isemailOtpUsed=false;
	private boolean isPhoneOtpUsed=false;
    private LocalDateTime expiryDateEmailOtp;
    private LocalDateTime expiryDatePhoneOtp;
	private boolean emailVerified = false;
	private boolean phoneverified = false;

}
