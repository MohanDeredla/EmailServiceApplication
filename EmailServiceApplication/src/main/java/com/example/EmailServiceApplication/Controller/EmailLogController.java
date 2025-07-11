package com.example.EmailServiceApplication.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EmailServiceApplication.Models.EmailLog;
import com.example.EmailServiceApplication.Repository.EmailLogRepository;

@RestController
@RequestMapping("/api/logs")
public class EmailLogController {
	@Autowired
    private EmailLogRepository emailLogRepository;
	@GetMapping
	public List<EmailLog> getAllLogs(){
		return emailLogRepository.findAll();
	}
}
