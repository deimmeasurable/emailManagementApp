package com.example.emailManagementApp.services;


import com.example.emailManagementApp.dtos.response.NotificationCheckedResponse;
import com.example.emailManagementApp.dtos.response.UserDto;
import com.example.emailManagementApp.dtos.response.UserResponseLogIn;
import com.example.emailManagementApp.exceptions.EmailManagementAppException;
import com.example.emailManagementApp.exceptions.UserDidNotLogInException;
import com.example.emailManagementApp.exceptions.UserDidNotLogInNotificationException;
import com.example.emailManagementApp.exceptions.UserDoesNotExistException;
import com.example.emailManagementApp.models.Notification;
import com.example.emailManagementApp.models.Role;
import com.example.emailManagementApp.models.User;
import com.example.emailManagementApp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

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
            User user = new User(email, password);
            mailBoxesService.createMailBoxes(email);
            User savedUser = userRepository.save(user);

            return mapper.map(savedUser, UserDto.class);
        } else {

            throw new EmailManagementAppException("user with email and password alreadyExist");
        }
    }
    @Override
    public UserResponseLogIn userCreatedCanLogIn(String email, String password) {
        User foundUser = userRepository.findUserByEmail(email).orElseThrow(() -> new UserDoesNotExistException("user don't exist"));

        if(!foundUser.getPassword().equals(password)){
            throw new UserDoesNotExistException("user password is incorrect");
        }

        if (!foundUser.isLogInStatus()) {
            log.info("login status-->{}", foundUser.isLogInStatus());
           foundUser.setLogInStatus(true);
            userRepository.save(foundUser);

               System.out.println(foundUser.getPassword());
            UserResponseLogIn userResponseLogIn = new UserResponseLogIn();
            userResponseLogIn.setPassword(password);
            userResponseLogIn.setUsername(email);
            userResponseLogIn.setLogInSuccessMessage(email + ", user login" );

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
            log.info("-->{}", foundUser.isLogInStatus());
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

    @Override
    public List<User> findAllUsers(User user) {
        List<User> users=new ArrayList<>();
        users=userRepository.findAll();
        return users;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UserDoesNotExistException("user don't exist"));

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username).orElseThrow(() -> new UserDoesNotExistException("user don't exist"));
        org.springframework.security.core.userdetails.User returnedUser = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user.getRoles()));
        return returnedUser;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {

        Collection<? extends SimpleGrantedAuthority> authorities=roles.stream().map(
                role ->new SimpleGrantedAuthority(role.getRoleType().name())
        ).collect(Collectors.toSet());
        return authorities;
    }


}
