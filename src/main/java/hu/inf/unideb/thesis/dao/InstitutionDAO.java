package hu.inf.unideb.thesis.dao;

import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import hu.inf.unideb.thesis.entity.Institution;
import hu.inf.unideb.thesis.repositories.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InstitutionDAO implements DAO<Institution>{

    @Autowired
    InstitutionRepository institutionRepository;

    @Override
    public Institution findById(long id) {

        if (institutionRepository.findById(id).isPresent()){
            return institutionRepository.findById(id).get();
        }else {
            return  null;
        }
    }

    @Override
    public List<Institution> getAll() {
        return institutionRepository.findAll();
    }

    @Override
    public void save(Institution institution) {
        institutionRepository.save(institution);
    }

    @Override
    public Institution update(Institution institution) {
        return institutionRepository.save(institution);
    }

    @Override
    public void delete(long id) {
        institutionRepository.deleteById(id);
    }

    public Institution findByName(String name){
        return institutionRepository.findByName(name);
    }

    public void setUpMockedData(){
        if (findByName("TestInstitution") == null){
            Institution test = new Institution();
            test.setName("TestInstitution");
            test.setLocation("testLocation");
            test.setType("testType");
            save(test);
        }
    }
}
