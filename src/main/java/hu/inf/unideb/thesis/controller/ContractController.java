package hu.inf.unideb.thesis.controller;

import hu.inf.unideb.thesis.entity.Contract;
import hu.inf.unideb.thesis.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
