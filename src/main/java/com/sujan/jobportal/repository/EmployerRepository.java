package com.sujan.jobportal.repository;

import com.sujan.jobportal.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer,Integer> {
}
