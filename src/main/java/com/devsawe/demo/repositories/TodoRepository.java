package com.devsawe.demo.repositories;

import com.devsawe.demo.entities.TodoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<TodoModel, Long> {
    Page<TodoModel> findByUserId(Long userId, Pageable pageable);
    List<TodoModel> findByUserId(Long userId);
    Optional<TodoModel> findByIdAndUserId(Long id, Long UserId);

    @Query(value = "SELECT d.* FROM todo d WHERE DATE_FORMAT(d.created_at, '%Y-%m-%d') = :date AND d.user_id = :id", nativeQuery = true)
    List<TodoModel> findByCreatedAt(@Param("id") Long id, @Param("date") String date);

    @Query(value = "SELECT d FROM todo d WHERE d.status = 'pending'", nativeQuery = true)
    List<TodoModel> findByPendingStatus(@Param("id") Long id);

    @Query(value = "SELECT d FROM todo d WHERE d.status = 'Completed'", nativeQuery = true)
    List<TodoModel> findByCompletedStatus(@Param("id") Long id);

   /* //query for today
    @Query(value = "SELECT todotitle,todotime FROM todo d WHERE DATE_FORMAT(d.created_at, '%Y-%m-%d') = :date AND d.user_id = :id", nativeQuery = true)
    List<Map<String, String>> findByCreatedAt(@Param("id") Long id, @Param("date") String date);*/


   /* @Query(value = "SELECT todotitle,todotime FROM todo d WHERE DATE_FORMAT(d.created_at, '%Y-%m-%d') => DATE_SUB(CURDATE(), INTERVAL 7 DAY) AND d.user_id = :id", nativeQuery = true)
    List<Map<String, String>> findByWeek(@Param("id") Long id);*/

}
