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

    //get the count of paid jobs
    @Query(value = "SELECT COUNT(d.id) FROM jobs d WHERE d.payment_status = 'paid'", nativeQuery = true)
    long countByPaid();

    //get the total earnings of a particular worker
    @Query(value = "SELECT SUM(m.earnings) FROM earnings m WHERE m.worker_id = :worker_id", nativeQuery = true)
    double totalEarnings(@Param("worker_id") Long worker_id);

    //get the count of unpaid jobs
    @Query(value = "SELECT COUNT(d.id) FROM jobs d WHERE d.payment_status = 'unpaid'", nativeQuery = true)
    long countByUnPaid();

    //get the average rating of a Worker
    @Query(value = "SELECT AVG(r.rating) FROM rating r WHERE r.worker_id = :worker_id", nativeQuery = true)
    double averageRating(@Param("worker_id") String worker_id);

    //get the count of all jobs
    @Query(value = "SELECT COUNT(id) FROM jobs", nativeQuery = true)
    long countAllBy();

    //get the count of all employees
    @Query(value = "SELECT COUNT(id) FROM users  u WHERE u.user_type = 'employee'", nativeQuery = true)
    long countByEmployee();

    //get the count of all employers
    @Query(value = "SELECT COUNT(id) FROM users u WHERE u.user_type = 'employer'", nativeQuery = true)
    long countByEmployer();

    @Query(value = "SELECT d.* FROM todo d WHERE DATE_FORMAT(d.created_at, '%Y-%m-%d') = :date AND d.user_id = :id", nativeQuery = true)
    List<JobModel> findByCreatedAt(@Param("id") Long id, @Param("date") String date);

    @Query(value = "SELECT d.* FROM jobs d WHERE d.favourite = :favourite AND d.employer_id = :employer_id", nativeQuery = true)
    List<JobModel> findFavouriteJobs(@Param("favourite") String favourite, @Param("employer_id") Long employer_id);

    @Query(value = "SELECT d.* FROM jobs d WHERE d.job_location = :location", nativeQuery = true)
    List<JobModel> findNearbyJobs(@Param("location") String location);

    @Query(value = "SELECT d.* FROM jobs d WHERE d.payment_status = :payment_status AND d.job_status = :job_status", nativeQuery = true)
    List<JobModel> findPaidAndAvailableJobs(@Param("payment_status") String payment_status, @Param("job_status") String job_status);

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
