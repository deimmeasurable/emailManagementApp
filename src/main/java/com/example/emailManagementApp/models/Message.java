package com.example.emailManagementApp.models;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private String sender;
    private String receiver;
    private String messageBody;
    private LocalDateTime date;
    @Id
    private String userName;
}
