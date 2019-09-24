package com.backend.helpdesk.entityDTO;

import lombok.Data;

@Data
public class Email {
    private String sendToEmail;
    private String subject;
    private String content;
}
