package com.example.EmailServiceApplication.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EmailServiceApplication.Models.EmailRequest;
import com.example.EmailServiceApplication.Service.EmailService;

@RestController
@RequestMapping
public class EmailController {
	@Autowired
	private EmailService emailService;
	
	public Map<String,Object> sendEmail(@RequestBody EmailRequest request){
		return emailService.sendEmail(request);
		
	}
}
