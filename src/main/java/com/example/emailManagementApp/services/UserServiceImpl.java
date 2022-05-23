package com.example.emailManagementApp.services;


import com.example.emailManagementApp.dtos.request.UserRequestLogInDto;
import com.example.emailManagementApp.dtos.response.UserDto;
import com.example.emailManagementApp.dtos.response.UserResponseLogIn;
import com.example.emailManagementApp.exceptions.EmailManagementAppException;
import com.example.emailManagementApp.exceptions.UserDidNotLogInException;
import com.example.emailManagementApp.exceptions.UserDoesNotExistException;
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
//            System.out.println(savedUser);

            return mapper.map(savedUser, UserDto.class);
        }

        throw new EmailManagementAppException("user with email and password alreadyExist");
    }

    @Override
    public UserResponseLogIn userCreatedCanLogIn(UserRequestLogInDto request) {
        User foundUser = userRepository.findUserByEmail(request.getEmail()).orElseThrow(() -> new UserDoesNotExistException("user don't exist"));
        if (!foundUser.isIslongIn()) {
            foundUser.setIslongIn(true);
            userRepository.save(foundUser);
//        }else {
//            throw new UserDidNotLogInException("user didn't login");
//        }
//            userRepository.save(foundUser);
            System.out.println(foundUser);
            UserResponseLogIn userResponseLogIn = new UserResponseLogIn();
            userResponseLogIn.setPassword(request.getPassword());
            userResponseLogIn.setUsername(request.getEmail());
            userResponseLogIn.setLogInSuccessMessage(request.getEmail() + "," + request.getMessage());

            return userResponseLogIn;
        } else {
            throw new UserDidNotLogInException("user didn't login");
        }

    }
}
