package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Mail {
    private String mailTo;
    private String toCC = "";
    private String subject;
    private String message;
    private Type type;


    public Mail(Type type, String mailTo, String subject, String message) {
        this.type = type;
        this.mailTo = mailTo;
        this.subject = subject;
        this.message = message;
    }

    public Mail(String mailTo, String subject, String message) {
        this.mailTo = mailTo;
        this.subject = subject;
        this.message = message;
    }

    public enum Type {
        TRELLO_CARD_EMAIL,
        DAILY_INFORMATION_EMAIL
    }}
