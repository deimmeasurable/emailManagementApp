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
    public NotificationResponseDto createNotification( MessageRequest messageRequest) {
        User recipient = userRepository.findUserByEmail(messageRequest.getReceiver()).orElseThrow(()->new UserDoesNotExistException("user don't exist"));


        Message sendMessage = new Message();
        sendMessage.setMessageBody(messageRequest.getMessageBody());
        sendMessage.setReceiver(messageRequest.getReceiver());
        sendMessage.setSender(messageRequest.getSender());
        sendMessage.setUserName(messageRequest.getUserName());
        sendMessage.setDate(messageRequest.getDate());

        Notification notification = new Notification();
        notification.setTitle(messageRequest.getMessageTitle());
        notification.setSentMessage(messageRequest.getMessageBody());
        notification.setReadMessage(notification.isReadMessage());
        notification.setReceiver(messageRequest.getReceiver());

      notificationRepository.save(notification);

      recipient.getNotificationlist().add(notification);

      NotificationResponseDto notificationResponseDto = new NotificationResponseDto();
      notificationResponseDto.setReadMessage(notificationResponseDto.isReadMessage());
      notificationResponseDto.setReceiver(messageRequest.getReceiver());
      notificationResponseDto.setTitle(messageRequest.getMessageTitle());
      notificationResponseDto.setSender(messageRequest.getSender());
      notificationResponseDto.setSentMessage(messageRequest.getMessageBody());





        return notificationResponseDto;
    }
}
