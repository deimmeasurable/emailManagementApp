package com.example.emailManagementApp.services;

import com.example.emailManagementApp.dtos.request.MessageRequest;
import com.example.emailManagementApp.models.MailBox;
import com.example.emailManagementApp.models.MailBoxes;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MailBoxesService {
    MailBoxes createMailBoxes(String email);
    MailBoxes findMailBoxesByUserName(String userName);

    List<MailBox> userCanViewAllInbox(MessageRequest messageRequest);
}
