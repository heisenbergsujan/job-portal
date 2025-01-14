package com.sujan.jobportal.service.job;

import com.sujan.jobportal.dto.JobDto;
import com.sujan.jobportal.exception.ResourceNotFoundException;
import com.sujan.jobportal.model.Employer;
import com.sujan.jobportal.model.Job;
import com.sujan.jobportal.repository.EmployerRepository;
import com.sujan.jobportal.repository.JobRepository;
import com.sujan.jobportal.request.JobPostRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService implements IJobService{

    private  final JobRepository jobRepository;
    private final EmployerRepository employerRepository;
    private final ModelMapper mapper;

    @Override
    public Job saveJob(
            JobPostRequest request, Integer employerId
    ) {
       Employer employer = employerRepository
               .findById(employerId)
               .orElseThrow(()-> new ResourceNotFoundException("Employer not found"));

       var job=mapper.map(request,Job.class);

       job.setEmployer(employer);
       job.setCreateAt(LocalDateTime.now());
       job.setUpdatedAt(LocalDateTime.now());
       job.setActive(true);

       System.out.println(job);

       return jobRepository.save(job);
    }

    @Override
    public JobDto convertToDto(Job job){
        return mapper.map(job, JobDto.class);
    }

    @Override
    public List<JobDto> convertAllJobsToDto(List<Job> jobs){
        return jobs.stream().map(this::convertToDto).toList();
    }

    @Override
    public void deletedJobById(Integer jobPostId){
        jobRepository.findById(jobPostId)
                .ifPresentOrElse(jobRepository::delete,()->{
                    throw new ResourceNotFoundException("JobPost not found!");
                });
    }

    @Override
    public List<Job> getAllJobs(){
        return jobRepository.findAll();
    }

    @Override
    public List<Job> searchJobs(String query) {
        return jobRepository.findByTitleContainingOrLocationContaining(query,query);
    }

    @Override
    public Job getJobById(Integer jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(()->new ResourceNotFoundException("Job post not found"));
    }
}

