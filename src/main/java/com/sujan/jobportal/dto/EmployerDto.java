package com.sujan.jobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDto extends UserDto {
    private String companyName;
    private String companyWebsiteUrl;
    private String companyDescription;
    private String location;
    private String industry;
    private String companySize;
    private List<String> postedJobs;
}
