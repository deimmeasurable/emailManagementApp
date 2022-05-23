package com.example.emailManagementApp.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseLogIn {
    private String username;
    private String password;
    private String logInSuccessMessage;
}
