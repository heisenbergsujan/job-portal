package com.sujan.jobportal.controller;

import com.sujan.jobportal.dto.ResumeDto;
import com.sujan.jobportal.exception.ResourceNotFoundException;
import com.sujan.jobportal.model.Resume;
import com.sujan.jobportal.reponse.ApiResponse;
import com.sujan.jobportal.service.resume.IResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/resume")
public class ResumeController {
    private final IResumeService iResumeService;

    @GetMapping("/{resumeId}/download")
    public ResponseEntity<Resource> downloadResume(@PathVariable Integer resumeId){
        try {
            Resume resume=iResumeService.getResumeById(resumeId);
            ByteArrayResource resource=new ByteArrayResource(resume
                    .getResume()
                    .getBytes(1,(int)resume.getResume().length()));
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType(resume.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\""+resume.getFileName()+"\"")
                    .body(resource);
        } catch (Exception e) {
           return ResponseEntity
                   .status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(null);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveResume(
            @RequestParam MultipartFile resumeFile,
            @RequestParam Integer jobSeekerId
            ){
        try {
            Resume resume=iResumeService.saveResume(resumeFile,jobSeekerId);
            ResumeDto resumeDto=iResumeService.convertToDto(resume);
            return ResponseEntity
                    .ok(new ApiResponse("Upload success!",resumeDto));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{resumeId}/delete")
    public ResponseEntity<ApiResponse> deleteResume(@PathVariable Integer resumeId){
        try {
            iResumeService.deleteResumeById(resumeId);
            return ResponseEntity.ok(new ApiResponse("Resume Deleted!",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{resumeId}/update")
    public ResponseEntity<ApiResponse> updateResume(
            @RequestParam MultipartFile resumeFile,
            @PathVariable Integer resumeId
    ){
        try {
            iResumeService.updateResume(resumeFile,resumeId);
            return ResponseEntity
                    .ok(new ApiResponse("resume update success",null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }
}
