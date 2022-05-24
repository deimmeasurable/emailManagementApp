package com.example.emailManagementApp.services;

import com.example.emailManagementApp.dtos.request.MailBoxesRequest;
import com.example.emailManagementApp.dtos.response.MailBoxesDto;
import com.example.emailManagementApp.models.MailBox;

import com.example.emailManagementApp.models.MailboxType;

import com.example.emailManagementApp.repositories.MailBoxesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class MailBoxesServiceImplTest {
@Autowired
    MailBoxesService mailBoxesService;

@Autowired
    MailBoxesRepository MailBoxesRepository;


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

}