package com.example.emailManagementApp.services;


import com.example.emailManagementApp.dtos.request.MessageRequest;
import com.example.emailManagementApp.dtos.response.CreateNewUserMessageDto;
import com.example.emailManagementApp.dtos.response.SentMessageResponseDto;
import com.example.emailManagementApp.models.Message;

import java.util.List;

public interface MessageService {

     Message sendMessageToNewUser(MessageRequest messageRequest);

     SentMessageResponseDto messageCanBeSendFromOneUserToAnotherUser(MessageRequest messageRequest);

     List<Message> userCanFindAMessageInListOfMessageInsideInbox(MessageRequest messageRequest);
}
