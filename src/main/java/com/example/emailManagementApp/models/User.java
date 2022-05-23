package com.example.emailManagementApp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.validation.annotation.Validated;


import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
@ToString
public class User {
    @Id
    private String email;
    private String password;
    private List<Notification> message;
    private boolean islongIn = false;


}
