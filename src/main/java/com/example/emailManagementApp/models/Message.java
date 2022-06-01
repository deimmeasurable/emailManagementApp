package com.example.emailManagementApp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document
public class Message {
    private String sender;
    private String receiver;
    private String messageBody;
    private LocalDateTime date;
    private String messageTitle;
    @Id
    private String userName;
}
