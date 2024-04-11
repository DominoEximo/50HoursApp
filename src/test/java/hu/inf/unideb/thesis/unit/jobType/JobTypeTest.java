package hu.inf.unideb.thesis.unit.jobType;

import hu.inf.unideb.thesis.entity.JobType;
import hu.inf.unideb.thesis.service.JobTypeService;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@Rollback
@ActiveProfiles("unit")
public class JobTypeTest {

    @Autowired
    JobTypeService jobTypeService;

    @Test
    public void testGetJobTypes(){

        JobType testType = new JobType("test");

        jobTypeService.save(testType);

        Assertions.assertEquals(1,jobTypeService.findAll().size());
    }

    @Test
    public void testSetUpMockedData(){
        jobTypeService.setUpMockedData();

        Assertions.assertFalse(jobTypeService.findAll().isEmpty());
    }

    @Test
    public void testDeleteJobType(){

        JobType testType = new JobType("test");

        jobTypeService.save(testType);

        Assertions.assertEquals(1,jobTypeService.findAll().size());

        jobTypeService.delete(testType);

        Assertions.assertEquals(0,jobTypeService.findAll().size());
    }

    @Test
    public void testUpdateJobType(){

        JobType testType = new JobType("test");
        jobTypeService.save(testType);

        Assertions.assertEquals("test",jobTypeService.findByName("test").getName());

        testType.setName("newTest");

        jobTypeService.update(testType.getId(),testType);


        Assertions.assertEquals("newTest",jobTypeService.findByName("newTest").getName());
    }
}
