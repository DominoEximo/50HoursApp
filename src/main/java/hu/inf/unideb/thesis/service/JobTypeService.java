package hu.inf.unideb.thesis.service;


import hu.inf.unideb.thesis.entity.JobType;

import java.util.List;

public interface JobTypeService {

    JobType findById(Long id);

    JobType findByName(String name);

    List<JobType> findAll();

    JobType save(JobType jobType);

    JobType update(Long id, JobType jobType);

    void delete(JobType jobType);

    void setUpMockedData();

}
