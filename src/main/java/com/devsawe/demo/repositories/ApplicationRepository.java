package com.devsawe.demo.repositories;

import com.devsawe.demo.entities.JobApplicationModel;
import com.devsawe.demo.entities.JobModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<JobApplicationModel, Long> {

    //get the deadline date
    @Query(value = "SELECT d.job_deadline FROM jobs WHERE d.job_post_id = :id", nativeQuery = true)
    String findByJobDeadline(@Param("id") Long job_post_id);

    List<JobApplicationModel> findByUserId(Long userId);

    @Query(value = "SELECT d.job_id FROM jobs_application d", nativeQuery = true)
    List<JobApplicationModel> findByJob_id(Long job_id);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE jobs_application d SET d.application_status = 'Approved' WHERE d.id = :id", nativeQuery = true)
    void updateStatus(@Param("id") Long id);

    @Query(value = "SELECT d.* FROM jobs_application d WHERE d.application_status = 'Approved'", nativeQuery = true)
    List<JobApplicationModel> findApprovedJobs(Long userId);

    /*//inner join query
    @Query(value = "SELECT d.* FROM jobs d INNER JOIN jobs_application j on d.id=j.id WHERE d.id = :id", nativeQuery = true);
    List<JobModel> findAppliedJobs();*/




}
