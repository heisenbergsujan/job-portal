package com.sujan.jobportal.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployerRegisterRequest {
    private String companyName;
    private String email;
    private String phoneNo;
    private String password;
}
