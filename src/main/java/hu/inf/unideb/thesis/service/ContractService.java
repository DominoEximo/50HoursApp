package hu.inf.unideb.thesis.service;

import hu.inf.unideb.thesis.entity.Contract;


import java.util.List;

public interface ContractService {

    Contract findById(Long id);

    List<Contract> findAll();

    Contract save(Contract contract);

    Contract update(Long id, Contract contract);

    void delete(Contract contract);

    void setUpMockedData();
}
