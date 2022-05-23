package com.example.emailManagementApp.services;

import com.example.emailManagementApp.dtos.request.MessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class MessageServiceImplTest {
    @Autowired
    private MessageService MessageService;

    @BeforeEach
    void setUp() {
    }
    @Test
    public void testThatNewUserReceivedWelcomeMessageImmediatelyTheyRegister(){
        MessageRequest messageRequest = new MessageRequest();

    }
}