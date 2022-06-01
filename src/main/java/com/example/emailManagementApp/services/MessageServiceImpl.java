package com.example.emailManagementApp.services;


import com.example.emailManagementApp.dtos.request.MessageRequest;

import com.example.emailManagementApp.dtos.response.SentMessageResponseDto;
import com.example.emailManagementApp.exceptions.MessageNotFoundException;
import com.example.emailManagementApp.exceptions.UserDidNotLogInException;
import com.example.emailManagementApp.exceptions.UserDoesNotExistException;
import com.example.emailManagementApp.models.*;
import com.example.emailManagementApp.repositories.MailBoxRepository;
import com.example.emailManagementApp.repositories.MessageRepository;
import com.example.emailManagementApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MessageServiceImpl  implements MessageService {
    //    @Autowired
    private final MessageRepository messageRepository;

    //    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailBoxRepository mailBoxRepository;

    //   @Autowired
    private MailBoxesService mailBoxesService;


    public MessageServiceImpl(UserRepository userRepository, MessageRepository messageRepository, MailBoxesService mailBoxesService) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.mailBoxesService = mailBoxesService;
    }


    @Override
    public Message sendMessageToNewUser(MessageRequest messageRequest) {

        User recipient = userRepository.findUserByEmail(messageRequest.getUserName()).orElseThrow(() -> new UserDoesNotExistException("user is not found"));

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


        Notification sendNotification = Notification.builder()
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

    public SentMessageResponseDto messageCanBeSendFromOneUserToAnotherUser(MessageRequest messageRequest) {
        User senderUser = userRepository.findUserByEmail(messageRequest.getUserName()).orElseThrow(() -> new UserDoesNotExistException("user is not found"));
        User receiverUser = userRepository.findUserByEmail(messageRequest.getReceiver()).orElseThrow(() -> new UserDoesNotExistException("user is not found"));

        if (!senderUser.isLogInStatus()) {
            senderUser.setLogInStatus(true);

            Message message = new Message();
            message.setMessageBody(messageRequest.getMessageBody());
            message.setSender(messageRequest.getSender());
            message.setUserName(messageRequest.getUserName());
            message.setDate(messageRequest.getDate());
            message.setReceiver(messageRequest.getReceiver());
            message.setMessageTitle(messageRequest.getMessageTitle());


            MailBoxes mailBoxes = new MailBoxes();
            mailBoxes.setUserName(messageRequest.getUserName());
            mailBoxes.setMailBox(mailBoxes.getMailBox());


            MailBox mailBoxSender = new MailBox();
            mailBoxSender.setMailboxType(MailboxType.SENT);
            mailBoxSender.setUserName(messageRequest.getUserName());
            mailBoxSender.getMessage().add(message);
            mailBoxes.getMailBox().add(mailBoxSender);

            System.out.println(receiverUser);
            System.out.println(mailBoxes);


            MailBox mailBoxReceiver = new MailBox();
            mailBoxReceiver.setMailboxType(MailboxType.INBOX);
            mailBoxReceiver.setUserName(messageRequest.getReceiver());
            mailBoxReceiver.getMessage().add(message);
            mailBoxes.getMailBox().add(mailBoxReceiver);

            System.out.println(mailBoxReceiver);
            System.out.println(mailBoxSender);
//            receiverUser.getNotificationlist().add(sendNotification);
            System.out.println(senderUser);

            Notification sendNotification = new Notification();
            sendNotification.setId(messageRequest.getReceiver());
            sendNotification.setSentMessage(messageRequest.getMessageBody());
            sendNotification.setTitle(messageRequest.getMessageTitle());
            sendNotification.setReadMessage(sendNotification.isReadMessage());
            receiverUser.getNotificationlist().add(sendNotification);

            SentMessageResponseDto sentMessageResponseDto = new SentMessageResponseDto();
            sentMessageResponseDto.setDate(messageRequest.getDate());
            sentMessageResponseDto.setSender(messageRequest.getSender());
            sentMessageResponseDto.setMessageBody(messageRequest.getMessageBody());

            return sentMessageResponseDto;

        }
        throw new UserDidNotLogInException("user didn't login");
    }

    @Override
    public Message userCanFindAMessageInListOfMessageInsideInbox(MessageRequest messageRequest) {
        User foundUser = userRepository.findUserByEmail(messageRequest.getUserName()).orElseThrow(() -> new UserDoesNotExistException("user don't exist"));


        if (!foundUser.isLogInStatus()) {
           foundUser.setLogInStatus(true);

            MailBoxes mailBoxes = mailBoxesService.findMailBoxesByUserName(foundUser.getEmail());
            MailBox box = null;
            for (MailBox mailBox : mailBoxes.getMailBox()) {
                if (mailBox.getMailboxType().equals(MailboxType.INBOX)) {
                    box = mailBox;
                }
            }

            for (Message msg : Objects.requireNonNull(box).getMessage()) {
                if (msg.getMessageBody().equals(messageRequest.getMessageBody()) || msg.getMessageTitle().equals(messageRequest.getMessageTitle())) {
                    return msg;


                }
            }
        }
        throw new UserDidNotLogInException("user didn't login");
    }

    @Override
    public Message userCanFindAMessageInListOfMessageInsideInOutBox(MessageRequest messageRequest) {
        User foundUser = userRepository.findUserByEmail(messageRequest.getSender()).orElseThrow(() -> new UserDoesNotExistException("user don't exist"));
//        MailBoxes mailBoxes = mailBoxesService.findMailBoxesByUserName(foundUser.getEmail());

        if (!foundUser.isLogInStatus()) {
            foundUser.setLogInStatus(true);

           MailBoxes mailBoxes = mailBoxesService.findMailBoxesByUserName(foundUser.getEmail());
            MailBox box = null;
            for (MailBox mailBox : mailBoxes.getMailBox()) {
                if (mailBox.getMailboxType().equals(MailboxType.SENT)) {
                    box = mailBox;
                }
            }

            for (Message msg : Objects.requireNonNull(box).getMessage()) {
                if (msg.getMessageBody().equals(messageRequest.getMessageBody()) || msg.getMessageTitle().equals(messageRequest.getMessageTitle())) {
                    return msg;


                }
            }
        }
        throw new UserDidNotLogInException("user didn't login");

    }
}