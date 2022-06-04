package com.example.emailManagementApp.services;


import com.example.emailManagementApp.dtos.request.ForwardMessageRequest;
import com.example.emailManagementApp.dtos.response.ForwardMessageResponse;
import com.example.emailManagementApp.dtos.request.MailBoxesMessageRequestDto;
import com.example.emailManagementApp.dtos.request.MessageRequest;
import com.example.emailManagementApp.dtos.request.UserRequest;
import com.example.emailManagementApp.dtos.response.SentMessageResponseDto;
import com.example.emailManagementApp.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.emailManagementApp.models.MailboxType.INBOX;
import static com.example.emailManagementApp.models.MailboxType.SENT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;
@SpringBootTest
class MessageServiceImplTest {
    @Autowired
    private MessageService messageService;

    @Autowired
    MailBoxesService mailBoxesService;

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp() {
    }
    @Test
    public void testThatNewUserReceivedWelcomeMessageImmediatelyTheyRegister(){

        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("newemail@gmail.com");
        userRequest.setPassword("password");
        mailBoxesService.createMailBoxes("newemail@gmail.com");
        userService.createUser("newemail@gmail.com", "password");

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setUserName("newemail@gmail.com");
        messageRequest.setReceiver("newemail@gmail.com");
        messageRequest.setSender("admin@gmail.com");
        messageRequest.setMessageBody("Welcome newUser to gmail.com, we are pls to have you");
        messageRequest.setDate( LocalDateTime.now());

        messageService.sendMessageToNewUser(messageRequest);

        MailBoxes mailBoxes = new MailBoxes();
        mailBoxes.setUserName("newemail@gmail.com");
        mailBoxes.setMailBox(mailBoxes.getMailBox());

        MailBox mailBox = new MailBox();
        mailBox.setMailboxType(INBOX);
        mailBox.setUserName("newemail@gmail.com");
        mailBoxes.getMailBox().add(mailBox);

        MailBoxesMessageRequestDto mailBoxesDto = new MailBoxesMessageRequestDto();
        mailBoxesDto.setUserName("useremail@gmail.com");
        mailBoxesDto.setMessageBody("Welcome newUser to gmail.com, we are pls to have you");
        mailBoxes.setMailBox(mailBoxes.getMailBox());

        Notification sendNotification = new Notification();
        sendNotification.setId("newemail@gmail.com");
        sendNotification.setSentMessage("Welcome newUser to gmail.com, we are pls to have you");
        sendNotification.setTitle("welcome new user");

        User user = new User();
        user.setLogInStatus(true);
        user.setNotificationlist(user.getNotificationlist());


      assertEquals(messageRequest.getReceiver(),"newemail@gmail.com");
      assertEquals(messageRequest.getMessageBody(),"Welcome newUser to gmail.com, we are pls to have you");
    }
    //to be worked on
    @Test
    public void testThatOneUserCanSendMessageToAnotherUser(){

        mailBoxesService.createMailBoxes("newemail@gmail.com");
        userService.createUser("newemail@gmail.com", "password");
        mailBoxesService.createMailBoxes("seconduser@gmail.com");
        userService.createUser("seconduser@gmail.com", "phantom45");

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setUserName("newemail@gmail.com");
        messageRequest.setReceiver("seconduser@gmail.com");
        messageRequest.setSender("newemail@gmail.com");
        messageRequest.setMessageBody("It was nice finally meeting you today, hope you will attend the conference");
        messageRequest.setMessageTitle("Invitation to attend conference");
        messageRequest.setDate(LocalDateTime.now());


        MailBoxes mailBoxes = new MailBoxes();
        mailBoxes.setUserName("newemail@gmail.com");
        mailBoxes.setMailBox(mailBoxes.getMailBox());


        MailBox mailBox = new MailBox();
        mailBox.setMailboxType(SENT);
        mailBox.setUserName("newemail@gmail.com");
        mailBoxes.getMailBox().add(mailBox);


        MailBox mailBoxUser2 = new MailBox();
        mailBoxUser2.setMailboxType(INBOX);
        mailBoxUser2.setUserName("seconduser@gmail.com");
        mailBoxes.getMailBox().add(mailBoxUser2);


     SentMessageResponseDto response= messageService.messageCanBeSendFromOneUserToAnotherUser(messageRequest);

        Notification sendNotification = new Notification();
        sendNotification.setId("seconduser@gmail.com");
        sendNotification.setSentMessage("It was nice finally meeting you today, hope you will attend the conference");
        sendNotification.setTitle("Invitation to attend conference");


        assertEquals(response.getSender(),"newemail@gmail.com");
        assertEquals(response.getMessageBody(),"It was nice finally meeting you today, hope you will attend the conference");




    }
    @Test
    public void testThatUserCanFindAMessageInsideListOfMessageInInBox(){


        mailBoxesService.createMailBoxes("newemail@gmail.com");
        userService.createUser("newemail@gmail.com", "password");
       mailBoxesService.createMailBoxes("seconduser@gmail.com");
        userService.createUser("seconduser@gmail.com", "phantom45");


        MailBox mailBoxUser2 = new MailBox();
        mailBoxUser2.setMailboxType(INBOX);
        mailBoxUser2.setUserName("seconduser@gmail.com");


        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setUserName("newemail@gmail.com");
        messageRequest.setReceiver("seconduser@gmail.com");
        messageRequest.setSender("newemail@gmail.com");
        messageRequest.setMessageBody("It was nice finally meeting you today, hope you will attend the conference");
        messageRequest.setMessageTitle("Invitation to attend conference");
        messageRequest.setDate(LocalDateTime.now());
        messageService.messageCanBeSendFromOneUserToAnotherUser(messageRequest);


        Message foundMessage = messageService.userCanFindAMessageInListOfMessageInsideInbox(messageRequest);

        assertEquals(foundMessage.getMessageTitle(),"Invitation to attend conference");
       assertEquals(foundMessage.getMessageBody(),"It was nice finally meeting you today, hope you will attend the conference");

    }
    @Test
    public void testThatUserCanFindAMessageInsideListOfMessageInOutBox(){
        User user = new User();
        user.setEmail("newemail@gmail.com");
        user.setPassword("password");
        user.setLogInStatus(true);
        mailBoxesService.createMailBoxes("newemail@gmail.com");
        userService.createUser("newemail@gmail.com", "password");

        User user2 = new User();
        user2.setEmail("seconduser@gmail.com");
        user2.setPassword("phantom45");
//        user2.setLogInStatus(true);
       mailBoxesService.createMailBoxes("seconduser@gmail.com");
        userService.createUser("seconduser@gmail.com", "phantom45");

        MailBoxes mailBoxes = new MailBoxes();
        mailBoxes.setMailBox(new ArrayList<>());
        mailBoxes.setUserName("newemail@gmail.com");



        MailBox mailBoxUser = new MailBox();
        mailBoxUser.setMailboxType(SENT);
        mailBoxUser.setUserName("newemail@gmail.com");
        mailBoxes.getMailBox().add(mailBoxUser);


        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setUserName("newemail@gmail.com");
        messageRequest.setReceiver("seconduser@gmail.com");
        messageRequest.setSender("newemail@gmail.com");
        messageRequest.setMessageBody("It was nice finally meeting you today, hope you will attend the conference");
        messageRequest.setMessageTitle("Invitation to attend conference");
        messageRequest.setDate(LocalDateTime.now());
        messageService.messageCanBeSendFromOneUserToAnotherUser(messageRequest);

        Message foundMessage= messageService.userCanFindAMessageInListOfMessageInsideInOutBox(messageRequest);

        assertEquals(foundMessage.getMessageTitle(),"Invitation to attend conference");
        assertEquals(foundMessage.getMessageBody(),"It was nice finally meeting you today, hope you will attend the conference");

    }
    @Test
    public void testThatReceivedMessageCanBeForwardedToAnotherUser(){
        User user = new User();
        user.setPassword("phantom45");
        user.setEmail("seconduser@gmail.com");
        user.setLogInStatus(true);
        mailBoxesService.createMailBoxes("seconduser@gmail.com");
        userService.createUser("seconduser@gmail.com", "phantom45");


        User user2 = new User();
        user2.setEmail("thirduser@gmail.com");
        user2.setPassword("house123");
        mailBoxesService.createMailBoxes("thirduser@gmail.com");
        userService.createUser("thirduser@gmail.com", "house123");

        MailBox mailBoxUser2 = new MailBox();
        mailBoxUser2.setMailboxType(INBOX);
        mailBoxUser2.setUserName("seconduser@gmail.com");


        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setUserName("newemail@gmail.com");
        messageRequest.setReceiver("seconduser@gmail.com");
        messageRequest.setSender("newemail@gmail.com");
        messageRequest.setMessageBody("It was nice finally meeting you today, hope you will attend the conference");
        messageRequest.setMessageTitle("Invitation to attend conference");
        messageRequest.setDate(LocalDateTime.now());
        messageService.messageCanBeSendFromOneUserToAnotherUser(messageRequest);

        ForwardMessageRequest forwardMessageRequest = new ForwardMessageRequest();
        forwardMessageRequest.setUserForwardMessage("seconduser@gmail.com");
        forwardMessageRequest.setReceiverForwardMessage("thirduser@gmail.com");
        forwardMessageRequest.setForwardMessageBody(String.valueOf(messageRequest));

        List<User> foundUser = messageService.receivedMessageCanBeForwardedToAnotherUser(messageRequest);


    }



}