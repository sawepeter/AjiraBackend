package com.devsawe.demo.repositories;

import com.devsawe.demo.entities.JobApplicationModel;
import com.devsawe.demo.entities.UserRatingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRatingRepository extends JpaRepository<UserRatingModel, Long> {

    List<UserRatingModel> findByUserId(Long userId);

}
