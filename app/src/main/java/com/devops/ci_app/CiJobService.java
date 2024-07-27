package com.devops.ci_app;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CiJobService {

    private final CiJobRepository ciJobRepository;

    public CiJobService(CiJobRepository ciJobRepository) {
        this.ciJobRepository = ciJobRepository;
    }

    @Transactional
    public CiJob addJob(String jobName, String status, Date createdAt, Date updatedAt, String jobType) {
        return ciJobRepository.save(new CiJob(jobName, status, createdAt, updatedAt, jobType));
    }

    @Transactional
    public void removeJob(Long jobId) {
        ciJobRepository.deleteById(jobId);
    }

    @Transactional
    public List<CiJob> getAllJobs() {
        return ciJobRepository.findAll();
    }

    @Transactional
    public Optional<CiJob> getJobById(Long jobId) {
        return ciJobRepository.findById(jobId);
    }

    @Transactional
    public CiJob updateJob(CiJob job) {
        return ciJobRepository.save(job);
    }

    @Transactional
    public List<CiJob> getJobsByStatus(String status) {
        return ciJobRepository.findByStatus(status);
    }

    @Transactional
    public List<CiJob> getJobsByJobType(String jobType) {
        return ciJobRepository.findByType(jobType);
    }

    @Transactional
    public List<CiJob> getJobsByDateRange(Date startDate, Date endDate) {
        return ciJobRepository.findByDateRange(startDate, endDate);
    }

}
