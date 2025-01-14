package com.sujan.jobportal.repository;

import com.sujan.jobportal.model.Job;
import com.sujan.jobportal.model.JobApplication;
import com.sujan.jobportal.model.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication,Integer> {
    boolean existsByJobSeekerAndJob(JobSeeker jobSeeker, Job jobPost);
}
