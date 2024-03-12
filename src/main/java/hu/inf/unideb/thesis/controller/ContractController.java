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

    @GetMapping(value = "/contracts",produces = "application/json")
    public List<Contract> getContracts(){

        return contractService.findAll();
    }

    @GetMapping(value = "/contracts/{id}",produces = "application/json")
    public Contract getContractById(@PathVariable Long id){

        return contractService.findById(id);

    }


    @PostMapping(value = "/contracts",consumes = "application/json")
    public void saveContract(@RequestBody Contract contract){

        if (contractService.findById(contract.getId()) == null){
            contractService.save(contract);
        }
        else {
            throw new RuntimeException("Duplicate contract exception");
        }

    }

    @DeleteMapping(value = "/contracts/{id}")
    public void deleteContract(@PathVariable Long id){

        if (contractService.findById(id) != null){
            contractService.delete(contractService.findById(id));
        }
        else {
            throw new RuntimeException("Contract not found");
        }

    }

    @PutMapping(value = "/contracts/{id}", consumes = "application/json")
    public void updateContract(@PathVariable Long id, @RequestBody Contract contract){

        contractService.update(id,contract);

    }
}
