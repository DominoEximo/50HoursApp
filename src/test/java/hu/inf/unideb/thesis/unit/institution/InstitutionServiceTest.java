package hu.inf.unideb.thesis.unit.institution;

import hu.inf.unideb.thesis.entity.Description;
import hu.inf.unideb.thesis.entity.Institution;
import hu.inf.unideb.thesis.entity.JobType;
import hu.inf.unideb.thesis.service.InstitutionService;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@Rollback
@ActiveProfiles("unit")
public class InstitutionServiceTest {

    @Autowired
    InstitutionService institutionService;

    @BeforeEach
    public void setUp(){
        institutionService.setUpMockedData();
    }

    @Test
    public void testGetAllInstitutions(){

        Institution testInstitution = new Institution();

        institutionService.save(testInstitution);

        Assertions.assertEquals(2, institutionService.findAll().size());
    }

    @Test
    public void testDeleteInstitution(){
        Institution testInstitution = new Institution();

        institutionService.save(testInstitution);

        Assertions.assertEquals(2, institutionService.findAll().size());

        institutionService.delete(testInstitution);

        Assertions.assertEquals(1, institutionService.findAll().size());
    }

    @Test
    public void testUpdateInstitution(){



        Institution testInstitution = new Institution();

        institutionService.save(testInstitution);

        Assertions.assertNull(institutionService.findAll().get(1).getDescription());

        testInstitution.setDescription(new Description());

        institutionService.update(testInstitution.getId(),testInstitution);

        Assertions.assertNotNull(institutionService.findAll().get(1).getDescription());
    }

    @Test
    public void testFindInstituionByName(){

        Institution testInstitution = new Institution();

        testInstitution.setName("test");

        testInstitution.setType(new JobType("InstTestJobType"));

        institutionService.save(testInstitution);

        Assertions.assertEquals("InstTestJobType",institutionService.findByName("test").getType().getName());


    }

    @Test
    public void testFindInstitutionByJobType(){

        Institution testInstitution = new Institution();

        testInstitution.setName("test");

        testInstitution.setType(new JobType("InstTestJobType"));

        Institution testInstitution2 = new Institution();

        testInstitution2.setName("test");

        testInstitution2.setType(new JobType("InstTestJobType2"));

        institutionService.save(testInstitution);
        institutionService.save(testInstitution2);

        Assertions.assertEquals(1,institutionService.findByJobType("InstTestJobType2", Pageable.ofSize(10)).get().toList().size());
    }
}
