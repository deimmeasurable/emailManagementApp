package com.example.emailManagementApp.services;


import com.example.emailManagementApp.dtos.request.UserRequestLogInDto;
import com.example.emailManagementApp.dtos.response.NotificationCheckedResponse;
import com.example.emailManagementApp.dtos.response.UserDto;
import com.example.emailManagementApp.dtos.response.UserResponseLogIn;
import com.example.emailManagementApp.models.Notification;
import com.example.emailManagementApp.repositories.UserRepository;

public interface UserService {
    UserDto createUser(String email, String password);
    UserResponseLogIn userCreatedCanLogIn(String email, String password);

    UserRepository getRepository();

    NotificationCheckedResponse userCheckNotificationWhenTheyLoggedIn(Notification checkNotification);



}
