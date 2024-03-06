package hu.inf.unideb.thesis.service;

import hu.inf.unideb.thesis.entity.Institution;

import java.util.List;

public interface InstitutionService {

    Institution findById(Long id);

    Institution findByName(String name);

    List<Institution> findAll();

    void save(Institution institution);

    void delete(Institution institution);

    void setUpMockedData();

}
