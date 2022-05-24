package com.example.emailManagementApp.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailBoxesMessageRequestDto {
    private String userName;
    private String receiver;
    private String messageBody;

}
