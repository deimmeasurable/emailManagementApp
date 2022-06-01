package com.example.emailManagementApp.services;

import com.example.emailManagementApp.dtos.request.MailBoxesRequest;
import com.example.emailManagementApp.dtos.request.MessageRequest;
import com.example.emailManagementApp.dtos.response.MailBoxesDto;
import com.example.emailManagementApp.models.MailBox;

import com.example.emailManagementApp.models.MailboxType;

import com.example.emailManagementApp.repositories.MailBoxesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.emailManagementApp.models.MailboxType.INBOX;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class MailBoxesServiceImplTest {
@Autowired
    MailBoxesService mailBoxesService;

@Autowired
    MailBoxesRepository MailBoxesRepository;

@Autowired
private UserService userService;

@Autowired
private MessageService messageService;


@Test
public void testThatMailBoxesCanBeCreated(){

    MailBoxesRequest mailBoxes = new MailBoxesRequest();
    mailBoxes.setUserName("useremail@gmail.com");
    mailBoxes.setMailBox(new ArrayList<>());

    MailBox received = new MailBox();
    received.setUserName("useremail@gmail.com");
    received.setMailboxType(MailboxType.INBOX);
    mailBoxes.getMailBox().add(received);

    MailBox sendMessage = new MailBox();
    sendMessage.setUserName("useremail@gmail.com");
    sendMessage.setMailboxType(MailboxType.SENT);

     mailBoxesService.createMailBoxes("useremail@gmail.com");

    MailBoxesDto mailBoxesDto = new MailBoxesDto();
    mailBoxesDto.setUserName("useremail@gmail.com");
     mailBoxesDto.setMessage("user mailboxes created");

    assertThat(mailBoxesDto.getUserName()).isEqualTo("useremail@gmail.com");
    assertThat(mailBoxesDto.getMessage()).isEqualTo("user mailboxes created");


}
    @Test
    public void testThatUserCanViewInbox(){
        mailBoxesService.createMailBoxes("seconduser@gmail.com");
        userService.createUser("seconduser@gmail.com", "phantom45");


        MailBox mailBoxUser = new MailBox();
        mailBoxUser.setMailboxType(INBOX);
        mailBoxUser.setUserName("seconduser@gmail.com");


        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setUserName("newemail@gmail.com");
        messageRequest.setReceiver("seconduser@gmail.com");
        messageRequest.setSender("newemail@gmail.com");
        messageRequest.setMessageBody("It was nice finally meeting you today, hope you will attend the conference");
        messageRequest.setMessageTitle("Invitation to attend conference");
        messageRequest.setDate(LocalDateTime.now());
       messageService.messageCanBeSendFromOneUserToAnotherUser(messageRequest);

       List<MailBox> mailBox= mailBoxesService.userCanViewAllInbox(messageRequest);
    }

}