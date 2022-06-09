package com.example.emailManagementApp.dtos.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ForwardMessageRequest {
    private String userForwardMessage;
    private String receiverForwardMessage;
    private String forwardMessageBody;
}
