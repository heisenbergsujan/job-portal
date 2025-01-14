package com.sujan.jobportal.service.resume;

import com.sujan.jobportal.dto.ResumeDto;
import com.sujan.jobportal.exception.ResourceNotFoundException;
import com.sujan.jobportal.model.JobSeeker;
import com.sujan.jobportal.model.Resume;
import com.sujan.jobportal.repository.JobSeekerRepository;
import com.sujan.jobportal.repository.ResumeRepository;
import com.sujan.jobportal.service.jobseeker.IJobSeekerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class ResumeService implements IResumeService{
    private final ModelMapper mapper;
    private final IJobSeekerService iJobSeekerService;
    private final ResumeRepository resumeRepository;

    @Override
    public Resume saveResume(MultipartFile resumeFile, Integer jobSeekerId)
             {
                     JobSeeker jobSeeker=
                             iJobSeekerService.getJobSeekerById(jobSeekerId);
                 try {
                     Resume   resume = new Resume();
                     String downloadUrl="api/v1/resume/"+resume.getId()+"/download";
                     resume.setDownloadUrl(downloadUrl);
                     resume.setFileName(resumeFile.getOriginalFilename());
                     resume.setFileType(resumeFile.getContentType());
                     resume.setResume(new SerialBlob(resumeFile.getBytes()));
                     resume.setJobSeeker(jobSeeker);
                     return resumeRepository.save(resume);
                 } catch (SQLException|IOException e) {
                     throw new RuntimeException(e.getMessage());
                 }
    }

    @Override
    public Resume getResumeById(Integer resumeId) {
        return resumeRepository
                .findById(resumeId)
                .orElseThrow(()->new ResourceNotFoundException("Resume not found!"));
    }

    @Override
    public void deleteResumeById(Integer resumeId) {
       resumeRepository.findById(resumeId)
               .ifPresentOrElse(resumeRepository::delete,()->{
                   throw new ResourceNotFoundException("Resume not found!");
               });
    }

    @Override
    public void updateResume(MultipartFile resumeFile, Integer resumeId) {
            Resume resume = this.getResumeById(resumeId);
        try {
            resume.setResume(new SerialBlob(resumeFile.getBytes()));
            resume.setFileName(resumeFile.getOriginalFilename());
            resume.setFileType(resumeFile.getContentType());
            resumeRepository.save(resume);
        } catch (SQLException|IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ResumeDto convertToDto(Resume resume) {
        return this.mapper.map(resume,ResumeDto.class);
    }
}
