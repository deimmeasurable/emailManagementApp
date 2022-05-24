package com.example.emailManagementApp.services;

import com.example.emailManagementApp.dtos.request.MessageRequest;
import com.example.emailManagementApp.dtos.response.CreateNewUserMessageDto;
import com.example.emailManagementApp.exceptions.UserDoesNotExistException;
import com.example.emailManagementApp.models.Message;
import com.example.emailManagementApp.models.Notification;
import com.example.emailManagementApp.models.User;
import com.example.emailManagementApp.repositories.MessageRepository;
import com.example.emailManagementApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl  implements MessageService{
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;
    @Override
    public Message sendMessageToNewUser(CreateNewUserMessageDto messageRequest) {
        Message message = Message.builder()
                .sender(messageRequest.getSender())
                .receiver(messageRequest.getReceiver())
                .messageBody(messageRequest.getMessageBody())
                 Message.builder()
                .sender(messageRequest.getSender())
                .receiver(messageRequest.getReceiver())
                .messageBody(messageRequest.getMessageBody())
                .date(messageRequest.getDate())
                .build();
        messageRepository.save(message);
User  recipient = userRepository.findUserByEmail(messageRequest.getReceiver()).orElseThrow (()->new UserDoesNotExistException("user is found"));
        Notification sendNotification =  Notification.builder()
                        .sender(messageRequest.getSender())
                                .id(message.getUserName())
                .sentMessage(messageRequest.getMessageBody())
                                        .title("new message alert")
                                                .build();
                recipient.getNotificationlist().add(sendNotification);




        userRepository.save(recipient);

        return message;
    }
}
