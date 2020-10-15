package com.devsawe.demo.controllers;

import com.devsawe.demo.authentication.CustomUserDetails;
import com.devsawe.demo.entities.JobModel;
import com.devsawe.demo.repositories.JobRepository;
import com.devsawe.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    //works creates jobs list for the currently logged in user.
    @PostMapping("/create/jobs")
    public JobModel createJobsList(@Valid @RequestBody JobModel jobModel) {
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        jobModel.setEmployerId(customUserDetails.getId());
        return jobRepository.save(jobModel);
    }

    @PutMapping("my-jobs/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody JobModel jobModel) {
        Map<String, String> resp = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        JobModel jobModel1 = jobRepository.findById(id).orElse(null);

        if (jobModel1 == null) {
            resp.put("state", "danger");
            resp.put("msg", "id not found");
            return ResponseEntity.ok(resp);
        }
        jobModel1.setId(jobModel.getId());
        jobModel1.setJobTitle(jobModel.getJobTitle());
        jobModel1.setJobLocation(jobModel.getJobLocation());
        jobModel1.setCompanyInfo(jobModel.getCompanyInfo());
        jobModel1.setCompanyName(jobModel.getCompanyName());
        jobModel1.setQualifications(jobModel.getQualifications());
        jobModel1.setJobLevel(jobModel.getJobLevel());
        jobModel1.setEmployerId(customUserDetails.getId());
        jobRepository.save(jobModel1);
        resp.put("state", "success");
        resp.put("msg", "Job updated successfully");
        return ResponseEntity.ok(resp);
    }


    //get the job list posted by the current Employer(logged in)
    @GetMapping("/my-jobs")
    public ResponseEntity<List<JobModel>> currentEmployerJobs() {
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        List<JobModel> jobModels = jobRepository.findByEmployerId(customUserDetails.getId());
        return ResponseEntity.ok(jobModels);
    }



    //get the jobs by a specific location
    @GetMapping("/nearby-jobs")
    public ResponseEntity<List<JobModel>> NearbyJobs(@RequestParam(value = "location") String location) {
       /* CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();*/
        List<JobModel> jobModels = jobRepository.findNearbyJobs(location);
        return ResponseEntity.ok(jobModels);
    }

    //get jobs
    @GetMapping("/jobs")
    public ResponseEntity<List<JobModel>> getAllJobs() {
        List<JobModel> jobModels = jobRepository.findAll();
        return ResponseEntity.ok(jobModels);
    }

    //get the todos where status = Completed
    @GetMapping("/todo-completed")
    public ResponseEntity<List<JobModel>> todoCompleted() {
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        List<JobModel> jobModels = jobRepository.findByCompletedStatus(customUserDetails.getId());
        return ResponseEntity.ok(jobModels);
    }

    //get the todos where status = pending
    @GetMapping("/todo-pending")
    public ResponseEntity<List<JobModel>> todoPending() {
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        List<JobModel> jobModels = jobRepository.findByPendingStatus(customUserDetails.getId());
        return ResponseEntity.ok(jobModels);
    }

    @DeleteMapping("/my-todos/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        JobModel jobModel = jobRepository.findById(id).orElse(null);

        if (jobModel == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        jobRepository.delete(jobModel);
        return new ResponseEntity<Void>(HttpStatus.OK);

    }

 /*   @GetMapping("/mytodos-today")
    public ResponseEntity<List<Map<String, String>>> Todostoday(@RequestParam(value = "date") String date){
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        List<Map<String, String>> todotitle;
         todotitle = todoRepository.findByCreatedAt(customUserDetails.getId(),date);
        return  ResponseEntity.ok(todotitle);
    }*/

    /*@GetMapping("/mytodos-week")
    public ResponseEntity<List<Map<String, String>>> TodosWeek(){
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        List<Map<String, String>> todotitle;
        todotitle = todoRepository.findByWeek(customUserDetails.getId());
        return ResponseEntity.ok(todotitle);
    }*/

    //change this one to see the updated status of job application
  /*  @PutMapping("my-todos/completed/{id}")
    public ResponseEntity<?> completed(@PathVariable long id) {
        Map<String, String> resp = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        JobModel jobModel1 = jobRepository.findById(id).orElse(null);

        if (jobModel1 == null) {
            resp.put("state", "danger");
            resp.put("msg", "The user does not a todo with that id");
            return ResponseEntity.ok(resp);
        }

        //jobModel1.setStatus("Completed");
        //todoModel1.setUserId(customUserDetails.getId());
        jobRepository.save(jobModel1);
        resp.put("state", "success");
        resp.put("msg", "state updated successfully");
        return ResponseEntity.ok(resp);
    }*/
}