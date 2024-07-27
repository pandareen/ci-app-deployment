package com.devops.ci_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@EnableWebMvc
@RequestMapping("/api")
public class CiJobController {

    @Autowired
    CiJobService ciJobService;

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @PutMapping(path = "/jobs", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CiJob> addJob(@RequestBody CiJobDTO ciJobDTO) {
        return new ResponseEntity<>(ciJobService.addJob(ciJobDTO.getJobName(), ciJobDTO.getStatus(), ciJobDTO.getCreatedAt(), ciJobDTO.getUpdatedAt(), ciJobDTO.getJobType()), HttpStatus.OK);
    }

    @PostMapping(path = "/jobs/{id}")
    public ResponseEntity<CiJob> updateJob(@PathVariable("id") Long jobId, @RequestBody CiJobDTO ciJobDTO) {
        CiJob ciJob = new CiJob(ciJobDTO.getJobName(), ciJobDTO.getStatus(), ciJobDTO.getCreatedAt(), ciJobDTO.getUpdatedAt(), ciJobDTO.getJobType());
        ciJob.setId(jobId);

        try {
            return new ResponseEntity<>(ciJobService.updateJob(ciJob), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/jobs")
    public ResponseEntity<List<CiJob>> getAllJobs() {
        try {
            return new ResponseEntity<>(ciJobService.getAllJobs(), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/jobs/{id}")
    public ResponseEntity<String> removeJob(@PathVariable("id") Long id) {
        try {
            ciJobService.removeJob(id);
            return new ResponseEntity<>("Delete Successful", HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Delete Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(path = "/jobs/{id}")
    public ResponseEntity<CiJob> getJobById(@PathVariable("id") Long id) {
        try {
            Optional<CiJob> ciJob = ciJobService.getJobById(id);
            return ciJob.map(job -> new ResponseEntity<>(job, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(path = "/jobs/status/{status}")
    public ResponseEntity<List<CiJob>> getJobsByStatus(@PathVariable("status") String status) {
        try {
            return new ResponseEntity<>(ciJobService.getJobsByStatus(status), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/jobs/jobType/{jobType}")
    public ResponseEntity<List<CiJob>> getJobsByJobType(@PathVariable("jobType") String jobType) {
        try {
            return new ResponseEntity<>(ciJobService.getJobsByJobType(jobType), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/jobs/date-range")
    public ResponseEntity<List<CiJob>> getJobsByJobType(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            return new ResponseEntity<>(ciJobService.getJobsByDateRange(startDate, endDate), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
