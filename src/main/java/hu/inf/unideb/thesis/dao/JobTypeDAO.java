package hu.inf.unideb.thesis.dao;

import hu.inf.unideb.thesis.entity.JobType;
import hu.inf.unideb.thesis.repositories.JobTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Data Access Object (DAO) service for managing jobTypes in the database.
 */
@Component
public class JobTypeDAO implements DAO<JobType>{

    @Autowired
    JobTypeRepository jobTypeRepository;

    /**
     * Retrieve a jobType by their unique identifier.
     * @param id The ID of the jobType.
     * @return The jobType object if found, otherwise null.
     */
    @Override
    public JobType findById(long id) {
        if (jobTypeRepository.findById(id).isPresent()){
            return jobTypeRepository.findById(id).get();
        }
        else {
            return null;
        }
    }

    /**
     * Retrieve all jobTypes from the database.
     * @return a List of jobType objects.
     */
    @Override
    public List<JobType> getAll() {
        return jobTypeRepository.findAll();
    }

    /**
     * Save a jobType object into the database.
     * @param jobType The jobType object to be saved.
     * @return The jobType that was saved.
     */
    @Override
    public JobType save(JobType jobType) {
        return jobTypeRepository.save(jobType);
    }

    /**
     * Update a jobType object in the database.
     * @param jobType The jobType object to be updated.
     * @return The jobType that was updated.
     */
    @Override
    public JobType update(JobType jobType) {
        return jobTypeRepository.save(jobType);
    }

    /**
     * Delete a jobType from the database based on the given ID.
     * @param id The ID of the object to be deleted.
     */
    @Override
    public void delete(long id) {
        jobTypeRepository.deleteById(id);
    }

    /**
     * Retrieve a jobType by their name.
     * @param name The name of the jobType.
     * @return The jobType object if found.
     */
    public JobType findByName(String name){
        if (jobTypeRepository.findByName(name) != null){
            return jobTypeRepository.findByName(name);
        }
        else{
            return null;
        }
    }

    /**
     * Sets up predefined data for the project.
     */
    public void setUpMockedData(){
        if (getAll().isEmpty()){
            save(new JobType("test"));
            save(new JobType("test2"));
            save(new JobType("test3"));
            save(new JobType("test4"));
            save(new JobType("test5"));
            save(new JobType("test6"));
        }
    }
}
