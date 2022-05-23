package com.example.emailManagementApp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MailBox {
    @Id
    private String userName;
    private MailboxType mailboxType;
}
