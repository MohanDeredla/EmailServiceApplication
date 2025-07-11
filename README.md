📧 Email Service Application

A robust Spring Boot-based Email Sending Service that supports:



✅ Multiple email providers (with fallback and circuit breaker)



✅ Retry with exponential backoff



✅ Idempotency (prevent duplicate emails)



✅ Rate limiting (5 emails per minute per recipient)



✅ Email logs stored in the database



🚀 Features

Send email via multiple providers (plug-and-play support)



Email logging with success/failure tracking



Rate Limiting: Prevent spamming (5 emails/min per recipient)



Idempotency: Prevents reprocessing duplicate requests



Retry mechanism: Retries failed providers with exponential backoff



Circuit breaker: Skips failed providers after repeated failures



🛠️ Tech Stack

Java 17



Spring Boot 3.x



Spring Data JPA



H2 / MySQL / PostgreSQL (pluggable DB)



Maven



Jakarta Persistence (JPA)



📦 Project Structure

bash

Copy

Edit

src/main/java/com/example/EmailServiceApplication

├── Controller/

│   └── EmailController.java       # REST endpoint

├── Models/

│   └── EmailRequest.java          # Email input DTO

│   └── EmailLog.java              # Entity for email logs

├── Provider/

│   └── EmailProvider.java         # Email provider interface

├── Repository/

│   └── EmailLogRepository.java    # JPA Repository

├── Service/

│   └── EmailService.java          # Main business logic

🧪 API Endpoint

POST /sendEmail

Description: Send an email using available providers.



📥 Request Body

json

Copy

Edit

{

&nbsp; "id": "unique-id-123",

&nbsp; "to": "recipient@example.com",

&nbsp; "subject": "Test Email",

&nbsp; "body": "This is a test email."

}

📤 Response (Success)

json

Copy

Edit

{

&nbsp; "provider": "GmailProvider",

&nbsp; "success": true,

&nbsp; "message": "Email sent successfully"

}

📤 Response (Duplicate Request)

json

Copy

Edit

{

&nbsp; "message": "Duplicate request ignored (idempotent)",

&nbsp; "success": false

}

📤 Response (Rate Limit Exceeded)

json

Copy

Edit

{

&nbsp; "message": "Rate limit exceeded",

&nbsp; "success": false

}

📤 Response (All Providers Failed)

json

Copy

Edit

{

&nbsp; "message": "All providers failed",

&nbsp; "success": false

}

📚 Database Table - EmailLog

Field	Type	Description

id	String	Unique identifier

toAddress	String	Email recipient

subject	String	Subject of the email

body	String	Email content

provider	String	Provider used

success	boolean	Email sent successfully or not

message	String	Success/failure message

sentAt	LocalDateTime	Timestamp of the send operation



🏃 Getting Started

✅ Prerequisites

Java 17+



Maven



IDE (IntelliJ / Eclipse / VSCode)



▶️ Run the App

bash

Copy

Edit

mvn spring-boot:run

The application will start at:

http://localhost:8080/sendEmail



🔌 Add New Email Provider

Create a class that implements EmailProvider interface.



Implement the sendEmail(EmailRequest request) method.



Annotate it with @Component so it's auto-discovered by Spring.

