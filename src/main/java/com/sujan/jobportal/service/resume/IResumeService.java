package com.sujan.jobportal.service.resume;

import com.sujan.jobportal.dto.ResumeDto;
import com.sujan.jobportal.model.Resume;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

public interface IResumeService {
    Resume saveResume(MultipartFile resumeFile, Integer jobSeekerId);
    Resume getResumeById(Integer resumeId);
    void deleteResumeById(Integer resumeId);
    void updateResume(MultipartFile resumeFile,Integer resumeId);

    ResumeDto convertToDto(Resume resume);
}
