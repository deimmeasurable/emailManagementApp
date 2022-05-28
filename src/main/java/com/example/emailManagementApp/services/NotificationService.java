package com.example.emailManagementApp.services;

import com.example.emailManagementApp.dtos.request.MessageRequest;
import com.example.emailManagementApp.dtos.response.NotificationRespnseDto;
import com.example.emailManagementApp.models.Notification;

public interface NotificationService {

      NotificationRespnseDto createNotification(Notification sendNotification, MessageRequest messageRequest);

}
