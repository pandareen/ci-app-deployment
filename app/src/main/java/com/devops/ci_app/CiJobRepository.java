package com.devops.ci_app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CiJobRepository extends JpaRepository<CiJob, Long> {

    @Query("select u from CiJob u where u.jobName = ?1")
    CiJob findByName(String jobName);

    @Query("select u from CiJob u where u.jobType = ?1")
    List<CiJob> findByType(String jobType);

    @Query("select u from CiJob u where u.status = ?1")
    List<CiJob> findByStatus(String jobStatus);

    @Query("select u from CiJob u where u.createdAt between ?1 and ?2")
    List<CiJob> findByDateRange(Date startDate, Date endDate);
}
