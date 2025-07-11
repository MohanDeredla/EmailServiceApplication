package com.example.EmailServiceApplication.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EmailServiceApplication.Models.EmailRequest;
import com.example.EmailServiceApplication.Service.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {
	@Autowired
	private EmailService emailService;
	@PostMapping("/send")
	public Map<String,Object> sendEmail(@RequestBody EmailRequest request){
		return emailService.sendEmail(request);
		
	}
}
