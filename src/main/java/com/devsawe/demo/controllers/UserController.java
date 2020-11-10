package com.devsawe.demo.controllers;

import com.devsawe.demo.Service.UserService;
import com.devsawe.demo.authentication.CustomUserDetails;
import com.devsawe.demo.entities.JobApplicationModel;
import com.devsawe.demo.entities.User;
import com.devsawe.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/users/register")
    public ResponseEntity<Object> registerEmployer(@RequestParam("username") String username,
                                                   @RequestParam("password") String password,
                                                   @RequestParam("userType") String userType) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setUserType(userType);
        user = userService.registerUser(user);
        return ResponseEntity.ok(user);
    }

}
