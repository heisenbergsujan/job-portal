package com.sujan.jobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResumeDto {
    private Integer id;
    private String fileName;
    private String downloadUrl;
}
