package com.example.emailManagementApp.dtos.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestLogInDto {
    private String email;
    private String password;
    private String message;

}
