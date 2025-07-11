package com.example.EmailServiceApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EmailServiceApplication.Models.EmailLog;

public interface EmailLogRepository extends JpaRepository<EmailLog, String>{

}
