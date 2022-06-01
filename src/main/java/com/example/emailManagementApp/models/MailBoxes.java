package com.example.emailManagementApp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document
public class MailBoxes {
    @Id
    private String userName;
    List<MailBox> mailBox = new ArrayList<>();

}
