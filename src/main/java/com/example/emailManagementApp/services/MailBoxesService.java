package com.example.emailManagementApp.services;

import com.example.emailManagementApp.models.MailBoxes;
import org.springframework.stereotype.Service;

@Service
public interface MailBoxesService {
    MailBoxes createMailBoxes(String email);
}
