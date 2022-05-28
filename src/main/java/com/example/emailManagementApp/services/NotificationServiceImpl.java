package com.example.emailManagementApp.services;

import com.example.emailManagementApp.dtos.request.MessageRequest;
import com.example.emailManagementApp.dtos.response.NotificationRespnseDto;
import com.example.emailManagementApp.exceptions.UserDoesNotExistException;
import com.example.emailManagementApp.models.Message;
import com.example.emailManagementApp.models.Notification;
import com.example.emailManagementApp.models.User;
import com.example.emailManagementApp.repositories.NotificationRepository;
import com.example.emailManagementApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;





    @Override
    public NotificationRespnseDto createNotification(Notification sendNotification, MessageRequest messageRequest) {
        User recipient = userRepository.findUserByEmail(sendNotification.getId()).orElseThrow(()->new UserDoesNotExistException("user don't exist"));


        Message sendMessage = new Message();
        sendMessage.setMessageBody(messageRequest.getMessageBody());
        sendMessage.setReceiver(messageRequest.getReceiver());
        sendMessage.setSender(messageRequest.getSender());
        sendMessage.setUserName(messageRequest.getUserName());
        sendMessage.setDate(messageRequest.getDate());

//        notificationRepository.save()
        return null;
    }
}
