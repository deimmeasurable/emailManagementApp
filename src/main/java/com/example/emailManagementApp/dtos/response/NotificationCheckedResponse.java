package com.example.emailManagementApp.dtos.response;

import lombok.*;

import java.time.LocalDateTime;
@Setter
@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor
@Builder
public class NotificationCheckedResponse {
    private boolean notificationRead;
    private LocalDateTime date;
    private String userName;
    private String messageReceived;
}
