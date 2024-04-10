package hu.inf.unideb.thesis.unit.contract;

import hu.inf.unideb.thesis.entity.Contract;
import hu.inf.unideb.thesis.service.ContractService;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@Rollback
public class ContractServiceTest {

    @Autowired
    ContractService contractService;


    @Test
    public void testGetContracts(){

        Assert.assertEquals(0,contractService.findAll().size());

    }

    @Test
    public void testDeleteContract(){

        Contract newContract = new Contract();

        contractService.save(newContract);

        contractService.delete(newContract);

        Assert.assertEquals(0, contractService.findAll().size());

    }

    @Test
    public void testUpdateContract(){

        Contract newContract = new Contract();

        newContract.setCompleted(false);

        contractService.save(newContract);

        Contract temp = contractService.findById(1L);

        Assert.assertFalse(contractService.findById(1L).getCompleted());

        temp.setCompleted(true);

        contractService.update(1L,temp);

        Assert.assertTrue(contractService.findById(1L).getCompleted());



    }

    @Test
    public void testAddNewContract(){

        Contract newContract = new Contract();

        contractService.save(newContract);

        Assert.assertEquals(1,contractService.findAll().size());
    }
}
