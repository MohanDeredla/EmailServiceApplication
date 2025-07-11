package com.example.EmailServiceApplication.Provider;

import com.example.EmailServiceApplication.Models.EmailRequest;

public interface EmailProvider {
	boolean sendEmail(EmailRequest request);
	String getName();
}
