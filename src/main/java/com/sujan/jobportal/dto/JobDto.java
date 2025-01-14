package com.sujan.jobportal.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class JobDto {
        private Integer id;
        private String title;
        private int numberOfOpenings;
        private String jobDescription;
        private String jobSpecification;
        private List<String> skills;
        private String location;
        private String employeeType;
        private String category;
        private String company;
        private String salary;
        private boolean negotiable;
        private LocalDateTime createAt;
        private LocalDateTime updatedAt;
        private boolean isActive;
        private EmployerDto employer;
}
