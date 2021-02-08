package com.devsawe.demo.controllers;

import com.devsawe.demo.Service.ReviewService;
import com.devsawe.demo.authentication.CustomUserDetails;
import com.devsawe.demo.entities.JobApplicationModel;
import com.devsawe.demo.entities.UserRatingModel;
import com.devsawe.demo.repositories.JobRepository;
import com.devsawe.demo.repositories.UserRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RatingController {


    @Autowired
    private UserRatingRepository userRatingRepository;


    @PostMapping("/rating")
    public ResponseEntity<?> submitRating(@Valid @RequestBody UserRatingModel userRatingModel) {
        Map<String, String> resp = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        userRatingModel.setWorkerId(customUserDetails.getId());
        if (customUserDetails.getUserType().equalsIgnoreCase("employer")) {
            userRatingRepository.save(userRatingModel);
            resp.put("state", "success");
            resp.put("msg", "Rating added successfully");
            return ResponseEntity.ok(resp);
        }
        resp.put("state", "Failed");
        resp.put("msg", "UserType lacks permissions");
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/rating/{id}")
    public ResponseEntity<List<UserRatingModel>> getUserRating(@PathVariable Long userId) {
        List<UserRatingModel> userRatingModels = userRatingRepository.findByUserId(userId);
        return ResponseEntity.ok(userRatingModels);
    }
}
