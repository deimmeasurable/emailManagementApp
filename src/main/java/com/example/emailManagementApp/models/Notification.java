package com.example.emailManagementApp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document
public class Notification {
    private String title;
    private boolean isReadMessage;
    private String sentMessage;
    private String sender;
    private String receiver;


    @Id
    private String id;


}
