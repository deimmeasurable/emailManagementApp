package com.example.emailManagementApp.services;


import com.example.emailManagementApp.dtos.request.UserRequest;
import com.example.emailManagementApp.dtos.request.UserRequestLogInDto;
import com.example.emailManagementApp.dtos.response.UserDto;
import com.example.emailManagementApp.dtos.response.UserResponseLogIn;
import com.example.emailManagementApp.exceptions.EmailManagementAppException;
import com.example.emailManagementApp.exceptions.UserDidNotLogInException;
import com.example.emailManagementApp.models.User;
import com.example.emailManagementApp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class UserServiceImplTest {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailBoxesService mailBoxesService;

    @Test
    public void testThatCanBeCreated() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("newemail@gmail.com");
        userRequest.setPassword("password");
        mailBoxesService.createMailBoxes("newemail@gmail.com");
        UserDto userDto = userService.createUser("newemail@gmail.com", "password");

     assertThat(userDto.getEmail(),is("newemail@gmail.com"));
     assertThat(userDto.getPassword(),is("password"));

    }
    @Test
    public void testThatUserWithSameEmailAndPasswordCanNotRegisterThrowsException(){
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("newemail@gmail.com");
        userRequest.setPassword("password");
        mailBoxesService.createMailBoxes("newemail@gmail.com");
        userService.createUser("newemail@gmail.com","password");

        UserRequest userRequest1= new UserRequest();
        userRequest1.setEmail("newemail@gmail.com");
        userRequest1.setPassword("password");
        mailBoxesService.createMailBoxes("newemail@gmail.com");


       assertThrows(EmailManagementAppException.class,()->userService.createUser("newemail@gmail.com","password"));



    }
    @Test
    public void testThatRegisterUserCanLogIn(){
        User user = new User();
        user.setEmail("newemail@gmail.com");
        user.setPassword("password");
        user.setLogInStatus(true);
        userService.createUser("newemail@gmail.com","password");
        mailBoxesService.createMailBoxes("newemail@gmail.com");

        UserRequestLogInDto request = new UserRequestLogInDto();
        request.setEmail("newemail@gmail.com");
        request.setPassword("password");
          userService.userCreatedCanLogIn(request);

          UserResponseLogIn userResponseLogIn = new UserResponseLogIn();
          userResponseLogIn.setUsername("newemail@gmail.com");
          userResponseLogIn.setPassword("password");
          userResponseLogIn.setLogInSuccessMessage("user logIn successfully");

          assertThat(userResponseLogIn.getLogInSuccessMessage(),is("user logIn successfully"));
          assertThat(userResponseLogIn.getUsername(),is("newemail@gmail.com"));




    }
    @Test
    public  void testThatIfUserIsNotLoggedInThrowsException(){
        //given
        User user = new User();
        user.setEmail("newemail@gmail.com");
        user.setPassword("password");
        user.setLogInStatus(false);
        userService.createUser("newemail@gmail.com","password");
        mailBoxesService.createMailBoxes("newemail@gmail.com");

        UserRequestLogInDto request = new UserRequestLogInDto();
        request.setEmail("newemail@gmail.com");
        request.setPassword("password");
        userService.userCreatedCanLogIn(request);

        assertThrows(UserDidNotLogInException.class,()-> userService.userCreatedCanLogIn(request));
    }



}