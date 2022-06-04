package com.example.emailManagementApp.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForwardMessageResponse {
    private String userForwardMessage;
    private String receiverForwardMessage;
    private String forwardMessageBody;

}
