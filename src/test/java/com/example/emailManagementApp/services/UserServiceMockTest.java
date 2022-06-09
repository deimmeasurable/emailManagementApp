package com.example.emailManagementApp.services;

import com.example.emailManagementApp.dtos.response.UserResponseLogIn;
import com.example.emailManagementApp.exceptions.EmailManagementAppException;
import com.example.emailManagementApp.exceptions.UserDidNotLogInException;
import com.example.emailManagementApp.models.User;
import com.example.emailManagementApp.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;
@Slf4j
@ExtendWith(MockitoExtension.class)
public class UserServiceMockTest {
    @Mock
    UserRepository userRepository;

    @Captor
    private ArgumentCaptor<User> argumentCaptor;

    private UserService userService;

    @Mock
    private MailBoxesService mailBoxesService;

    @BeforeEach
    void setup() {
        this.userService = new UserServiceImpl(userRepository, mailBoxesService);
    }
    @Test
    void testThatUserCanBeCreated(){
        String email= "newemail@gmail.com";
        String password= "password";

        User mockUser = new User("newemail@gmail.com", "password",new ArrayList<>(),false);
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
       var result = userService.createUser(email,password);
//        verify(userRepository,times(1)).save(argumentCaptor.capture());
//        User user = argumentCaptor.getValue();
       assertEquals(result.getEmail(), email);

    }
    @Test
    void testThatUserWithSameEmailCannotRegister(){

        User mockUser = new User("newemail@gmail.com", "password",new ArrayList<>(),false);
        User mockUser1 = new User("newemail@gmail.com","password",new ArrayList<>(),false);
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(mockUser));

        assertThrows(EmailManagementAppException.class,()->userService.createUser("newemail@gmail.com","password"));
    }
    @Test
    void testThatRegisterUserCanLogIn() {
        String email = "newemail@gmail.com";
        String password = "password";
        User mockUser = new User("newemail@gmail.com","password",new ArrayList<>(),false);

        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(mockUser));

        var result = userService.userCreatedCanLogIn(email,password);
        log.info("check the result-->",result);

        assertEquals(result.getUsername(),email);
        assertEquals(result.getPassword(),password);
    }
    @Test
    void testThatExceptionIfUserDidNotLogin() {
        String email = "newemail@gmail.com";
        String password = "password";
        User mockUser = new User("newemail@gmail.com","password",new ArrayList<>(),false);

       when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(mockUser));
//        when(userRepository.findUserByPassword(anyString())).thenReturn(Optional.of(mockUser));

        assertThrows(UserDidNotLogInException.class,()->userService.userCreatedCanLogIn(email,password));
    }
//    @AfterEach
//    void tearDown() {userService=null;}
}
