package com.example.emailManagementApp.services;

import com.example.emailManagementApp.dtos.request.MessageRequest;

import com.example.emailManagementApp.dtos.request.UserRequest;
import com.example.emailManagementApp.dtos.response.NotificationResponseDto;
import com.example.emailManagementApp.models.Notification;
import com.example.emailManagementApp.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class NotificationServiceImplTest {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MailBoxesService mailBoxesService;

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @Test
    public void testThatNotificationsCanBeCreated(){
        //given that we have a user
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("newemail@gmail.com");
        userRequest.setPassword("password");
        mailBoxesService.createMailBoxes("newemail@gmail.com");
        userService.createUser("newemail@gmail.com", "password");
        //and we have a sender
        //when notification message is sent
        //assert that it was send

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setUserName("newemail@gmail.com");
        messageRequest.setReceiver("newemail@gmail.com");
        messageRequest.setSender("admin@gmail.com");
        messageRequest.setMessageBody("Welcome newUser to gmail.com, we are pls to have you");
        messageRequest.setDate( LocalDateTime.now());
        messageService.sendMessageToNewUser(messageRequest);
        Notification sendNotification =  Notification.builder()
                .sender(messageRequest.getSender())
                .id(messageRequest.getUserName())
                .sentMessage(messageRequest.getMessageBody())
                .receiver(messageRequest.getUserName())
                .title("new message alert")
                .build();

        NotificationResponseDto response = notificationService.createNotification(sendNotification,messageRequest);

        assertEquals(response.getReceiver(),"newemail@gmail.com");
    }
}