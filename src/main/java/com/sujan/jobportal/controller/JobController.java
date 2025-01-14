package com.sujan.jobportal.controller;

import com.sujan.jobportal.dto.JobDto;
import com.sujan.jobportal.exception.ResourceNotFoundException;
import com.sujan.jobportal.model.Job;
import com.sujan.jobportal.reponse.ApiResponse;
import com.sujan.jobportal.request.JobPostRequest;
import com.sujan.jobportal.service.job.IJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/jobs")
@RequiredArgsConstructor
public class JobController {

    private final IJobService iJobService;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchJobs(@RequestParam String query){
        try {
            List<Job> jobList= iJobService.searchJobs(query);
            List<JobDto> jobDtoList=iJobService.convertAllJobsToDto(jobList);
            return ResponseEntity.ok(new ApiResponse("Search success!",jobDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllJobs(){
        try {
            List<Job> jobList=iJobService.getAllJobs();
            List<JobDto> jobDtoList=iJobService.convertAllJobsToDto(jobList);
            return ResponseEntity.ok(new ApiResponse("Success!",jobDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("{jobId}/job")
    public ResponseEntity<ApiResponse> getJobById(@PathVariable Integer jobId){
        try {
            Job job=iJobService.getJobById(jobId);
            JobDto jobDto=iJobService.convertToDto(job);
            return ResponseEntity.ok(new ApiResponse("success!",jobDto));
        } catch (ResourceNotFoundException e) {
           return ResponseEntity
                   .status(HttpStatus.NOT_FOUND)
                   .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/post-job")
    public ResponseEntity<ApiResponse>  postJob(@RequestBody JobPostRequest request
            , @RequestParam Integer employerId){

        try {
            Job job=iJobService.saveJob(request,employerId);
            JobDto jobDto=iJobService.convertToDto(job);
            return ResponseEntity
                    .ok(new ApiResponse("Job post success!",jobDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{jobId}/delete")
    public ResponseEntity<ApiResponse>  deleteJobById(@PathVariable Integer jobId){
        try {
            iJobService.deletedJobById(jobId);
            return ResponseEntity.ok(new ApiResponse("Deleted!",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }   
    }
}
