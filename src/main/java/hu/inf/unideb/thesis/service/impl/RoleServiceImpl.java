package hu.inf.unideb.thesis.service.impl;

import hu.inf.unideb.thesis.dao.RoleDAO;
import hu.inf.unideb.thesis.entity.Contract;
import hu.inf.unideb.thesis.entity.Role;
import hu.inf.unideb.thesis.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing role-related operations.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDAO roleDAO;

    /**
     * Retrieve a role by their unique identifier.
     * @param id The ID of the role.
     * @return The role object if found, otherwise null.
     */
    @Override
    public Role findById(long id) {
        return roleDAO.findById(id);
    }

    /**
     * Retrieve all roles from the database.
     * @return a List of role objects.
     */
    @Override
    public List<Role> findAll() {
        return roleDAO.getAll();
    }

    /**
     * Save a role object into the database.
     * @param role The role object to be saved.
     * @return The role that was saved.
     */
    @Override
    public Role save(Role role) {
        return roleDAO.save(role);
    }

    /**
     * Update a role object in the database.
     * @param id The ID of the role.
     * @param role The role object to be updated.
     * @return The role that was updated.
     */
    @Override
    public Role update(Long id, Role role) {

        if (roleDAO.findById(id) != null){
            return roleDAO.update(role);
        }
        else {
            throw new RuntimeException("Could not find role");
        }

    }

    /**
     * Delete a role from the database based on the given ID.
     * @param role The object to be deleted.
     */
    @Override
    public void delete(Role role) {
        roleDAO.delete(role.getId());
    }

    /**
     * Retrieve a role by their name.
     * @param name The name of the role.
     * @return The role object if found.
     */
    @Override
    public Role findByName(String name) {
        return roleDAO.findByName(name);
    }

    /**
     * Sets up predefined data for the project.
     */
    @Override
    public void setUpMockedData() {
        roleDAO.setUpMockedData();
    }
}
