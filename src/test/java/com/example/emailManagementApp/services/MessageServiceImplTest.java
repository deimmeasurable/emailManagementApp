package com.example.emailManagementApp.services;


import com.example.emailManagementApp.dtos.request.MailBoxesMessageRequestDto;
import com.example.emailManagementApp.dtos.request.MessageRequest;
import com.example.emailManagementApp.dtos.response.MailBoxesDto;
import com.example.emailManagementApp.models.MailBox;
import com.example.emailManagementApp.models.MailBoxes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.emailManagementApp.models.MailboxType.INBOX;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MessageServiceImplTest {
    @Autowired
    private MessageService messageService;

    @BeforeEach
    void setUp() {
    }
    @Test
    public void testThatNewUserReceivedWelcomeMessageImmediatelyTheyRegister(){
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setUserName("newemail@gmail.com");
        messageRequest.setReceiver("newemail@gmail.com");
        messageRequest.setMessageBody("Welcome newUser to gmail.com, we are pls to have you");
        messageRequest.setDate( LocalDateTime.now());

//        messageService.sendMessageToNewUser(messageRequest);
        MailBoxes mailBoxes = new MailBoxes();
        mailBoxes.setUserName("newemail@gmail.com");
        mailBoxes.setMailBox(new ArrayList<>());

        MailBox mailBox = new MailBox();
        mailBox.setMailboxType(INBOX);
        mailBox.setUserName("newemail@gmail.com");
        mailBoxes.getMailBox().add(mailBox);

        MailBoxesMessageRequestDto mailBoxesDto = new MailBoxesMessageRequestDto();
        mailBoxesDto.setUserName("useremail@gmail.com");
        mailBoxesDto.setMessageBody("Welcome newUser to gmail.com, we are pls to have you");
        mailBoxes.setMailBox((List<MailBox>) mailBox);

//        assertThat()
    }
}