package com.example.emailManagementApp.dtos.request;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageRequest {
    private String receiver;
    private String sender;
    private String messageBody;
    private LocalDateTime date;

    @Id
    private String userName;

}