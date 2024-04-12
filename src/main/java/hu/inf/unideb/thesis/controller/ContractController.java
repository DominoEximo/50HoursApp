package hu.inf.unideb.thesis.controller;

import hu.inf.unideb.thesis.entity.Contract;
import hu.inf.unideb.thesis.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.springframework.web.servlet.function.ServerResponse.status;

/**
 * Controller for handling contract-related HTTP requests.
 */
@RestController("/contract")
@CrossOrigin
@RequestScope
public class ContractController {

    private static final String CONTRACTNOTFOUNDMESSAGE = "Contract not found";

    @Autowired
    ContractService contractService;

    /**
     * Retrieve a contract by their ID.
     * @param id The ID of the contract.
     * @return The contract object if found, otherwise null.
     */
    @GetMapping(value = "/contracts/{id}",produces = "application/json")
    public CompletableFuture<ResponseEntity<Contract>> getContractById(@PathVariable Long id){
        return  CompletableFuture.supplyAsync(() ->ResponseEntity.ok(contractService.findById(id)));


    }

    /**
     * Retrieve all contracts from the database.
     * @return a List of contract objects along with HTTP status.
     */
    @GetMapping(value = "/contracts",produces = "application/json")
    public CompletableFuture<ResponseEntity<List<Contract>>> getContracts(){

        return  CompletableFuture.supplyAsync(() -> ResponseEntity.ok(contractService.findAll()));

    }
    /**
     * Create a new contract.
     * @param contract The contract object to be created.
     * @return The contract that was created along with HTTP status.
     */
    @PostMapping(value = "/contracts",consumes = "application/json")
    public CompletableFuture<ResponseEntity<Contract>> saveContract(@RequestBody Contract contract){

        return  CompletableFuture.supplyAsync(() -> {
            if (contractService.findById(contract.getId()) == null){
                return ResponseEntity.status(201).body(contractService.save(contract));
            }
            else {
                throw new RuntimeException("Duplicate contract exception");
            }
        });
    }

    /**
     * Update a contract .
     * @param id The ID of the contract.
     * @param contract The contract to be updated.
     * @return The contract that was updated along with HTTP status.
     */
    @PutMapping(value = "/contracts/{id}", consumes = "application/json")
    public CompletableFuture<ResponseEntity<Contract>> updateContract(@PathVariable Long id, @RequestBody Contract contract){
        return  CompletableFuture.supplyAsync(() -> {
            if (contractService.findById(id) != null) {
                return ResponseEntity.status(204).body(contractService.update(id, contract));
            }
            else {
                throw new RuntimeException(CONTRACTNOTFOUNDMESSAGE);
            }
        });
    }

    /**
     * Delete a contract based on the given ID.
     * @param id The ID of the object to be deleted.
     */
    @DeleteMapping(value = "/contracts/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteContract(@PathVariable Long id){
        return  CompletableFuture.supplyAsync(() -> {
            if (contractService.findById(id) != null){
                contractService.delete(contractService.findById(id));
                return ResponseEntity.ok(null);
            }
            else {
                throw new RuntimeException("Contract not found");
            }
        });
    }
    /**
     * Sets up predefined data for the project.
     */
    @RequestMapping(value = "/contracts/setUpMockedData",produces = "application/json")
    public void setUpMockedContract(){

        contractService.setUpMockedData();
    }
}
