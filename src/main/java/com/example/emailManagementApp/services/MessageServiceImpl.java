package com.example.emailManagementApp.services;


import com.example.emailManagementApp.dtos.request.MessageRequest;
import com.example.emailManagementApp.dtos.response.CreateNewUserMessageDto;
import com.example.emailManagementApp.exceptions.UserDoesNotExistException;
import com.example.emailManagementApp.models.*;
import com.example.emailManagementApp.repositories.MessageRepository;
import com.example.emailManagementApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl  implements MessageService{
//    @Autowired
    private final MessageRepository messageRepository;

//    @Autowired
    UserRepository userRepository;

    public MessageServiceImpl(UserRepository userRepository, MessageRepository messageRepository){
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }


    @Override
    public Message sendMessageToNewUser(MessageRequest messageRequest) {

        User  recipient = userRepository.findUserByEmail(messageRequest.getUserName()).orElseThrow (()->new UserDoesNotExistException("user is not found"));

        Message message = Message.builder()
                .sender(messageRequest.getSender())
                .receiver(messageRequest.getReceiver())
                .messageBody(messageRequest.getMessageBody())
                .date(messageRequest.getDate())
                .build();
        messageRepository.save(message);

        MailBoxes mailBoxes = new MailBoxes();
        mailBoxes.setUserName(message.getUserName());
        mailBoxes.setMailBox(mailBoxes.getMailBox());

        MailBox mailBox = new MailBox();
        mailBox.setMailboxType(MailboxType.INBOX);
        mailBox.setUserName(messageRequest.getUserName());
        mailBox.getMessage().add(message);

        mailBoxes.getMailBox().add(mailBox);




        Notification sendNotification =  Notification.builder()
                        .sender(messageRequest.getSender())
                                .id(message.getUserName())
                .sentMessage(messageRequest.getMessageBody())
                                        .title("new message alert")
                                                .build();
                recipient.getNotificationlist().add(sendNotification);




        userRepository.save(recipient);
        System.out.println(mailBoxes);
        System.out.println(recipient);

        return message;
    }
}
