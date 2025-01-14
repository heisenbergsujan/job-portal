package com.sujan.jobportal.model;

import com.sujan.jobportal.enums.EmployeeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Job {
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private int numberOfOpenings;
    private String jobDescription;
    private String jobSpecification;
    private List<String> skills;
    private String location;

    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType;

    private String category;
    private String company;
    private String salary;
    private boolean negotiable;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private Employer employer;

//    @ManyToMany(mappedBy = "appliedJobs")
//    private List<JobSeeker> jobSeekers;

    @OneToMany(mappedBy = "job",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<JobApplication> applications=new ArrayList<>();
}
