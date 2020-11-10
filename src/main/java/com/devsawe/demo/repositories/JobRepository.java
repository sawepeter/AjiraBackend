package com.devsawe.demo.repositories;

import com.devsawe.demo.entities.JobModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<JobModel, Long> {

    Page<JobModel> findByEmployerId(Long employerId, Pageable pageable);

    List<JobModel> findByEmployerId(Long employerId);

    Optional<JobModel> findByIdAndUserId(Long id, Long UserId);

    @Query(value = "SELECT d.* FROM todo d WHERE DATE_FORMAT(d.created_at, '%Y-%m-%d') = :date AND d.user_id = :id", nativeQuery = true)
    List<JobModel> findByCreatedAt(@Param("id") Long id, @Param("date") String date);

    @Query(value = "SELECT d.* FROM jobs d WHERE d.job_location = :location", nativeQuery = true)
    List<JobModel> findNearbyJobs(@Param("location") String location);

    @Query(value = "SELECT d.* FROM jobs", nativeQuery = true)
    List<JobModel> findAllJobs();

    @Query(value = "SELECT d.* FROM jobs d WHERE d.status = 'pending'", nativeQuery = true)
    List<JobModel> findByPendingStatus();

    @Query(value = "SELECT d.* FROM jobs d WHERE d.status = 'done'", nativeQuery = true)
    List<JobModel> findByCompletedStatus();

   /* //query for today
    @Query(value = "SELECT todotitle,todotime FROM todo d WHERE DATE_FORMAT(d.created_at, '%Y-%m-%d') = :date AND d.user_id = :id", nativeQuery = true)
    List<Map<String, String>> findByCreatedAt(@Param("id") Long id, @Param("date") String date);*/


   /* @Query(value = "SELECT todotitle,todotime FROM todo d WHERE DATE_FORMAT(d.created_at, '%Y-%m-%d') => DATE_SUB(CURDATE(), INTERVAL 7 DAY) AND d.user_id = :id", nativeQuery = true)
    List<Map<String, String>> findByWeek(@Param("id") Long id);*/

}
