package hu.inf.unideb.thesis.dao;

import hu.inf.unideb.thesis.entity.Role;
import hu.inf.unideb.thesis.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Data Access Object (DAO) service for managing roles in the database.
 */
@Component
public class RoleDAO implements DAO<Role>{

    @Autowired
    RoleRepository roleRepository;

    /**
     * Retrieve a role by their unique identifier.
     * @param id The ID of the role.
     * @return The role object if found, otherwise null.
     */
    @Override
    public Role findById(long id) {

        if (roleRepository.findById(id).isPresent()){
            return roleRepository.findById(id).get();
        }else {
            return  null;
        }
    }

    /**
     * Retrieve all roles from the database.
     * @return a List of role objects.
     */
    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    /**
     * Save a role object into the database.
     * @param role The role object to be saved.
     * @return The role that was saved.
     */
    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    /**
     * Update a role object in the database.
     * @param role The role object to be updated.
     * @return The role that was updated.
     */
    @Override
    public Role update(Role role) {
        return roleRepository.save(role);
    }

    /**
     * Delete a role from the database based on the given ID.
     * @param id The ID of the object to be deleted.
     */
    @Override
    public void delete(long id) {
        roleRepository.deleteById(id);
    }

    /**
     * Retrieve a role by their name.
     * @param name The name of the role.
     * @return The role object if found.
     */
    public Role findByName(String name){
        return roleRepository.findByName(name);
    }

    /**
     * Sets up predefined data for the project.
     */
    public void setUpMockedData(){
        if(getAll().isEmpty()){
            save(new Role("BACKOFFICE"));
            save(new Role("USER"));
        }

    }
}
