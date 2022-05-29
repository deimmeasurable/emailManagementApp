package com.example.emailManagementApp.dtos.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SentMessageResponseDto {
    private String messageBody;
    private String sender;
    private String userName;
    private LocalDateTime date =LocalDateTime.now();
    private String receiver;
}
