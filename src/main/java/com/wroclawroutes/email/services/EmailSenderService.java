package com.wroclawroutes.email.services;

public interface EmailSenderService {
    void send(String to, String email);
}
