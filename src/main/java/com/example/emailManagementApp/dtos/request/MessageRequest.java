package com.example.emailManagementApp.dtos.request;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class MessageRequest {
    private String receiver;
    private String sender;
    private String messageBody;
    private LocalDateTime date;
    private String messageTitle;

    @Id
    private String userName;

}
