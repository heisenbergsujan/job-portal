package com.sujan.jobportal.model;

import com.sujan.jobportal.enums.CompanySize;
import jakarta.persistence.*;
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
    public class Employer extends User{

        private String companyName;

        private String companyWebsiteUrl;

        private String companyDescription;

        private String location;

        private String industry;

        @Enumerated(EnumType.STRING)
        private CompanySize companySize;

        @OneToMany(mappedBy = "employer",cascade = CascadeType.ALL,orphanRemoval = true)
        private List<Job> postedJobs;
    }
