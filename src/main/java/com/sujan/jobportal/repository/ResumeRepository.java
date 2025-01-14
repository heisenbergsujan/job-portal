package com.sujan.jobportal.repository;

import com.sujan.jobportal.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume,Integer> {
}
