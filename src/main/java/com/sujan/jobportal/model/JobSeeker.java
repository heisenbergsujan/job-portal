package com.sujan.jobportal.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
public class JobSeeker extends User{

    private String firstName;

    private String lastName;

    @OneToOne(mappedBy = "jobSeeker", cascade = CascadeType.ALL,orphanRemoval = true)
    private Resume resume;

    @OneToMany(mappedBy = "jobSeeker",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<JobApplication>  applications;
}
