package com.sujan.jobportal.controller;

import com.sujan.jobportal.service.jobseeker.IJobSeekerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JobSeekerController {
    private final IJobSeekerService iJobSeekerService;
}
