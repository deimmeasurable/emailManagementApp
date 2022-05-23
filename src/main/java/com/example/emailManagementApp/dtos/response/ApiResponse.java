package com.example.emailManagementApp.dtos.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
    private Object payload;
    private boolean isSuccessful;
    private int statusCode;
    private String message;
}
