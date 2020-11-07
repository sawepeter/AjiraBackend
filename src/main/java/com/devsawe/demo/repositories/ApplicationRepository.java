package com.devsawe.demo.repositories;

import com.devsawe.demo.entities.JobApplicationModel;
import org.springframework.data.jpa.repository.JpaRepository;
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



}
