package com.sujan.jobportal.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobSeekerRegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNo;
}
