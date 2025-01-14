package com.sujan.jobportal.service.jobseeker;

import com.sujan.jobportal.exception.ResourceNotFoundException;
import com.sujan.jobportal.model.JobSeeker;
import com.sujan.jobportal.repository.JobSeekerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobSeekerService implements IJobSeekerService{
    private final JobSeekerRepository jobSeekerRepository;


    @Override
    public JobSeeker getJobSeekerById(Integer jobSeekerId) {
        return jobSeekerRepository
                .findById(jobSeekerId)
                .orElseThrow(()->new ResourceNotFoundException("Job Seeker not found!"));
    }
}
