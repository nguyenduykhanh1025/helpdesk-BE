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
@RequestMapping("/send-email")
public class EmailController {

    @Autowired
    public JavaMailSender mailSender;

    @PostMapping
    public void sendEmail(@RequestBody Email email){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(email.getSendToEmail());
        mailMessage.setSubject(email.getSubject());
        mailMessage.setText(email.getText());

        mailSender.send(mailMessage);
    }
}
