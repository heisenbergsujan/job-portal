package com.sujan.jobportal.repository;

import com.sujan.jobportal.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job,Integer> {
    Optional<Job> findByEmployerId(Integer employerId);

    List<Job> findByTitleContainingOrLocationContaining(String query, String query1);
}
