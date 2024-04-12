package hu.inf.unideb.thesis.service.impl;

import hu.inf.unideb.thesis.dao.InstitutionDAO;
import hu.inf.unideb.thesis.dao.JobTypeDAO;
import hu.inf.unideb.thesis.entity.Contract;
import hu.inf.unideb.thesis.entity.Institution;
import hu.inf.unideb.thesis.entity.JobType;
import hu.inf.unideb.thesis.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing institution-related operations.
 */
@Service
public class InstitutionServiceImpl implements InstitutionService {

    @Autowired
    InstitutionDAO institutionDAO;

    @Autowired
    JobTypeDAO jobTypeDAO;

    /**
     * Retrieve a institution by their unique identifier.
     * @param id The ID of the institution.
     * @return The institution object if found, otherwise null.
     */

    @Override
    public Institution findById(Long id) {
        return institutionDAO.findById(id);
    }

    /**
     * Retrieve all institutions from the database.
     * @return a List of institution objects.
     */
    @Override
    public List<Institution> findAll() {
        return institutionDAO.getAll();
    }

    /**
     * Save an institution object into the database.
     * @param institution The institution object to be saved.
     * @return The institution that was saved.
     */

    @Override
    public Institution save(Institution institution) {
        return institutionDAO.save(institution);
    }

    /**
     * Update an institution object in the database.
     * @param id The ID of the institution
     * @param institution The institution object to be updated.
     * @return The institution that was updated.
     */
    @Override
    public Institution update(Long id, Institution institution) {

        if (institutionDAO.findById(id) != null){
            return institutionDAO.update(institution);
        }
        else {
            throw new RuntimeException("Could not find institution");
        }

    }

    /**
     * Delete an institution from the database based on the given ID.
     * @param institution The object to be deleted.
     */
    @Override
    public void delete(Institution institution) {
        institutionDAO.delete(institution.getId());
    }

    /**
     * Retrieve an institution by their name.
     * @param name The name of the institution.
     * @return The institution object if found.
     */
    @Override
    public Institution findByName(String name) {
        return institutionDAO.findByName(name);
    }

    /**
     * Retrieve a filtered list of institutions from the database.
     * @param jobType the type of job to filter by.
     * @param pageable the pageable object containing the length and page number
     * @return a Page of institution objects.
     */
    @Override
    public Page<Institution> findByJobType(String jobType, Pageable pageable) {
        return institutionDAO.getFilteredInstitution(jobType,pageable);
    }

    /**
     * Sets up predefined data for the project.
     */
    @Override
    public void setUpMockedData() {
        institutionDAO.setUpMockedData();
    }
}
