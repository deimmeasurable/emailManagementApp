package com.example.emailManagementApp.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateNewUserMessageDto {
    private  String email;
    private String sender;
    private String messageBody;
    private LocalDateTime date = LocalDateTime.now();
    private String receiver;

    public CreateNewUserMessageDto(String email, String sender, String messageBody){
                    this.email = email;
                    this.sender = sender;
                    this.messageBody = messageBody;
    }
}
