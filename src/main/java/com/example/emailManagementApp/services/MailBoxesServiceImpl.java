package com.example.emailManagementApp.services;


import com.example.emailManagementApp.dtos.request.MessageRequest;
import com.example.emailManagementApp.dtos.response.CreateNewUserMessageDto;
import com.example.emailManagementApp.dtos.response.MailBoxesDto;
import com.example.emailManagementApp.models.MailBox;
import com.example.emailManagementApp.models.MailBoxes;
import com.example.emailManagementApp.models.Message;
import com.example.emailManagementApp.repositories.MailBoxesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.emailManagementApp.models.MailboxType.INBOX;
import static com.example.emailManagementApp.models.MailboxType.SENT;

@Service
public class MailBoxesServiceImpl implements MailBoxesService{
    @Autowired
    MailBoxesRepository mailBoxesRepository;

    private ModelMapper mapper;

    @Autowired
    MessageService messageService;

    @Override
    public MailBoxes createMailBoxes(String email)  {
        MailBoxes newMailBoxes = new MailBoxes();
        newMailBoxes.setMailBox(new ArrayList<>());
        newMailBoxes.setUserName(email);

        MailBox receiveMailBox= new MailBox();
        receiveMailBox.setUserName(email);
        receiveMailBox.setMailboxType(INBOX);


        newMailBoxes.getMailBox().add(receiveMailBox);

        MailBox sentMailBox = new MailBox();
        sentMailBox.setMailboxType(SENT);
        sentMailBox.setUserName(email);
        newMailBoxes.getMailBox().add(sentMailBox);

        mailBoxesRepository.save(newMailBoxes);

        MailBoxesDto mailBoxesDto = new MailBoxesDto();
        mailBoxesDto.setUserName(email);
        mailBoxesDto.setMessage(email+""+"mailboxes created for user");

//        MessageRequest messageRequest = new MessageRequest();
//        messageRequest.setMessageBody(email+""+"mailboxes created for user");
//        messageRequest.setSender("mailadmin");
//        messageRequest.setReceiver(email);
//
//        messageService.sendMessageToNewUser(messageRequest);


//        System.out.println(newMailBoxes);

        return  newMailBoxes;
    }
}
