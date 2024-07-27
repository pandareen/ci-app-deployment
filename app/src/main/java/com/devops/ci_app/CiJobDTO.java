package com.devops.ci_app;


import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CiJobDTO {

    private @NonNull String jobName;

    private String status;

    private Date createdAt;

    private Date updatedAt;

    private String jobType;

    public CiJobDTO(String jobName, String status, Date createdAt, Date updatedAt, String jobType) {
        this.jobName = jobName;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.jobType = jobType;
    }

}
