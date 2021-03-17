package com.devsawe.demo.repositories;

import com.devsawe.demo.entities.EmployeeEarningModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EarningsRepository extends JpaRepository<EmployeeEarningModel, Long> {


}
