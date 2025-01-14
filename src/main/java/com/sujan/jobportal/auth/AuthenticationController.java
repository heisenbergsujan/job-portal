package com.sujan.jobportal.auth;

import com.sujan.jobportal.reponse.AuthResponse;
import com.sujan.jobportal.request.AuthRequest;
import com.sujan.jobportal.request.EmployerRegisterRequest;
import com.sujan.jobportal.request.JobSeekerRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody AuthRequest request
    ){
       return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/register/job-seeker")
    public ResponseEntity<AuthResponse> registerJobSeeker(
            @RequestBody JobSeekerRegisterRequest request
    ){
        System.out.println(request);
     return ResponseEntity.ok(authenticationService.registerJobSeeker(request));
    }

    @PostMapping("/register/employer")
    public ResponseEntity<AuthResponse> registerEmployers(
            @RequestBody EmployerRegisterRequest request
    ){
      return ResponseEntity.ok(authenticationService.registerEmployer(request));
    }

}
