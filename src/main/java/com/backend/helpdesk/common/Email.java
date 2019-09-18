package com.backend.helpdesk.common;

import lombok.Data;

@Data
public class Email {

    private String sendToEmail;

    private String subject;

    private String text;
}
