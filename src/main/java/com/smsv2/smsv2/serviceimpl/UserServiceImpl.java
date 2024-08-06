package com.smsv2.smsv2.serviceimpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smsv2.smsv2.DTO.UserDTO;
import com.smsv2.smsv2.OtpService.EmailService;
import com.smsv2.smsv2.OtpService.PhoneService;
import com.smsv2.smsv2.config.JwtUtil;
import com.smsv2.smsv2.dao.UserDao;
import com.smsv2.smsv2.entity.LoginResponse;
import com.smsv2.smsv2.entity.User;
import com.smsv2.smsv2.exception.ResourceBadRequestException;
import com.smsv2.smsv2.exception.ResourceInternalServerErrorException;
import com.smsv2.smsv2.exception.ResourceNotFoundException;
import com.smsv2.smsv2.service.UserService;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userdao;

	@Autowired
	private EmailService emailservice;

	@Autowired
	private PhoneService phoneservice;

//	@Override
//	public User login(UserDTO userDTO) {
//		User user = userdao.findByEmail(userDTO.getEmail())
//				.orElseThrow(() -> new ResourceNotFoundException("user", "email", userDTO.getEmail()));
//		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
//		if (user != null && user.isEmailVerified() && bcrypt.matches(userDTO.getPassword(), user.getPassword())) {
//			return user;
//		} else {
//			throw new ResourceBadRequestException("use valid email and password");
//
//		}
//	}

	@Autowired
	private JwtUtil jwtUtil;

	public LoginResponse login(UserDTO userDTO) {
		User user = userdao.findByEmail(userDTO.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("user", "email", userDTO.getEmail()));

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		if (user != null && user.isEmailVerified() && bcrypt.matches(userDTO.getPassword(), user.getPassword())) {
			// Generate JWT token
			String token = jwtUtil.generateToken(user.getEmail());

			// Return user and token
			return new LoginResponse(token, user);
		} else {
			throw new ResourceBadRequestException("Use valid email and password");
		}
	}

	@Override
	public void emailVerify(UserDTO userDTO) {
		User user = userdao.findByEmail(userDTO.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("user", "email", userDTO.getEmail()));

		if (user == null) {
			throw new ResourceNotFoundException("user", "email", userDTO.getEmail());
		} else if (user.isEmailVerified()) {
			throw new ResourceBadRequestException("student already verify");
		} else if (userDTO.getOtp().equals(user.getEmailotp()) && !user.isIsemailOtpUsed()
				&& user.getExpiryDateEmailOtp().isAfter(LocalDateTime.now())) {
			user.setEmailVerified(true);
			user.setIsemailOtpUsed(true);
			userdao.save(user);
		} else {
			throw new ResourceBadRequestException("invalid or used OTP or expire OTP");

		}

	}

	@Override
	public void updateEmail(int id, UserDTO userDTO) {
		User user = userdao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
		Optional<User> emailUser=userdao.findByEmail(userDTO.getEmail());
		if(emailUser.isPresent()) {
			throw new ResourceInternalServerErrorException("user","email",userDTO.getEmail());
		}
		if (id == userDTO.getCurrentUserId()) {
			user.setEmail(userDTO.getEmail());
			user.setEmailVerified(false);
			userdao.save(user);
		} else {
			throw new ResourceBadRequestException("you are not allwed");

		}

	}

	@Override
	public void addPhone(int id, UserDTO userDTO) {
		User user = userdao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
		Optional<User> emailUser=userdao.findByPhone(userDTO.getPhone());
		if(emailUser.isPresent()) {
			throw new ResourceInternalServerErrorException("user","phone",userDTO.getPhone());
		}
		if (id == userDTO.getCurrentUserId()) {
			user.setPhone(userDTO.getPhone());
			user.setPhoneverified(false);
			String otp = phoneservice.genereteOtp();
			phoneservice.sendOtp(userDTO.getPhone(), otp);
			user.setExpiryDatePhoneOtp(LocalDateTime.now().plusMinutes(10));
			user.setPhoneotp(otp);
			userdao.save(user);
		} else {
			throw new ResourceBadRequestException("you are not allwed");

		}

	}

	@Override
	public void phoneVerify(UserDTO userDTO) {
		User user = userdao.findByPhone(userDTO.getPhone())
				.orElseThrow(() -> new ResourceNotFoundException("user", "phone", userDTO.getPhone()));
		if (user == null) {
			throw new ResourceNotFoundException("user", "phone", userDTO.getPhone());

		} else if (user.isPhoneverified()) {
			throw new ResourceBadRequestException("student already verify");
		} else if (userDTO.getOtp().equals(user.getPhoneotp()) && !user.isPhoneOtpUsed()
				&& user.getExpiryDatePhoneOtp().isAfter(LocalDateTime.now())) {
			user.setPhoneverified(true);
			user.setPhoneOtpUsed(true);
			userdao.save(user);
		} else {
			throw new ResourceBadRequestException("internal error");

		}

	}

	@Override
	public void sendOtpTOEmaail(UserDTO userDTO) {
		User user = userdao.findByEmail(userDTO.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("user", "email", userDTO.getEmail()));

		String otp = emailservice.genereteOtp();
		user.setEmailotp(otp);
		user.setExpiryDateEmailOtp(LocalDateTime.now().plusMinutes(10));
		emailservice.sendVerficationEmail(userDTO.getEmail(), otp);
		user.setIsemailOtpUsed(false);
		userdao.save(user);

	}

	@Override
	public void updatePassword(int id, UserDTO userDTO) {
		User user = userdao.findByEmail(userDTO.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("user", "email", userDTO.getEmail()));

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		if (id == userDTO.getCurrentUserId()) {
			if (bcrypt.matches(userDTO.getPassword(), user.getPassword())) {
				user.setPassword(bcrypt.encode(userDTO.getChangePassword()));
				userdao.save(user);
			} else {
				throw new ResourceBadRequestException("use valid password");

			}
		} else {
			throw new ResourceBadRequestException("you are not allwed");

		}

	}

	@Override
	public void forgetPassword(UserDTO userDTO) {
		User user = userdao.findByEmail(userDTO.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("user", "email", userDTO.getEmail()));
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

		if (userDTO.getOtp().equals(user.getEmailotp()) && !user.isIsemailOtpUsed()
				&& user.getExpiryDateEmailOtp().isAfter(LocalDateTime.now())) {
			user.setPassword(bcrypt.encode(userDTO.getPassword()));
			user.setIsemailOtpUsed(true);
			userdao.save(user);
		} else {
			throw new ResourceBadRequestException("invalid otp or expire otp or used Otp");

		}

	}

}
