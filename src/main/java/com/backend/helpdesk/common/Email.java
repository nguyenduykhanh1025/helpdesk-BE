package com.backend.helpdesk.common;

import lombok.Data;

import java.util.List;

@Data
public class Email {

    private List<String> sendToEmail;

    private String subject;

    private String text;
}
