package com.example.emailManagementApp.models;

import lombok.*;
import org.springframework.data.annotation.Id;


import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MailBoxes {
    @Id
    private String userName;
    List<MailBox> mailBox = new ArrayList<>();

}
