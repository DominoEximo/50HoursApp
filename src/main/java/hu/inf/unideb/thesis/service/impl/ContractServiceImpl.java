package hu.inf.unideb.thesis.service.impl;

import hu.inf.unideb.thesis.dao.ContractDAO;
import hu.inf.unideb.thesis.dao.InstitutionDAO;
import hu.inf.unideb.thesis.dao.UserDAO;
import hu.inf.unideb.thesis.entity.Contract;
import hu.inf.unideb.thesis.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Service layer for managing contract-related operations.
 */
@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    ContractDAO contractDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    InstitutionDAO institutionDAO;

    /**
     * Retrieve a contract by their unique identifier.
     * @param id The ID of the contract.
     * @return The contract object if found, otherwise null.
     */
    @Override
    public Contract findById(Long id) {
        return contractDAO.findById(id);
    }

    /**
     * Retrieve all contracts from the database.
     * @return a List of contract objects.
     */
    @Override
    public List<Contract> findAll() {
        return contractDAO.getAll();
    }

    /**
     * Save a contract object into the database.
     * @param contract The contract object to be saved.
     * @return The contract that was saved.
     */
    @Override
    public Contract save(Contract contract) {
        return contractDAO.save(contract);
    }

    /**
     * Update a contract object in the database.
     * @param id The ID of the contract.
     * @param contract The contract object to be updated.
     * @return The contract that was updated.
     */
    @Override
    public Contract update(Long id,Contract contract) {

        if (contractDAO.findById(id) != null){
            return contractDAO.update(contract);
        }
        else {
            throw new RuntimeException("Could not find contract");
        }

    }

    /**
     * Delete a contract from the database based on the given ID.
     * @param contract The object to be deleted.
     */
    @Override
    public void delete(Contract contract) {
        contractDAO.delete(contract.getId());
    }

    /**
     * Sets up predefined data for the project.
     */
    @Override
    public void setUpMockedData() {
        if (findAll().isEmpty()){
            Contract test = new Contract();
            test.setCompleted(false);
            LocalDate startDate = LocalDate.of(2024, 4, 5);
            test.setStartDate(Date.valueOf(startDate));
            LocalDate endDate = LocalDate.of(2024, 10, 5);
            test.setEndDate(Date.valueOf(endDate));
            if (userDAO.findByUsername("user") != null){
                test.setStudent(userDAO.findByUsername("user"));
            }
            if (institutionDAO.findByName("TestInstitution") != null){
                test.setInstitution(institutionDAO.findByName("TestInstitution"));
            }
            save(test);
        }
    }
}
