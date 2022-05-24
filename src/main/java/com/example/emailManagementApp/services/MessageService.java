package com.example.emailManagementApp.services;

import com.example.emailManagementApp.dtos.request.MessageRequest;
import com.example.emailManagementApp.dtos.response.CreateNewUserMessageDto;
import com.example.emailManagementApp.models.Message;

public interface MessageService {

     Message sendMessageToNewUser(CreateNewUserMessageDto messageRequest);
}
