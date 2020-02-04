package com.devsawe.demo.controllers;

import com.devsawe.demo.Service.UserService;
import com.devsawe.demo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

     /*@PostMapping("/users/signup")
         public ResponseEntity<?> registerUser(@RequestBody User user) {
             user = userService.registerUser(user);
             return ResponseEntity.ok(user);
         }*/

    @PostMapping(value = "/users/register")
    public ResponseEntity<Object> registerUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user = userService.registerUser(user);
        return ResponseEntity.ok(user);
    }

  /* @PostMapping("/users")
   public User createUser(@Valid @RequestBody User user){
       return userService.registerUser(user);
   }*/

  /*  @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestParam("username") String username, @RequestParam("password") String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user = userService.updateUser(user);
        return ResponseEntity.ok(user);
    }*/
}
