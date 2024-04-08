package hu.inf.unideb.thesis.dao;

import hu.inf.unideb.thesis.entity.JobType;
import hu.inf.unideb.thesis.repositories.JobTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobTypeDAO implements DAO<JobType>{

    @Autowired
    JobTypeRepository jobTypeRepository;


    @Override
    public JobType findById(long id) {
        if (jobTypeRepository.findById(id).isPresent()){
            return jobTypeRepository.findById(id).get();
        }
        else {
            return null;
        }
    }

    public JobType findByName(String name){
        if (jobTypeRepository.findByName(name) != null){
            return jobTypeRepository.findByName(name);
        }
        else{
            return null;
        }
    }

    @Override
    public List<JobType> getAll() {
        return jobTypeRepository.findAll();
    }

    @Override
    public JobType save(JobType jobType) {
        return jobTypeRepository.save(jobType);
    }

    @Override
    public JobType update(JobType jobType) {
        return jobTypeRepository.save(jobType);
    }

    @Override
    public void delete(long id) {
        jobTypeRepository.deleteById(id);
    }
}
