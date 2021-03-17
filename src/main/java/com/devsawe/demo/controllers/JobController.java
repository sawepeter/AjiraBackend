package com.devsawe.demo.controllers;

import com.devsawe.demo.authentication.CustomUserDetails;
import com.devsawe.demo.entities.EmployeeEarningModel;
import com.devsawe.demo.entities.JobApplicationModel;
import com.devsawe.demo.entities.JobModel;
import com.devsawe.demo.entities.WorkerProfile;
import com.devsawe.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    private ApplicationRepository applicationRepository;

    @Autowired
    private EarningsRepository earningsRepository;

    @Autowired
    private WorkersRepository workersRepository;

    @Autowired
    private UserRepository userRepository;

    //works creates jobs list for the currently logged in user.
    @PostMapping("/create/jobs")
    public ResponseEntity<?> createJobsList(@Valid @RequestBody JobModel jobModel) {
        Map<String, String> resp = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        jobModel.setEmployerId(customUserDetails.getId());
        jobModel.setEmployerName(customUserDetails.getUserName());
        jobModel.setJob_status("Available");
        jobModel.setPayment_status("unpaid");
        jobModel.setFavourite("false");
        //check if user is employee here
        if (customUserDetails.getUserType().equalsIgnoreCase("employer")) {
            jobRepository.save(jobModel);
            resp.put("state", "success");
            resp.put("msg", "Job created successfully");
            return ResponseEntity.ok(resp);
        }
        resp.put("state", "Failed");
        resp.put("msg", "UserType lacks permissions");
        return ResponseEntity.ok(resp);
    }

    //request to apply jobs(worked on postman)
    @PostMapping("/apply/jobs")
    public ResponseEntity<?> applyJob(@Valid @RequestBody JobApplicationModel applicationModel) {
        Map<String, String> resp = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        applicationModel.setUserId(customUserDetails.getId());
        applicationModel.setWorker_name(customUserDetails.getUserName());
        applicationModel.setApplication_status("pending");
        //check if user is employee here
        if (customUserDetails.getUserType().equalsIgnoreCase("employee")) {
            applicationRepository.save(applicationModel);
            resp.put("state", "success");
            resp.put("msg", "Job applied successfully");
            return ResponseEntity.ok(resp);
        }
        resp.put("state", "Failed");
        resp.put("msg", "UserType lacks permissions");
        return ResponseEntity.ok(resp);
    }

    //request to add worker earnings
    @PostMapping("/earnings")
    public ResponseEntity<?> updateWorkerEarnings(@Valid @RequestBody EmployeeEarningModel employeeEarningModel) {
        Map<String, String> resp = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        employeeEarningModel.setUserId(customUserDetails.getId());
        employeeEarningModel.setEmployee_name(customUserDetails.getUserName());
        //check if user is employee here
        if (customUserDetails.getUserType().equalsIgnoreCase("employee")) {
            earningsRepository.save(employeeEarningModel);
            resp.put("state", "success");
            resp.put("msg", "Job earning updated");
            return ResponseEntity.ok(resp);
        }
        resp.put("state", "Failed");
        resp.put("msg", "UserType lacks permissions");
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/my-jobs{id}")
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
        jobModel1.setEmployerName(jobModel.getEmployerName());
        jobModel1.setEmployerPhone(jobModel.getEmployerPhone());
        jobModel1.setJobAmount(jobModel.getJobAmount());
        jobModel1.setJobDeadline(jobModel.getJobDeadline());
        jobModel1.setEmployerId(customUserDetails.getId());
        jobRepository.save(jobModel1);
        resp.put("state", "success");
        resp.put("msg", "Job updated successfully");
        return ResponseEntity.ok(resp);
    }

    //update the worker total earnings
    @PutMapping("/worker/earnings/{id}")
    public ResponseEntity<?> updateEarnings(@PathVariable long id) {
        Map<String, String> resp = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        WorkerProfile workerProfile = workersRepository.findById(id).orElse(null);
        if (workerProfile == null) {
            resp.put("state", "danger");
            resp.put("msg", "The user does not a applications with that id");
            return ResponseEntity.ok(resp);
        }
        workerProfile.setUserId(customUserDetails.getId());
        workerProfile.setEarnings(jobRepository.totalEarnings(customUserDetails.getId()));
        workersRepository.save(workerProfile);
        resp.put("state", "success");
        resp.put("msg", "Worker earnings updated");
        return ResponseEntity.ok(resp);
    }

    //Update the Job Status depending with the action
    @PutMapping("/my-jobs/status/{id}")
    public ResponseEntity<?> visibleJob(@PathVariable long id,@RequestParam(value = "status") String status) {
        Map<String, String> resp = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        JobModel jobModel = jobRepository.findById(id).orElse(null);
        if (jobModel == null) {
            resp.put("state", "danger");
            resp.put("msg", "No job found with that id");
            return ResponseEntity.ok(resp);
        }
        jobModel.setJob_status(status);
        //jobModel.setEmployerId(customUserDetails.getId());
        jobRepository.save(jobModel);
        resp.put("state", "success");
        resp.put("msg", "state updated successfully");
        return ResponseEntity.ok(resp);
    }

    //api to update the job payment status to paid used by admin.
    @PutMapping("/my-jobs/payment/{id}")
    public ResponseEntity<?> updateJobPaymentStatus(@PathVariable long id){
        Map<String, String> resp = new HashMap<>();
       CustomUserDetails customUserDetails = (CustomUserDetails)
               SecurityContextHolder.getContext()
               .getAuthentication().getPrincipal();
       JobModel jobModel = jobRepository.findById(id).orElse(null);
       if (jobModel == null){
           resp.put("state", "danger");
           resp.put("msg", "No Job found with that id");
           return ResponseEntity.ok(resp);
       }
       jobModel.setPayment_status("paid");
       jobRepository.save(jobModel);
       resp.put("state", "Success");
       resp.put("msg", "Payment Status updated");
       return ResponseEntity.ok(resp);
    }

    //admin or job automatically revokes it after a certain duration
    @PutMapping("/my-jobs/revoke/{id}")
    public ResponseEntity<?> revokeJobPaymentStatus(@PathVariable long id){
        Map<String, String> resp = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        JobModel jobModel = jobRepository.findById(id).orElse(null);
        if (jobModel == null){
            resp.put("state", "danger");
            resp.put("msg", "No Job found with that id");
            return ResponseEntity.ok(resp);
        }
        jobModel.setPayment_status("unpaid");
        jobRepository.save(jobModel);
        resp.put("state", "Success");
        resp.put("msg", "Payment Status revoked");
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/my-jobs/favourite/{id}")
    public ResponseEntity<?> addJobFavourite(@PathVariable long id){
        Map<String, String> resp = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        JobModel jobModel = jobRepository.findById(id).orElse(null);
        if (jobModel == null){
            resp.put("state", "danger");
            resp.put("msg", "No Job found with that id");
            return ResponseEntity.ok(resp);
        }
        jobModel.setFavourite("true");
        jobRepository.save(jobModel);
        resp.put("state", "Success");
        resp.put("msg", "Job added to favourite");
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/applications/status/{id}")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable long id){
        Map<String, String> resp = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        JobApplicationModel jobApplicationModel = applicationRepository.findById(id).orElse(null);
        if (jobApplicationModel == null){
            resp.put("state", "danger");
            resp.put("msg", "No Job found with that id");
            return ResponseEntity.ok(resp);
        }
        jobApplicationModel.setApplication_status("Approved");
        applicationRepository.save(jobApplicationModel);
        resp.put("state", "Success");
        resp.put("msg", "Job application approved");
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
        List<JobModel> jobModels = jobRepository.findNearbyJobs(location);
        return ResponseEntity.ok(jobModels);
    }

    //get the jobs by a favourite jobs
    @GetMapping("/favourite-jobs")
    public ResponseEntity<List<JobModel>> FavouriteJobs(@RequestParam(value = "favourite") String favourite) {
        CustomUserDetails customUserDetails = (CustomUserDetails)
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<JobModel> jobModels = jobRepository.findFavouriteJobs(favourite,customUserDetails.getId());
        return ResponseEntity.ok(jobModels);
    }

    //get the jobs based on a specific status
    @GetMapping("/status-jobs")
    public ResponseEntity<List<JobModel>> getStatusJobs(@RequestParam(value = "payment_status") String payment_status,
                                                   @RequestParam("job_status") String job_status){
        List<JobModel> jobModels = jobRepository.findPaidAndAvailableJobs(payment_status,job_status);
        return ResponseEntity.ok(jobModels);
    }

    /*//get the jobs that have already been applied
    @GetMapping("/jobs/Active")
    public ResponseEntity<List<JobApplicationModel>> getActiveJobs() {
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<JobApplicationModel> jobApplicationModels = applicationRepository.findByUserId(customUserDetails.getId());
        return ResponseEntity.ok(jobApplicationModels);
    }*/

    //get the jobs that have already been marked as approved
    @GetMapping("/jobs/approved")
    public ResponseEntity<List<JobApplicationModel>> getComplete() {
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<JobApplicationModel> jobApplicationModels = applicationRepository.findApprovedJobs(customUserDetails.getId());
        return ResponseEntity.ok(jobApplicationModels);
    }
    //get jobs
    @GetMapping("/jobs")
    public ResponseEntity<List<JobModel>> getAllJobs() {
        List<JobModel> jobModels = jobRepository.findAll();
        return ResponseEntity.ok(jobModels);
    }

   /* //get the todos where status = pending
    @GetMapping("/jobs-pending")
    public ResponseEntity<List<JobModel>> jobsPending() {
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        List<JobModel> jobModels = jobRepository.findByPendingStatus();
        return ResponseEntity.ok(jobModels);
    }*/

    //Query all jobs count
    @GetMapping("/JobsNumber")
    public long getAllJobsCount(){
        return jobRepository.countAllBy();
    }

    //get the average rating of a worker
    @GetMapping("/averageRating")
    public double getAverageRating(@RequestParam(value = "worker_id") String worker_id){
        return jobRepository.averageRating(worker_id);
    }

    @GetMapping("/paidJobsNumber")
    public long getPaidJobsCount(){
       return jobRepository.countByPaid();
    }

    @GetMapping("/unpaidJobsNumber")
    public long getUnPaidJobsCount(){
        return jobRepository.countByUnPaid();
    }

    @GetMapping("/employees")
    public long getEmployeeCount(){
        return jobRepository.countByEmployee();
    }

    @GetMapping("/employers")
    public long getEmployerCount(){
        return jobRepository.countByEmployer();
    }

}
