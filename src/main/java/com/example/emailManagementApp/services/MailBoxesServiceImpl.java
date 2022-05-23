package com.example.emailManagementApp.services;

import com.example.emailManagementApp.dtos.response.MailBoxesDto;
import com.example.emailManagementApp.models.MailBox;
import com.example.emailManagementApp.models.MailBoxes;
import com.example.emailManagementApp.repositories.MailBoxesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.emailManagementApp.models.MailboxType.INBOX;
import static com.example.emailManagementApp.models.MailboxType.SENT;

@Service
public class MailBoxesServiceImpl implements MailBoxesService{
    @Autowired
    MailBoxesRepository mailBoxesRepository;

    private ModelMapper mapper;

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


        System.out.println(newMailBoxes);

        return  newMailBoxes;
    }
}
