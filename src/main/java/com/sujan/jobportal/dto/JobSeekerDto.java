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
public class JobSeekerDto extends UserDto {
    private String firstName;
    private String lastName;
    private ResumeDto resumeDto;
    private List<String> applications;
}
