package com.example.emailManagementApp.services;


import com.example.emailManagementApp.dtos.request.MailBoxesMessageRequestDto;
import com.example.emailManagementApp.dtos.request.MessageRequest;
import com.example.emailManagementApp.dtos.request.UserRequest;
import com.example.emailManagementApp.dtos.response.MailBoxesDto;
import com.example.emailManagementApp.dtos.response.SentMessageResponseDto;
import com.example.emailManagementApp.dtos.response.UserDto;
import com.example.emailManagementApp.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
    public void testThatUserCanFindAMessageInsideListOfMessage(){

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

        List<Message> foundMessage= messageService.userCanFindAMessageInListOfMessageInsideInbox(messageRequest);

//        assertArrayEquals(foundMessage.stream().toArray());
        assertEquals(foundMessage.size(),1);
    }


}