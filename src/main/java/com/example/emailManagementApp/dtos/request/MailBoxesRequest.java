package com.example.emailManagementApp.dtos.request;

import com.example.emailManagementApp.models.MailBox;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailBoxesRequest {
    private String userName;
    List<MailBox> MailBox=new ArrayList<>();
}
