package com.example.EmailServiceApplication.Provider;

import org.springframework.stereotype.Component;

import com.example.EmailServiceApplication.Models.EmailRequest;

@Component
public class ProviderA implements EmailProvider{

	@Override
	public boolean sendEmail(EmailRequest request) {
		return true;
	}

	@Override
	public String getName() {
		return "ProviderA";
	}

}
