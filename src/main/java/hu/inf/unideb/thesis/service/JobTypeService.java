package hu.inf.unideb.thesis.service;


import hu.inf.unideb.thesis.entity.JobType;

import java.util.List;

public interface JobTypeService {

    JobType findById(Long id);

    JobType findByName(String name);

    List<JobType> findAll();

    void save(JobType jobType);

    void update(Long id, JobType jobType);

    void delete(JobType jobType);

    void setUpMockedData();

}
