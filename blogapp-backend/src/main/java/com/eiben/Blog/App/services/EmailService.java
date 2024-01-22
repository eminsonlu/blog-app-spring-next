package com.eiben.Blog.App.services;

import com.eiben.Blog.App.entities.User;
import com.eiben.Blog.App.entities.UserVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final UserVerificationService userVerificationService;

    private final JavaMailSender mailSender;


    public void sendHtmlMail(User user, String token) throws Exception {
        UserVerification userVerification = userVerificationService.findByUser(user);
        // check user token
        if (userVerification == null) {
            return;
        }
        // send email
        String to = user.getEmail();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@eibenblogapp.com");
        message.setTo(to);
        message.setSubject("Verify your email");
        message.setText("Click on the link below to verify your email \n" +
                        "http://localhost:8080/auth/activation?token=" + token);
        mailSender.send(message);
    }
}
