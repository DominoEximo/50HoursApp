package hu.inf.unideb.thesis.controller;

import hu.inf.unideb.thesis.entity.Contract;
import hu.inf.unideb.thesis.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RestController("/contract")
@CrossOrigin
@RequestScope
public class ContractController {

    @Autowired
    ContractService contractService;

    @RequestMapping(value = "/contracts/setUpMockedData",produces = "application/json")
    public void setUpMockedContract(){

        contractService.setUpMockedData();
    }

    @RequestMapping(value = "/contracts/getContracts",produces = "application/json")
    public List<Contract> getContracts(){

        return contractService.findAll();
    }

    @RequestMapping(value = "/contracts/getContractById/{id}",produces = "application/json")
    public Contract getContractById(@PathVariable Long id){

        return contractService.findById(id);

    }


    @PostMapping(value = "/contracts/saveContract",consumes = "application/json")
    public void saveContract(@RequestBody Contract contract){

        if (contractService.findById(contract.getId()) == null){
            contractService.save(contract);
        }
        else {
            throw new RuntimeException("Duplicate contract exception");
        }

    }

    @RequestMapping(value = "/contracts/deleteContractById/{id}")
    public void deleteContract(@PathVariable Long id){

        if (contractService.findById(id) != null){
            contractService.delete(contractService.findById(id));
        }
        else {
            throw new RuntimeException("Contract not found");
        }

    }
}
