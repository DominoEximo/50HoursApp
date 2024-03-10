package hu.inf.unideb.thesis.service;

import hu.inf.unideb.thesis.entity.Contract;


import java.util.List;

public interface ContractService {

    Contract findById(Long id);

    List<Contract> findAll();

    void save(Contract contract);

    void delete(Contract contract);

    void setUpMockedData();
}
