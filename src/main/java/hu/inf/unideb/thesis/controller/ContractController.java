package hu.inf.unideb.thesis.controller;

import hu.inf.unideb.thesis.entity.Contract;
import hu.inf.unideb.thesis.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController("/contract")
@CrossOrigin
@RequestScope
public class ContractController {

    private static final String CONTRACTNOTFOUNDMESSAGE = "Contract not found";

    @Autowired
    ContractService contractService;

    @RequestMapping(value = "/contracts/setUpMockedData",produces = "application/json")
    public void setUpMockedContract(){

        contractService.setUpMockedData();
    }

    @GetMapping(value = "/contracts",produces = "application/json")
    public CompletableFuture<List<Contract>> getContracts(){

        return  CompletableFuture.supplyAsync(() -> contractService.findAll());

    }

    @GetMapping(value = "/contracts/{id}",produces = "application/json")
    public CompletableFuture<Contract> getContractById(@PathVariable Long id){
        return  CompletableFuture.supplyAsync(() -> contractService.findById(id));


    }


    @PostMapping(value = "/contracts",consumes = "application/json")
    public CompletableFuture<Void> saveContract(@RequestBody Contract contract){

        return  CompletableFuture.supplyAsync(() -> {
            if (contractService.findById(contract.getId()) == null){

                contractService.save(contract);
                return null;

            }
            else {
                throw new RuntimeException("Duplicate contract exception");
            }
        });


    }

    @DeleteMapping(value = "/contracts/{id}")
    public CompletableFuture<Void> deleteContract(@PathVariable Long id){
        return  CompletableFuture.supplyAsync(() -> {
            if (contractService.findById(id) != null){

                contractService.delete(contractService.findById(id));
                return null;

            }
            else {

                throw new RuntimeException("Contract not found");

            }
        });


    }

    @PutMapping(value = "/contracts/{id}", consumes = "application/json")
    public CompletableFuture<Void> updateContract(@PathVariable Long id, @RequestBody Contract contract){
        return  CompletableFuture.supplyAsync(() -> {
            if (contractService.findById(id) != null) {
                contractService.update(id, contract);
                return null;
            }
            else {
                throw new RuntimeException(CONTRACTNOTFOUNDMESSAGE);
            }
        });
    }
}
