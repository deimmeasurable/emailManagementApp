package com.example.emailManagementApp.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@lombok.Builder

public class UserRequest {
    private String email;
    private String password;
}
