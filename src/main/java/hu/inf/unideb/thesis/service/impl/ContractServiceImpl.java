package hu.inf.unideb.thesis.service.impl;

import hu.inf.unideb.thesis.dao.ContractDAO;
import hu.inf.unideb.thesis.dao.InstitutionDAO;
import hu.inf.unideb.thesis.dao.UserDAO;
import hu.inf.unideb.thesis.entity.Contract;
import hu.inf.unideb.thesis.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
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
    public void save(Contract contract) {
        contractDAO.save(contract);
    }

    @Override
    public void delete(Contract contract) {
        contractDAO.update(contract);
    }

    @Override
    public void setUpMockedData() {
        if (findById(1L) == null){
            Contract test = new Contract();
            test.setCompleted(false);
            test.setStartDate(new Date(2024,3,4));
            test.setEndDate(new Date(2024,6,1));
            if (userDAO.findByName("user") != null){
                test.setStudent(userDAO.findByName("user"));
            }
            if (institutionDAO.findByName("TestInstitution") != null){
                test.setInstitution(institutionDAO.findByName("TestInstitution"));
            }
            save(test);
        }
    }
}
