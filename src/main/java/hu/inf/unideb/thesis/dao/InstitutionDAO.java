package hu.inf.unideb.thesis.dao;

import hu.inf.unideb.thesis.entity.Description;
import hu.inf.unideb.thesis.entity.Institution;
import hu.inf.unideb.thesis.entity.JobType;
import hu.inf.unideb.thesis.entity.Location;
import hu.inf.unideb.thesis.repositories.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Data Access Object (DAO) service for managing institutions in the database.
 */
@Component
public class InstitutionDAO implements DAO<Institution>{

    @Autowired
    InstitutionRepository institutionRepository;

    /**
     * Retrieve a institution by their unique identifier.
     * @param id The ID of the institution.
     * @return The institution object if found, otherwise null.
     */
    @Override
    public Institution findById(long id) {

        if (institutionRepository.findById(id).isPresent()){
            return institutionRepository.findById(id).get();
        }else {
            return  null;
        }
    }

    /**
     * Retrieve all institutions from the database.
     * @return a List of institution objects.
     */
    @Override
    public List<Institution> getAll() {
        return institutionRepository.findAll();
    }

    /**
     * Save an institution object into the database.
     * @param institution The institution object to be saved.
     * @return The institution that was saved.
     */
    @Override
    public Institution save(Institution institution) {
        return institutionRepository.save(institution);
    }

    /**
     * Update an institution object in the database.
     * @param institution The institution object to be updated.
     * @return The institution that was updated.
     */
    @Override
    public Institution update(Institution institution) {
        return institutionRepository.save(institution);
    }

    /**
     * Delete an institution from the database based on the given ID.
     * @param id The ID of the object to be deleted.
     */
    @Override
    public void delete(long id) {
        institutionRepository.deleteById(id);
    }

    /**
     * Retrieve an institution by their name.
     * @param name The name of the institution.
     * @return The institution object if found.
     */
    public Institution findByName(String name){
        return institutionRepository.findByName(name);
    }

    /**
     * Retrieve a filtered list of institutions from the database.
     * @param jobType the type of job to filter by.
     * @param pageable the pageable object containing the length and page number
     * @return a Page of institution objects.
     */
    public Page<Institution> getFilteredInstitution(String jobType, Pageable pageable){
        if (jobType != null){
            return institutionRepository.findByType(jobType,pageable);
        }
        else {
            return institutionRepository.findAll(pageable);
        }
    }

    /**
     * Sets up predefined data for the project.
     */
    public void setUpMockedData(){
        if (getAll().isEmpty()){
            Institution test = new Institution();
            test.setName("TestInstitution");
            Location testLocation = new Location();
            testLocation.setCountry("Hungary");
            testLocation.setStreet("Budapest");
            testLocation.setLat(47.497913);
            testLocation.setLon(19.040236);
            test.setLocation(testLocation);
            Description testDescription = new Description();
            testDescription.setText("This is a test Description for the purpose of testing this description");
            testDescription.setLinks(List.of("https://outlook.office.com/mail/","https://www.youtube.com/watch?v=gEFM5EoE4x4"));
            test.setDescription(testDescription);
            save(test);
        }
    }
}
