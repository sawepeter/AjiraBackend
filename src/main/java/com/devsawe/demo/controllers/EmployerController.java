package com.devsawe.demo.controllers;

import com.devsawe.demo.authentication.CustomUserDetails;
import com.devsawe.demo.entities.EmployerProfile;
import com.devsawe.demo.repositories.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EmployerController {

    @Autowired
    private EmployerRepository employerRepository;

    //create a worker skills profile
    @PostMapping("/employer/new")
    public ResponseEntity<?> createEmployerProfile(@Valid @RequestBody EmployerProfile employerProfile) {
        Map<String, String> resp = new HashMap<>();
        CustomUserDetails customUserDetails =
                (CustomUserDetails) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        employerProfile.setUserId(customUserDetails.getId());
        //employerProfile.setEmployer_name(customUserDetails.getUserName());
        String userType = customUserDetails.getUserType();
        //String username = customUserDetails.getUserName();
        //check if user is employee here
        if (userType.equalsIgnoreCase("employer")) {

            employerRepository.save(employerProfile);
            resp.put("state", "success");
            resp.put("msg", "Employer Profile Created successfully");
            return ResponseEntity.ok(resp);
        }
        resp.put("state", "Failed");
        resp.put("msg", "UserType lacks permissions");
        return ResponseEntity.ok(resp);
    }

    //all employer profiles
    @GetMapping("/employer/profiles")
    public ResponseEntity<List<EmployerProfile>> getEmployerProfiles() {
        List<EmployerProfile> employerProfiles = employerRepository.findAll();
        return ResponseEntity.ok(employerProfiles);
    }
}
