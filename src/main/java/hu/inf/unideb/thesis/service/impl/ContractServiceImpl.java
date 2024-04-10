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
@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    ContractDAO contractDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    InstitutionDAO institutionDAO;

    @Override
    public Contract findById(Long id) {
        return contractDAO.findById(id);
    }

    @Override
    public List<Contract> findAll() {
        return contractDAO.getAll();
    }

    @Override
    public Contract save(Contract contract) {
        return contractDAO.save(contract);
    }

    @Override
    public Contract update(Long id,Contract contract) {

        if (contractDAO.findById(id) != null){
            return contractDAO.update(contract);
        }
        else {
            throw new RuntimeException("Could not find contract");
        }

    }

    @Override
    public void delete(Contract contract) {
        contractDAO.delete(contract.getId());
    }

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
