package com.example.emailManagementApp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
@ToString
@Document
public class User {
    @Id
    private String email;
    private String password;
    private List<Notification> notificationlist=new ArrayList<>();
    private boolean isLogInStatus = false;
    private Set<Role> roles;


    public User(String email, String password, RoleType roleType) {
        this.email = email;
        this.password = password;
        if (roles == null){
            roles = new HashSet<>();
        }
        roles.add(new Role(roleType));
    }

    public void addRole(Role role){
        if (this.roles == null){
            this.roles = new HashSet<>();
        }
        roles.add(role);
    }
}
