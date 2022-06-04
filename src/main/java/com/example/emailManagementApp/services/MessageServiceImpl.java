package com.example.emailManagementApp.services;


import com.example.emailManagementApp.dtos.request.MessageRequest;

import com.example.emailManagementApp.dtos.response.SentMessageResponseDto;
import com.example.emailManagementApp.exceptions.MessageNotFoundException;
import com.example.emailManagementApp.exceptions.UserDidNotLogInException;
import com.example.emailManagementApp.exceptions.UserDoesNotExistException;
import com.example.emailManagementApp.models.*;
import com.example.emailManagementApp.repositories.MailBoxRepository;
import com.example.emailManagementApp.repositories.MailBoxesRepository;
import com.example.emailManagementApp.repositories.MessageRepository;
import com.example.emailManagementApp.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Slf4j
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

    @Autowired
    private MailBoxesRepository mailBoxesRepository;


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
            messageRepository.save(message);


            MailBoxes mailBoxes = new MailBoxes();
            mailBoxes.setUserName(messageRequest.getUserName());
            mailBoxes.setMailBox(new ArrayList<>());


            MailBoxes mailBoxes2 = new MailBoxes();
            mailBoxes2.setUserName(messageRequest.getReceiver());
            mailBoxes2.setMailBox(new ArrayList<>());


            SentMessageResponseDto sentMessageResponseDto = getSentMessageResponseDto(messageRequest, receiverUser, message, mailBoxes, mailBoxes2);

            return sentMessageResponseDto;

        }
        throw new UserDidNotLogInException("user didn't login");
    }

    private SentMessageResponseDto getSentMessageResponseDto(MessageRequest messageRequest, User receiverUser, Message message, MailBoxes mailBoxes, MailBoxes mailBoxes2) {
        MailBox mailBoxSender = new MailBox();
        mailBoxSender.setMailboxType(MailboxType.SENT);
        mailBoxSender.setUserName(messageRequest.getUserName());
        mailBoxSender.getMessage().add(message);
        mailBoxes.getMailBox().add(mailBoxSender);
        mailBoxRepository.save(mailBoxSender);
        mailBoxesRepository.save(mailBoxes);


        MailBox mailBoxReceiver = new MailBox();
        mailBoxReceiver.setMailboxType(MailboxType.INBOX);
        mailBoxReceiver.setUserName(messageRequest.getReceiver());
        mailBoxReceiver.getMessage().add(message);
        mailBoxes2.getMailBox().add(mailBoxReceiver);
        mailBoxRepository.save(mailBoxReceiver);
        mailBoxesRepository.save(mailBoxes2);



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

    @Override
    public Message userCanFindAMessageInListOfMessageInsideInbox(MessageRequest messageRequest) {
        User foundUser = userRepository.findUserByEmail(messageRequest.getReceiver()).orElseThrow(() -> new UserDoesNotExistException("user don't exist"));


        if (!foundUser.isLogInStatus()) {
            foundUser.setLogInStatus(true);

            MailBoxes mailBoxes = mailBoxesService.findMailBoxesByUserName(foundUser.getEmail());
            log.info("mailboxes-->{}", mailBoxes);

            MailBox mail = null;
            for (MailBox foundMailBox : mailBoxes.getMailBox()) {
                if (foundMailBox.getMailboxType().equals(MailboxType.INBOX)) {
                    mail = foundMailBox;
                    log.info("check mail box",mail);
                }
            }

            for (Message msg : mail.getMessage()) {
//                messageRepository.findMessageBySender(messageRequest.getSender());
                log.info("check the repository-->", messageRepository);
                if (msg.getMessageBody().equals(messageRequest.getMessageBody()) || msg.getMessageTitle().equals(messageRequest.getMessageTitle())) {
                    log.info("Message-->{}", msg);
                    return msg;
                }
            }
        }
            throw new UserDidNotLogInException("user didn't login");

    }

    @Override
    public Message userCanFindAMessageInListOfMessageInsideInOutBox(MessageRequest messageRequest) {
        User foundUser = userRepository.findUserByEmail(messageRequest.getUserName()).orElseThrow(() -> new UserDoesNotExistException("user don't exist"));
        if (foundUser.isLogInStatus()) {
           foundUser.setLogInStatus(true);
        }
        log.info("i was here---->");
        MailBoxes mailBoxes = mailBoxesService.findMailBoxesByUserName(foundUser.getEmail());
        MailBox box = null;
        for (MailBox mailBox : mailBoxes.getMailBox()) {
            if (mailBox.getMailboxType().equals(MailboxType.SENT)) {
                box = mailBox;
                log.info("check the mailbox--->",box.getMessage());
            }
        }

        for (Message msg : box.getMessage()) {
            log.info("box-->",box);
            if (msg.getMessageBody().equals(messageRequest.getMessageBody()) || msg.getMessageTitle().equals(messageRequest.getMessageTitle())) {

                return msg;
            }
        }
        throw new UserDidNotLogInException("message not found");

    }

    @Override
    public List<User> receivedMessageCanBeForwardedToAnotherUser(MessageRequest messageRequest) {
        User foundUser = userRepository.findUserByEmail(messageRequest.getReceiver()).orElseThrow(() -> new UserDoesNotExistException("user is found"));

        if(foundUser.isLogInStatus()){
            foundUser.setLogInStatus(true);
        }

        Message message = new Message();
        message.setMessageBody(messageRequest.getMessageBody());
        message.setSender(messageRequest.getSender());
        message.setUserName(messageRequest.getUserName());
        message.setDate(messageRequest.getDate());
        message.setReceiver(messageRequest.getReceiver());
        message.setMessageTitle(messageRequest.getMessageTitle());
        messageRepository.save(message);


        MailBox mailBox = new MailBox();
        mailBox.setMailboxType(MailboxType.INBOX);
        mailBox.setMessage(new ArrayList<>());
        mailBox.getMessage().add(message);
        mailBoxRepository.save(mailBox);



        
        return null;
    }
}