package com.example.emailManagementApp.services;

import com.example.emailManagementApp.dtos.request.MessageRequest;

import com.example.emailManagementApp.dtos.response.NotificationResponseDto;
import com.example.emailManagementApp.models.Notification;

public interface NotificationService {

      NotificationResponseDto createNotification( MessageRequest messageRequest);

}
