package com.example.emailManagementApp.models;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Message {
    private String sender;
    private String receiver;
    private String messageBody;
    private LocalDateTime date;
    private String messageTitle;
    @Id
    private String userName;
}
