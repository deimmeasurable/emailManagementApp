package com.example.emailManagementApp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String sender;
    private String receiver;
    private String messageBody;
    private LocalDateTime date;
    @Id
    private String userName;
}
