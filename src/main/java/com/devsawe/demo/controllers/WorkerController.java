package com.devsawe.demo.controllers;

import com.devsawe.demo.authentication.CustomUserDetails;
import com.devsawe.demo.entities.WorkerProfile;
import com.devsawe.demo.repositories.UserRepository;
import com.devsawe.demo.repositories.WorkersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WorkerController {

    @Autowired
    private WorkersRepository workersRepository;

    @Autowired
    private UserRepository userRepository;


    //create a worker skills profile
    @PostMapping("/workers/new")
    public ResponseEntity<?> createWorkerProfile(@Valid @RequestBody WorkerProfile workerProfile) {
        Map<String, String> resp = new HashMap<>();
        CustomUserDetails  customUserDetails =
                (CustomUserDetails) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        workerProfile.setUserId(customUserDetails.getId());
        String userType = customUserDetails.getUserType();
        //check if user is employee here
        if (userType.equalsIgnoreCase("employee")) {
            workersRepository.save(workerProfile);
            resp.put("state", "success");
            resp.put("msg", "Worker Created successfully");
            return ResponseEntity.ok(resp);
        }
        resp.put("state", "Failed");
        resp.put("msg", "UserType lacks permissions");
        return ResponseEntity.ok(resp);
    }

    //all workers profiles
    @GetMapping("/worker/profiles")
    public ResponseEntity<List<WorkerProfile>> getWorkersProfiles() {
        List<WorkerProfile> workerProfiles = workersRepository.findAll();
        return ResponseEntity.ok(workerProfiles);
    }


}
