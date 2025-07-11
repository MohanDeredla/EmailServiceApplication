package com.example.EmailServiceApplication.Models;

import jakarta.persistence.Entity;

@Entity
public class EmailRequest {
	
	private String id;
	private String to;
	private String subject;
	private String body;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	@Override
	public String toString() {
		return "EmailRequest [id=" + id + ", to=" + to + ", subject=" + subject + ", body=" + body + "]";
	}
}
