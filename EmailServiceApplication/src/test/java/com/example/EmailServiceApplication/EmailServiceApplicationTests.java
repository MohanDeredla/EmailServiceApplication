package com.example.EmailServiceApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.EmailServiceApplication.Models.EmailRequest;
import com.example.EmailServiceApplication.Provider.EmailProvider;
import com.example.EmailServiceApplication.Repository.EmailLogRepository;
import com.example.EmailServiceApplication.Service.EmailService;

@SpringBootTest
class EmailServiceApplicationTests {
	private EmailService emailService;
    private EmailProvider providerA;
    private EmailProvider providerB;
    private EmailLogRepository emailLogRepository;

    @BeforeEach
    void setUp() {
        providerA = mock(EmailProvider.class);
        providerB = mock(EmailProvider.class);
        emailLogRepository = mock(EmailLogRepository.class);

        emailService = new EmailService();
        emailService.emailProvider = Arrays.asList(providerA, providerB);
        emailService.emailLogRepository = emailLogRepository;
    }

    @Test
    void testSendEmail_Success() {
        EmailRequest request = new EmailRequest("email-001", "test@example.com", "Hi", "Body");

        when(providerA.sendEmail(request)).thenReturn(true);
        when(providerA.getName()).thenReturn("ProviderA");

        Map<String, Object> response = emailService.sendEmail(request);

        assertTrue((Boolean) response.get("success"));
        assertEquals("ProviderA", response.get("provider"));
        verify(emailLogRepository, times(1)).save(any());
    }

    @Test
    void testSendEmail_AllProvidersFail() {
        EmailRequest request = new EmailRequest("email-002", "fail@example.com", "Fail", "No go");

        when(providerA.sendEmail(request)).thenReturn(false);
        when(providerA.getName()).thenReturn("ProviderA");
        when(providerB.sendEmail(request)).thenReturn(false);
        when(providerB.getName()).thenReturn("ProviderB");

        Map<String, Object> response = emailService.sendEmail(request);

        assertFalse((Boolean) response.get("success"));
        assertEquals("All providers failed", response.get("message"));
        verify(emailLogRepository, times(1)).save(any());
    }

    @Test
    void testSendEmail_Idempotent() {
        EmailRequest request = new EmailRequest("email-003", "dupe@example.com", "Dupe", "Once only");

        when(providerA.sendEmail(request)).thenReturn(true);
        when(providerA.getName()).thenReturn("ProviderA");

        emailService.sendEmail(request); 
        Map<String, Object> response = emailService.sendEmail(request);

        assertFalse((Boolean) response.get("success"));
        assertEquals("Duplicate request ignored (idempotent)", response.get("message"));
        verify(emailLogRepository, times(1)).save(any());
    }

    @Test
    void testSendEmail_RateLimitExceeded() {
        EmailRequest request = new EmailRequest("email-004", "rate@example.com", "Rate", "Limit");

        when(providerA.sendEmail(any())).thenReturn(true);
        when(providerA.getName()).thenReturn("ProviderA");

       
        for (int i = 0; i < 5; i++) {
            EmailRequest temp = new EmailRequest("id-" + i, "rate@example.com", "Rate", "Limit");
            emailService.sendEmail(temp);
        }

       
        EmailRequest blockedRequest = new EmailRequest("id-6", "rate@example.com", "Rate", "Limit");
        Map<String, Object> response = emailService.sendEmail(blockedRequest);

        assertFalse((Boolean) response.get("success"));
        assertEquals("Rate limit exceeded", response.get("message"));
    }
	

}
