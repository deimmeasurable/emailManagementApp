package com.example.emailManagementApp.services;

import com.example.emailManagementApp.dtos.request.UserRequestLogInDto;
import com.example.emailManagementApp.exceptions.EmailManagementAppException;
import com.example.emailManagementApp.models.AccessToken;

public interface LoginService {
    AccessToken login(UserRequestLogInDto loginRequest) throws EmailManagementAppException;
}
