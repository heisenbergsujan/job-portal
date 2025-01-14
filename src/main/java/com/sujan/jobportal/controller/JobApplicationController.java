package com.sujan.jobportal.controller;

import com.sujan.jobportal.exception.ResourceNotFoundException;
import com.sujan.jobportal.reponse.ApiResponse;
import com.sujan.jobportal.service.jobapplication.IJobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/job-applications")
@RequiredArgsConstructor
public class JobApplicationController {

    private final IJobApplicationService iJobApplicationService;

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse> applyForJob(
            @RequestParam Integer jobSeekerId,
            @RequestParam Integer jobId
            ){

        try {
           String response= iJobApplicationService.applyForJob(jobSeekerId,jobId);
            return ResponseEntity
                    .ok(new ApiResponse(response,
                            null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }
}
