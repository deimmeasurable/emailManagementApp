package com.example.emailManagementApp.models;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    private String title;
    private boolean isReadMessage;
    private String sentMessage;
    private String sender;


    @Id
    private String id;


}
