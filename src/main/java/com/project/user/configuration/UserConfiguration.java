package com.project.user.configuration;

import com.project.user.controller.UserController;
import com.project.user.model.User;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@ComponentScan({ "com.project.user.*" })
public class UserConfiguration {
    @Bean(name="user")
    public User userBean() {
        return new User();
    }

    @Bean(name="userController")
    public UserController userControllerBean() {
        return new UserController();
    }

    @Bean(name="userMap")
    public Map<String, User> userMapBean() {
        return Stream.of(this.userBean()).collect(Collectors.toMap(
                User::getId, Function.identity()));
    }
}