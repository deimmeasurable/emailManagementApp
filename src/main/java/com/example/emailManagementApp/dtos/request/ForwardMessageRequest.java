package com.example.emailManagementApp.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForwardMessageRequest {
    private String userForwardMessage;
    private String receiverForwardMessage;
    private String forwardMessageBody;
}
