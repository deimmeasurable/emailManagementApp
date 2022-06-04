package com.example.emailManagementApp.controller;

import com.example.emailManagementApp.dtos.request.UserRequestLogInDto;
import com.example.emailManagementApp.dtos.response.ApiResponse;
import com.example.emailManagementApp.dtos.response.UserDto;
import com.example.emailManagementApp.dtos.response.UserResponseLogIn;
import com.example.emailManagementApp.exceptions.EmailManagementAppException;
import com.example.emailManagementApp.exceptions.UserDidNotLogInException;
import com.example.emailManagementApp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/emailApp")
public class UserEmailController {
    private final UserService userService;


    public UserEmailController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/create")
    public ResponseEntity<?> createUser(@RequestParam  String email, @RequestParam  String password) throws EmailManagementAppException {
        try {
            UserDto userDto = userService.createUser(email, password);
            ApiResponse apiResponse = ApiResponse.builder()
                    .payload(userDto)
                    .isSuccessful(true)
                    .statusCode(201)
                    .message("user created successfully")
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (EmailManagementAppException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .isSuccessful(false)
                    .statusCode(400)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/login/{email}/{password}")
   public ResponseEntity<?> userCreatedCanLogIn(@PathVariable String email, @PathVariable String password){
        try {
            UserResponseLogIn userResponseLogIn= userService.userCreatedCanLogIn(email,password);
            ApiResponse apiResponse = ApiResponse.builder()
                    .payload(userResponseLogIn)
                    .isSuccessful(true)
                    .statusCode(201)
                    .message("user login successfully")
                    .build();
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);


        }catch(UserDidNotLogInException e){
            ApiResponse apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .isSuccessful(false)
                    .statusCode(400)
                    .build();
            return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
        }

    }
}