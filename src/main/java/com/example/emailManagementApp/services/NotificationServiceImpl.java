package com.example.emailManagementApp.services;

import com.example.emailManagementApp.dtos.request.MessageRequest;

import com.example.emailManagementApp.dtos.response.NotificationResponseDto;
import com.example.emailManagementApp.exceptions.UserDoesNotExistException;
import com.example.emailManagementApp.models.Message;
import com.example.emailManagementApp.models.Notification;
import com.example.emailManagementApp.models.User;
import com.example.emailManagementApp.repositories.NotificationRepository;
import com.example.emailManagementApp.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

private ModelMapper modelmapper=new ModelMapper();



    @Override
    public NotificationResponseDto createNotification(Notification sendNotification, MessageRequest messageRequest) {
        User recipient = userRepository.findUserByEmail(sendNotification.getReceiver()).orElseThrow(()->new UserDoesNotExistException("user don't exist"));


        Message sendMessage = new Message();
        sendMessage.setMessageBody(messageRequest.getMessageBody());
        sendMessage.setReceiver(messageRequest.getReceiver());
        sendMessage.setSender(messageRequest.getSender());
        sendMessage.setUserName(messageRequest.getUserName());
        sendMessage.setDate(messageRequest.getDate());

        Notification notification = new Notification();
        notification.setTitle(sendNotification.getTitle());
        notification.setSentMessage(sendNotification.getSentMessage());
        notification.setReadMessage(sendNotification.isReadMessage());
        notification.setReceiver(sendNotification.getReceiver());

      notificationRepository.save(notification);

      recipient.getNotificationlist().add(notification);

//      NotificationResponseDto notificationResponseDto = new NotificationResponseDto();





        return modelmapper.map(recipient,NotificationResponseDto.class) ;
    }
}
