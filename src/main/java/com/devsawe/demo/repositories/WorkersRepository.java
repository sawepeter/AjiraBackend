package com.devsawe.demo.repositories;

import com.devsawe.demo.entities.WorkerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkersRepository extends JpaRepository<WorkerProfile, Long> {

    List<WorkerProfile> findByUserId(Long userId);
}
