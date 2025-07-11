package com.example.EmailServiceApplication.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EmailLog {
	@Id
	private String id;
	private String toAddress;
	private String subject;
	private String body;
	private boolean success;
	private String message;
	private LocalDateTime sentAt;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
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
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getSentAt() {
		return sentAt;
	}
	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}
	@Override
	public String toString() {
		return "EmailLog [id=" + id + ", toAddress=" + toAddress + ", subject=" + subject + ", body=" + body
				+ ", success=" + success + ", message=" + message + ", sentAt=" + sentAt + "]";
	}
	
	
}
