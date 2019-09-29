package com.backend.helpdesk.controller;

import com.backend.helpdesk.common.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/send-email")
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping
    public void sendEmail(@RequestBody Email email){
        for(String sendToEmail : email.getSendToEmail()) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setTo(sendToEmail);
            mailMessage.setSubject(email.getSubject());
            mailMessage.setText(email.getText());

            mailSender.send(mailMessage);
        }
    }
}
