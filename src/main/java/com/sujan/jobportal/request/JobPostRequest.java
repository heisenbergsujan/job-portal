package com.sujan.jobportal.request;

import com.sujan.jobportal.enums.EmployeeType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class JobPostRequest {
    private String title;
    private int numberOfOpenings;
    private String jobDescription;
    private String jobSpecification;
    private List<String> skills;
    private String location;
    private EmployeeType employeeType;
    private String category;
    private String company;
    private String salary;
    private boolean negotiable;
}
