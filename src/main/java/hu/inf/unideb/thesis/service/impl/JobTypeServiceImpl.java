package hu.inf.unideb.thesis.service.impl;

import hu.inf.unideb.thesis.dao.JobTypeDAO;
import hu.inf.unideb.thesis.entity.JobType;
import hu.inf.unideb.thesis.repositories.JobTypeRepository;
import hu.inf.unideb.thesis.service.JobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing jobType-related operations.
 */
@Service
public class JobTypeServiceImpl implements JobTypeService {

    @Autowired
    JobTypeDAO jobTypeDAO;

    /**
     * Retrieve a jobType by their unique identifier.
     * @param id The ID of the jobType.
     * @return The jobType object if found, otherwise null.
     */
    @Override
    public JobType findById(Long id) {
        return jobTypeDAO.findById(id);
    }

    /**
     * Retrieve all jobTypes from the database.
     * @return a List of jobType objects.
     */
    @Override
    public List<JobType> findAll() {
        return jobTypeDAO.getAll();
    }

    /**
     * Save a jobType object into the database.
     * @param jobType The jobType object to be saved.
     * @return The jobType that was saved.
     */
    @Override
    public JobType save(JobType jobType) {
        return jobTypeDAO.save(jobType);
    }

    /**
     * Update a jobType object in the database.
     * @param id The ID of the job type.
     * @param jobType The jobType object to be updated.
     * @return The jobType that was updated.
     */
    @Override
    public JobType update(Long id, JobType jobType) {
        if (jobTypeDAO.findById(id) != null){
            return jobTypeDAO.update(jobType);
        }
        else {
            throw new RuntimeException("Could not find Job Type");
        }

    }

    /**
     * Delete a jobType from the database based on the given ID.
     * @param jobType The object to be deleted.
     */
    @Override
    public void delete(JobType jobType) {
        jobTypeDAO.delete(jobType.getId());
    }

    /**
     * Retrieve a jobType by their name.
     * @param name The name of the jobType.
     * @return The jobType object if found.
     */
    @Override
    public JobType findByName(String name) {
        return jobTypeDAO.findByName(name);
    }

    /**
     * Sets up predefined data for the project.
     */
    @Override
    public void setUpMockedData() {
        jobTypeDAO.setUpMockedData();
    }
}
