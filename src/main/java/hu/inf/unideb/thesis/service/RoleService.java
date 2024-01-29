package hu.inf.unideb.thesis.service;

import hu.inf.unideb.thesis.entity.Role;

import java.util.List;

public interface RoleService {

    Role findById(long id);

    Role findByName(String name);

    List<Role> findAll();

    void save(Role role);

    void delete(Role role);

    /**
     * adds predefined roles to the database
     */
    void setUpMockedData();
}
