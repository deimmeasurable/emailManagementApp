package com.example.emailManagementApp.services;


import com.example.emailManagementApp.dtos.request.UserRequestLogInDto;
import com.example.emailManagementApp.dtos.response.NotificationCheckedResponse;
import com.example.emailManagementApp.dtos.response.UserDto;
import com.example.emailManagementApp.dtos.response.UserResponseLogIn;
import com.example.emailManagementApp.exceptions.EmailManagementAppException;
import com.example.emailManagementApp.exceptions.UserDidNotLogInException;
import com.example.emailManagementApp.exceptions.UserDidNotLogInNotificationException;
import com.example.emailManagementApp.exceptions.UserDoesNotExistException;
import com.example.emailManagementApp.models.Notification;
import com.example.emailManagementApp.models.User;
import com.example.emailManagementApp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@NoArgsConstructor
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private ModelMapper mapper;


    private MailBoxesService mailBoxesService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MailBoxesService mailBoxesService) {
        this.userRepository = userRepository;
        this.mailBoxesService = mailBoxesService;
        this.mapper = new ModelMapper();


    }


    @Override
    public UserDto createUser(String email, String password) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isEmpty()) {
            User user = new User(email, password, new java.util.ArrayList<>(), false);
            mailBoxesService.createMailBoxes(email);
            User savedUser = userRepository.save(user);

            return mapper.map(savedUser, UserDto.class);
        } else {

            throw new EmailManagementAppException("user with email and password alreadyExist");
        }
    }
    @Override
    public UserResponseLogIn userCreatedCanLogIn(UserRequestLogInDto request) {
        User foundUser = userRepository.findUserByEmail(request.getEmail()).orElseThrow(() -> new UserDoesNotExistException("user don't exist"));
        if (!foundUser.isLogInStatus()) {
           foundUser.setLogInStatus(true);
            userRepository.save(foundUser);

//            System.out.println(foundUser);
            UserResponseLogIn userResponseLogIn = new UserResponseLogIn();
            userResponseLogIn.setPassword(request.getPassword());
            userResponseLogIn.setUsername(request.getEmail());
            userResponseLogIn.setLogInSuccessMessage(request.getEmail() + "," + request.getMessage());

            return userResponseLogIn;
        } else {
            throw new UserDidNotLogInException("user didn't login");
        }

    }

    @Override
    public UserRepository getRepository() {
    return userRepository;
    }

    @Override
    public NotificationCheckedResponse userCheckNotificationWhenTheyLoggedIn(Notification checkNotification) {
        User foundUser = userRepository.findUserByEmail(checkNotification.getId()).orElseThrow(()->new UserDoesNotExistException("user don't exist"));
        if(!foundUser.isLogInStatus()){
            foundUser.setLogInStatus(true);

            Notification notification = new Notification();
            notification.setReadMessage(true);
            notification.setSentMessage(checkNotification.getSentMessage());
            notification.setTitle(checkNotification.getTitle());

            foundUser.getNotificationlist().remove(notification);
            System.out.println(foundUser);

            NotificationCheckedResponse notificationUpdate = new NotificationCheckedResponse();
            notificationUpdate.setNotificationRead(true);
            notificationUpdate.setDate(notificationUpdate.getDate());
            notificationUpdate.setUserName(checkNotification.getId());
            notificationUpdate.setMessageReceived(checkNotification.getSentMessage());

            return notificationUpdate;

        }else{
            throw new UserDidNotLogInNotificationException("user didn't login, so can't check notification");
        }

    }

}
