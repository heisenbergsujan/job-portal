package com.sujan.jobportal.service.job;

import com.sujan.jobportal.dto.JobDto;
import com.sujan.jobportal.model.Job;
import com.sujan.jobportal.request.JobPostRequest;

import java.util.List;

public interface IJobService {
    Job saveJob(JobPostRequest request,Integer employerId);
    JobDto convertToDto(Job job);

    List<JobDto> convertAllJobsToDto(List<Job> jobs);

    void deletedJobById(Integer jobPostId);

    List<Job> getAllJobs();

    List<Job> searchJobs(String query);

    Job getJobById(Integer jobId);
}
