package com.sujan.jobportal.service.jobapplication;

import com.sujan.jobportal.enums.ApplicationStatus;
import com.sujan.jobportal.exception.AlreadyExistsException;
import com.sujan.jobportal.exception.ResourceNotFoundException;
import com.sujan.jobportal.model.Job;
import com.sujan.jobportal.model.JobApplication;
import com.sujan.jobportal.model.JobSeeker;
import com.sujan.jobportal.repository.JobApplicationRepository;
import com.sujan.jobportal.repository.JobRepository;
import com.sujan.jobportal.repository.JobSeekerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class JobApplicationService implements IJobApplicationService{

    private final JobApplicationRepository jobApplicationRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final JobRepository jobRepository;

    @Override
    public String applyForJob(Integer jobSeekerId, Integer jobId) {
        JobSeeker jobSeeker=jobSeekerRepository
                .findById(jobSeekerId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Job Seeker not found"));
        Job jobPost= jobRepository.findById(jobId)
                .orElseThrow(()->new ResourceNotFoundException("Job post not found"));
         boolean isApplicationExists=jobApplicationRepository
                .existsByJobSeekerAndJob(jobSeeker,jobPost);

         if(isApplicationExists){
             return "You have already applied for this job";
         }

        JobApplication application=JobApplication
                .builder()
                .jobSeeker(jobSeeker)
                .job(jobPost)
                .applicationDate(LocalDateTime.now())
                .status(ApplicationStatus.APPLIED)
                .build();
        jobApplicationRepository.save(application);
        return "Job application submitted successfully";
    }
}
