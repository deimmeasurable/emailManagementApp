package com.example.emailManagementApp.services;


import com.example.emailManagementApp.dtos.request.UserRequest;
import com.example.emailManagementApp.dtos.request.UserRequestLogInDto;
import com.example.emailManagementApp.dtos.response.UserDto;
import com.example.emailManagementApp.dtos.response.UserResponseLogIn;
import com.example.emailManagementApp.models.User;

import java.util.Optional;

public interface UserService {
    UserDto createUser(String email, String password);
    UserResponseLogIn userCreatedCanLogIn(UserRequestLogInDto request);


}
