package com.devsawe.demo.Service;

import com.devsawe.demo.entities.User;
import com.devsawe.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user){
     user=   this.userRepository.save(user);
     return user;
    }

    public User updateUser(User user){
        user = this.userRepository.save(user);
        return user;
    }
}
