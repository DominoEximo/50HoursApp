package hu.inf.unideb.thesis.unit.contract;

import hu.inf.unideb.thesis.entity.Contract;
import hu.inf.unideb.thesis.service.ContractService;
import hu.inf.unideb.thesis.service.InstitutionService;
import hu.inf.unideb.thesis.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Collectors;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@Rollback
@ActiveProfiles("unit")
public class ContractServiceTest {

    @Autowired
    ContractService contractService;

    @Autowired
    InstitutionService institutionService;

    @Autowired
    UserService userService;


    @Test
    public void testGetContracts(){

        Assertions.assertEquals(0,contractService.findAll().size());

    }

    @Test
    public void testDeleteContract(){

        Contract newContract = new Contract();

        contractService.save(newContract);

        contractService.delete(newContract);

        Assertions.assertEquals(0, contractService.findAll().size());

    }

    @Test
    public void testUpdateContract(){

        Contract newContract = new Contract();

        newContract.setCompleted(false);

        contractService.save(newContract);

        Assertions.assertFalse(newContract.getCompleted());

        newContract.setCompleted(true);

        contractService.update(newContract.getId(),newContract);

        Assertions.assertTrue(contractService.findById(newContract.getId()).getCompleted());



    }

    @Test
    public void testAddNewContract(){

        Contract newContract = new Contract();

        contractService.save(newContract);

        Assertions.assertEquals(1,contractService.findAll().size());
    }

    @Test
    public void testSettingUpMockedData(){
        institutionService.setUpMockedData();
        contractService.setUpMockedData();

        Assertions.assertEquals(1,contractService.findAll().size());

        //Duplicate call doesn't create new instance
        contractService.setUpMockedData();

        Assertions.assertEquals(1,contractService.findAll().size());

        userService.delete(userService.findByName("user"));

        //User is null
        contractService.setUpMockedData();
    }
}
