package com.example.emailManagementApp.models;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Notification {
    private String title;
    private boolean isReadMessage;
    private String sentMessage;
    private String sender;
    private String receiver;


    @Id
    private String id;


}
