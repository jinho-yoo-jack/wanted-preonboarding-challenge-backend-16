package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.layered.application.cancel.v1.mail.EmailSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailSenderTest {
    @Autowired
    private EmailSender emailSender;

    @Test
    public void emailSendTests() {
        // Create an instance of JavaMailSender
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // Create an instance of EmailSender
//        EmailSender emailSender = new EmailSender(mailSender);
        // Call the sendEmail method to send an email
        String recipientEmail = "jhy7342@gmail.com";
        String subject = "Hello from Spring Boot";
        String content = "<p>Hello,</p><p>This is a test email sent from Spring Boot.</p>";

        try {
            emailSender.sendEmail(recipientEmail, subject, content);
            System.out.println("Email sent successfully.");
        } catch (Exception e) {
            System.out.println("Failed to send email. Error: " + e.getMessage());
        }
    }
}
