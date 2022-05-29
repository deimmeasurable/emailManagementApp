package com.example.emailManagementApp.dtos.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponseDto {
    private String title;
    private boolean isReadMessage;
    private String sentMessage;
    private String sender;
    private String receiver;
}
