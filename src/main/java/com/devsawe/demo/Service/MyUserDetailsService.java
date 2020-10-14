package com.devsawe.demo.Service;

import com.devsawe.demo.authentication.CustomUserDetails;
import com.devsawe.demo.entities.User;
import com.devsawe.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.devsawe.demo.entities.User user = this.userRepository.findByUsername(username);
        return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword(), user.getUserType(), new ArrayList<>());
    }


}
