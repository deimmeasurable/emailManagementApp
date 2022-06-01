package com.example.emailManagementApp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Document
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MailBox {
    @Id
    private String userName;
    private MailboxType mailboxType;
    private List<Message> message= new ArrayList<>();
}
