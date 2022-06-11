//package com.example.emailManagementApp.security;
//
//import com.example.emailManagementApp.security.jwt.JwtAuthenticationFilter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//@Slf4j
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(
//        prePostEnabled = true
//)
//public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    UserDetailsService userDetailsService;
//
//    @Autowired
//    UnAuthorizedEntryPoint unAuthorizedEntryPoint;
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable()
//                .authorizeHttpRequests(authorize -> {
//                    try {
//                        authorize.antMatchers("/**/users/create/**", "/**/users/all/**","/**/auth/login").permitAll()
////                                .antMatchers("/**/diary/add/**").hasAnyRole("ROLE_ADMIN")
//                                .anyRequest().authenticated()
//                                .and()
//                                .exceptionHandling().authenticationEntryPoint(unAuthorizedEntryPoint)
//                                .and()
//                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//                        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
//                        http.addFilterBefore( exceptionHandlerFilterBean(), JwtAuthenticationFilter.class);
//
//                    } catch (Exception e) {
//                        throw new RuntimeException(e.getMessage());
//                    }
//                });
//    }
//    @Bean
//    public JwtAuthenticationFilter authenticationTokenFilterBean(){
//        return new JwtAuthenticationFilter();
//    }
//
//    @Bean
//    public ExceptionHandlerFilter exceptionHandlerFilterBean(){
//        return new ExceptionHandlerFilter();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//}
