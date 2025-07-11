package com.example.EmailServiceApplication.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EmailServiceApplication.Models.EmailLog;
import com.example.EmailServiceApplication.Models.EmailRequest;
import com.example.EmailServiceApplication.Provider.EmailProvider;
import com.example.EmailServiceApplication.Repository.EmailLogRepository;

@Service
public class EmailService {
	@Autowired
	public List<EmailProvider> emailProvider;
	@Autowired
	public EmailLogRepository emailLogRepository;
	
	 private final Set<String> sentEmails = new HashSet<>();
	 private final Map<String, List<Long>> rateLimitMap = new HashMap<>();
	 private final Map<String, Integer> providerFailures = new HashMap<>();

	public Map<String, Object> sendEmail(EmailRequest request) {
		Map<String,Object> response=new HashMap<>();
		
		//Idempotency Check
		if(sentEmails.contains(request.getId())) {
			response.put("messgae","Duplicate request ignored");
			response.put("success",false);
			return response;
		}
		
		//Rate Limiting(5 per minute)
		long currentTime=System.currentTimeMillis();
		rateLimitMap.putIfAbsent(request.getTo(), new ArrayList<>());
		List<Long> timestamps =rateLimitMap.get(request.getTo());
		timestamps.removeIf(ts->currentTime-ts>60000);
		if(timestamps.size()>5) {
			response.put("message","Rate Limit Exceeded");
			response.put("success",false);
			return response;
		}
		
		//Circuit breaker check
		for(EmailProvider provider:emailProvider) {
			if(providerFailures.getOrDefault(provider.getName(), 0)>=3) continue;
			boolean sent=false;
			int attempt=0;
			int delay=1000;
			
			//Retry with exponential backoff
			while(attempt<3) {
				try {
					sent=provider.sendEmail(request);
					if(sent) break;
					Thread.sleep(delay);
					delay*=2;
					attempt++;
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(sent) {
				timestamps.add(currentTime);
				sentEmails.add(request.getId());
				emailLogRepository.save(new EmailLog(
                        request.getId(), request.getTo(), request.getSubject(), request.getBody(),
                        provider.getName(), true, "Email sent successfully", LocalDateTime.now()
                ));
				response.put("Provider", provider.getName());
				response.put("Success", true);
				response.put("message","Email sent Successfully");
				return response;
			}
			else {
				providerFailures.put(provider.getName(),
						providerFailures.getOrDefault(provider.getName(),0)+1);
				
			}
		}
		//All providers Failed
		emailLogRepository.save(new EmailLog(
				request.getId(), request.getTo(), request.getSubject(), request.getBody(),
                "N/A", false, "All providers failed", LocalDateTime.now()
				));
		response.put("success", false);
		response.put("message", "All providers Failed");
		return response;
	}

	
	
}
