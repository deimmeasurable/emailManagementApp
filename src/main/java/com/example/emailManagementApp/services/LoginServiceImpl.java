package com.example.emailManagementApp.services;

import com.example.emailManagementApp.dtos.request.UserRequestLogInDto;
import com.example.emailManagementApp.exceptions.EmailManagementAppException;
import com.example.emailManagementApp.models.AccessToken;
import com.example.emailManagementApp.models.User;
import com.example.emailManagementApp.security.jwt.TokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    UserServiceImpl userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    TokenServices tokenServices;



    @Override
    public AccessToken login(UserRequestLogInDto loginRequest) throws EmailManagementAppException {
        User user = userService.findUserByEmail(loginRequest.getEmail());

        if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String token=tokenServices.generateToken(userDetails);
            return new AccessToken(token);
        }
        throw new EmailManagementAppException("invalid Password");
    }
}
