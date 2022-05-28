package com.example.emailManagementApp.services;

import com.example.emailManagementApp.dtos.request.MessageRequest;
import com.example.emailManagementApp.dtos.response.NotificationRespnseDto;
import com.example.emailManagementApp.models.Notification;
import com.example.emailManagementApp.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificationServiceImplTest {
    @Autowired
    private NotificationService notificationService;


    @Autowired
    private MessageService messageService;

    @Test
    public void testThatNotificationsCanBeCreated(){
        //given that we have a user

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
                .title("new message alert")
                .build();

        NotificationRespnseDto response = notificationService.createNotification(sendNotification,messageRequest);

//        recipient.getNotificationlist().add(sendNotification);
    }
}