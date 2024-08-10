package com.smsv2.smsv2.OtpService;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.annotation.PostConstruct;
@Service
public class PhoneService {
	@Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;
    
    @PostConstruct
    private void initTwilio() {
        Twilio.init(accountSid, authToken);
    }


    public void sendOtp(String recipientPhoneNumber, String otpCode) {
        // Ensure the phone number is in E.164 format
        String formattedPhoneNumber = formatPhoneNumber(recipientPhoneNumber);

        Message.creator(new PhoneNumber(formattedPhoneNumber), new PhoneNumber(twilioPhoneNumber),
                "Your OTP code is: " + otpCode)
                .create();
    }

    private String formatPhoneNumber(String phoneNumber) {
        // Assuming all numbers are for a specific country, e.g., India (+91)
        // You might need to adjust this logic based on your use case.
        if (phoneNumber.startsWith("+")) {
            return phoneNumber;
        } else {
            return "+91" + phoneNumber; // Assuming Indian phone numbers
        }
    }
    public String genereteOtp() {
		Random random = new Random();
		int otpvalue = 100000 + random.nextInt(90000);
		return String.valueOf(otpvalue);
	}
}
