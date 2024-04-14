package hu.inf.unideb.thesis.dao;

import hu.inf.unideb.thesis.entity.Contract;
import hu.inf.unideb.thesis.repositories.ContractRepository;
import hu.inf.unideb.thesis.repositories.InstitutionRepository;
import hu.inf.unideb.thesis.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Data Access Object (DAO) service for managing contracts in the database.
 */
@Component
public class ContractDAO implements DAO<Contract>{

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InstitutionRepository institutionRepository;

    /**
     * Retrieve a contract by their unique identifier.
     * @param id The ID of the contract.
     * @return The contract object if found, otherwise null.
     */
    @Override
    public Contract findById(long id) {
        if (contractRepository.findById(id).isPresent()){
            return contractRepository.findById(id).get();
        }else {
            return  null;
        }

    }

    /**
     * Retrieve all contracts from the database.
     * @return a List of contract objects.
     */
    @Override
    public List<Contract> getAll() {
        return contractRepository.findAll();
    }

    /**
     * Save a contract object into the database.
     * @param contract The contract object to be saved.
     * @return The contract that was saved.
     */
    @Override
    public Contract save(Contract contract) {
        return contractRepository.save(contract);
    }

    /**
     * Update a contract object in the database.
     * @param contract The contract object to be updated.
     * @return The contract that was updated.
     */
    @Override
    public Contract update(Contract contract) {
        return contractRepository.save(contract);
    }

    /**
     * Delete a contract from the database based on the given ID.
     * @param id The ID of the object to be deleted.
     */
    @Override
    public void delete(long id) {
        contractRepository.deleteById(id);
    }

    public void setUpMockedData() {
        if (getAll().isEmpty()){
            Contract test = new Contract();
            test.setCompleted(false);
            LocalDate startDate = LocalDate.of(2024, 4, 5);
            test.setStartDate(Date.valueOf(startDate));
            LocalDate endDate = LocalDate.of(2024, 10, 5);
            test.setEndDate(Date.valueOf(endDate));
            if (userRepository.findByUsername("user") != null){
                test.setStudent(userRepository.findByUsername("user"));
            }
            if (institutionRepository.findByName("TestInstitution") != null){
                test.setInstitution(institutionRepository.findByName("TestInstitution"));
            }
            save(test);
        }
    }
}
