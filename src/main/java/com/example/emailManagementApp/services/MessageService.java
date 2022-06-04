package com.example.emailManagementApp.services;


import com.example.emailManagementApp.dtos.request.MessageRequest;
import com.example.emailManagementApp.dtos.response.SentMessageResponseDto;
import com.example.emailManagementApp.models.Message;
import com.example.emailManagementApp.models.User;

import java.util.List;

public interface MessageService {

     Message sendMessageToNewUser(MessageRequest messageRequest);

     SentMessageResponseDto messageCanBeSendFromOneUserToAnotherUser(MessageRequest messageRequest);

     Message userCanFindAMessageInListOfMessageInsideInbox(MessageRequest messageRequest);

     Message userCanFindAMessageInListOfMessageInsideInOutBox(MessageRequest messageRequest);

     List<User> receivedMessageCanBeForwardedToAnotherUser(MessageRequest messageRequest);
}
