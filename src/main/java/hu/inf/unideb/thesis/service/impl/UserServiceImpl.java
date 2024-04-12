package hu.inf.unideb.thesis.service.impl;

import hu.inf.unideb.thesis.dao.RoleDAO;
import hu.inf.unideb.thesis.dao.UserDAO;
import hu.inf.unideb.thesis.entity.Location;
import hu.inf.unideb.thesis.entity.Role;
import hu.inf.unideb.thesis.entity.User;
import hu.inf.unideb.thesis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service layer for managing user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    RoleDAO roleDAO;

    /**
     * Retrieve a user by their unique identifier.
     * @param id The ID of the user.
     * @return The user object if found, otherwise null.
     */
    @Override
    public User findById(long id) {
        return userDAO.findById(id);
    }

    /**
     * Retrieve all users from the database.
     * @return a List of user objects.
     */
    @Override
    public List<User> findAll() {
        return userDAO.getAll();
    }

    /**
     * Save a user object into the database.
     * @param user The user object to be saved.
     * @return The user that was saved.
     */
    @Override
    public User save(User user) {
        return userDAO.save(user);
    }

    /**
     * Update a user object in the database.
     * @param id The ID of the user.
     * @param user The user object to be updated.
     * @return The user that was updated.
     */
    @Override
    public User update(Long id, User user) {

        if (userDAO.findById(id) != null){
            return userDAO.update(user);
        }
        else {
            throw new RuntimeException("Could not find user");
        }

    }

    /**
     * Delete a user from the database based on the given ID.
     * @param user The object to be deleted.
     */
    @Override
    public void delete(User user) {
        userDAO.delete(user.getId());
    }

    /**
     * Retrieve a user by their username.
     * @param name The username of the user.
     * @return The user object if found.
     */
    @Override
    public User findByName(String name) {
        return userDAO.findByUsername(name);
    }

    /**
     * Retrieve all users from the database.
     * @param pageNumber the number of the current page
     * @param pageSize the size of the page
     * @return a Page of user objects.
     */
    @Override
    public List<User> getAllPaginated(int pageNumber, int pageSize) {
        return userDAO.getAllPaginated(pageNumber,pageSize);
    }

    /**
     * Retrieve a filtered list of users from the database.
     * @param role the role to filter by.
     * @param pageable the pageable object containing the length and page number
     * @return a Page of user objects.
     */
    @Override
    public Page<User> findByRole(String role, Pageable pageable) {
        return userDAO.getUsersByRole(role, pageable);
    }

    /**
     * Sets up predefined data for the project.
     */
    @Override
    public void setUpMockedData() {
        userDAO.setUpMockedData();
    }
}