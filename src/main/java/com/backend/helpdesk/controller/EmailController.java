package com.backend.helpdesk.controller;

import com.backend.helpdesk.common.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RestController
@RequestMapping("/api/send-email")
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping
    public void sendEmail(@RequestBody Email email){
        try {
            for (String sendToEmail : email.getSendToEmail()) {
                MimeMessage mailMessage = mailSender.createMimeMessage();

                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, true);

                mimeMessageHelper.setTo(sendToEmail);
                mimeMessageHelper.setSubject(email.getSubject());
                mimeMessageHelper.setText(email.getText(), true);

                mailSender.send(mailMessage);
            }
        }catch (MessagingException ex) {
            System.out.println(ex);
        }
    }
}
