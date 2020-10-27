package com.devsawe.demo.repositories;

import com.devsawe.demo.entities.EmployerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployerRepository extends JpaRepository<EmployerProfile, Long> {

    List<EmployerProfile> findByUserId(Long id);

}
