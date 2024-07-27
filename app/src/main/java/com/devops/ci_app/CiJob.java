package com.devops.ci_app;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="CiJob")
public class CiJob {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="job_name", length=50, nullable=false, unique = true)
    private String jobName;

    @Column(name="status", length=50, nullable=false)
    private String status;

    @Column(name="created_at", nullable=false)
    private Date createdAt;

    @Column(name="updated_at", nullable=false)
    private Date updatedAt;

    @Column(name="job_type", length=50, nullable=false)
    private String jobType;

    public CiJob(String jobName, String status, Date createdAt, Date updatedAt, String jobType) {
        this.jobName = jobName;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.jobType = jobType;
    }

    public CiJob(Long jobId, String jobName, String status, Date createdAt, Date updatedAt, String jobType) {
        this.jobName = jobName;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.jobType = jobType;
    }

    public CiJob() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
}
