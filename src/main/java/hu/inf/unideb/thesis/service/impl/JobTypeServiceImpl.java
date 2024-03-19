package hu.inf.unideb.thesis.service.impl;

import hu.inf.unideb.thesis.dao.JobTypeDAO;
import hu.inf.unideb.thesis.entity.JobType;
import hu.inf.unideb.thesis.repositories.JobTypeRepository;
import hu.inf.unideb.thesis.service.JobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class JobTypeServiceImpl implements JobTypeService {

    @Autowired
    JobTypeDAO jobTypeDAO;
    @Override
    public JobType findById(Long id) {
        return jobTypeDAO.findById(id);
    }

    @Override
    public JobType findByName(String name) {
        return jobTypeDAO.findByName(name);
    }

    @Override
    public List<JobType> findAll() {
        return jobTypeDAO.getAll();
    }

    @Override
    public void save(JobType jobType) {
        jobTypeDAO.save(jobType);
    }

    @Override
    public void update(Long id, JobType jobType) {
        if (jobTypeDAO.findById(id) != null){
            jobTypeDAO.update(jobType);
        }
        else {
            throw new RuntimeException("Could not find Job Type");
        }

    }

    @Override
    public void delete(JobType jobType) {
        jobTypeDAO.delete(jobType.getId());
    }

    @Override
    public void setUpMockedData() {
        if (findByName("test") == null){
            save(new JobType("test"));
            save(new JobType("test2"));
            save(new JobType("test3"));
            save(new JobType("test4"));
            save(new JobType("test5"));
            save(new JobType("test6"));
        }
    }
}
