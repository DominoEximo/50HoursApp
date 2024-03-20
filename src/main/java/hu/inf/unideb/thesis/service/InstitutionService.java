package hu.inf.unideb.thesis.service;

import hu.inf.unideb.thesis.entity.Institution;
import hu.inf.unideb.thesis.entity.JobType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InstitutionService {

    Institution findById(Long id);

    Institution findByName(String name);

    Page<Institution> findByJobType(String jobType, Pageable pageable);

    List<Institution> findAll();

    void save(Institution institution);

    void update(Long id, Institution institution);

    void delete(Institution institution);

    void setUpMockedData();

}
