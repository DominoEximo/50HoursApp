package hu.inf.unideb.thesis.service.impl;

import hu.inf.unideb.thesis.dao.InstitutionDAO;
import hu.inf.unideb.thesis.entity.Contract;
import hu.inf.unideb.thesis.entity.Institution;
import hu.inf.unideb.thesis.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    @Autowired
    InstitutionDAO institutionDAO;

    @Override
    public Institution findById(Long id) {
        return institutionDAO.findById(id);
    }

    @Override
    public Institution findByName(String name) {
        return institutionDAO.findByName(name);
    }

    @Override
    public List<Institution> findAll() {
        return institutionDAO.getAll();
    }

    @Override
    public void save(Institution institution) {
        institutionDAO.save(institution);
    }

    @Override
    public void update(Long id, Institution institution) {

        if (institutionDAO.findById(id) != null){
            institutionDAO.update(institution);
        }
        else {
            throw new RuntimeException("Could not find institution");
        }

    }

    @Override
    public void delete(Institution institution) {
        institutionDAO.delete(institution.getId());
    }

    @Override
    public void setUpMockedData() {
        institutionDAO.setUpMockedData();
    }
}
