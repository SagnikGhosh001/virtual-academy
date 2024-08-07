package com.smsv2.smsv2.OtpService;

import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	private final JavaMailSender javaMailSender;
	
	public EmailService(JavaMailSender javaMailSender) {
		this.javaMailSender=javaMailSender;
	}
	
	public void sendEmail(String to,String subjet,String body) {
		try {
			MimeMessage message=javaMailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(message,true);
			helper.setTo(to);
			helper.setSubject(subjet);
			helper.setText(body,true);
			javaMailSender.send(message);
		}catch (MessagingException e) {
            e.printStackTrace(); 
        }
		
	}
	
	public String genereteOtp() {
		Random random = new Random();
		int otpvalue = 100000 + random.nextInt(90000);
		return String.valueOf(otpvalue);
	}

	public void sendVerficationEmail(String email, String otp) {
		String subject = "Email verification";
		String body = "your verification otp is " + otp +" only valid for 10 minutes";
		sendEmail(email, subject, body);
	}
	public void sendVerficationEmail1(String email, String msg) {
		String subject = "Email verification";
		String body = msg;
		sendEmail(email, subject, body);
	}
}
