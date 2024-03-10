package hu.inf.unideb.thesis.dao;

import hu.inf.unideb.thesis.entity.Contract;
import hu.inf.unideb.thesis.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContractDAO implements DAO<Contract>{

    @Autowired
    ContractRepository contractRepository;

    @Override
    public Contract findById(long id) {
        if (contractRepository.findById(id).isPresent()){
            return contractRepository.findById(id).get();
        }else {
            return  null;
        }

    }

    @Override
    public List<Contract> getAll() {
        return contractRepository.findAll();
    }

    @Override
    public void save(Contract contract) {
        contractRepository.save(contract);
    }

    @Override
    public Contract update(Contract contract) {
        return contractRepository.save(contract);
    }

    @Override
    public void delete(long id) {
        contractRepository.deleteById(id);
    }
}
