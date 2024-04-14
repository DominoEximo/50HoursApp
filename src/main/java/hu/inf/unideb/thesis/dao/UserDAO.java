package hu.inf.unideb.thesis.dao;

import hu.inf.unideb.thesis.entity.Location;
import hu.inf.unideb.thesis.entity.Role;
import hu.inf.unideb.thesis.entity.User;
import hu.inf.unideb.thesis.repositories.RoleRepository;
import hu.inf.unideb.thesis.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Data Access Object (DAO) service for managing users in the database.
 */
@Component
public class UserDAO implements DAO<User>{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    /**
     * Retrieve a user by their unique identifier.
     * @param id The ID of the user.
     * @return The user object if found, otherwise null.
     */
    @Override
    public User findById(long id) {
        if (userRepository.findById(id).isPresent()){
            return userRepository.findById(id).get();
        }else {
            return  null;
        }
    }
    /**
     * Retrieve all users from the database.
     * @return a List of user objects.
     */
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * Save a user object into the database.
     * @param user The user object to be saved.
     * @return The user that was saved.
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Update a user object in the database.
     * @param user The user object to be updated.
     * @return The user that was updated.
     */
    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    /**
     * Delete a user from the database based on the given ID.
     * @param id The ID of the object to be deleted.
     */
    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    /**
     * Retrieve a user by their username.
     * @param name The username of the user.
     * @return The user object if found.
     */
    public User findByUsername(String name){
        return userRepository.findByUsername(name);
    }

    /**
     * Retrieve all users from the database.
     * @param pageNumber the number of the current page
     * @param pageSize the size of the page
     * @return a Page of user objects.
     */
    public List<User> getAllPaginated(int pageNumber, int pageSize) {
        return userRepository.findAll(PageRequest.of(pageNumber-1,pageSize)).getContent();
    }

    /**
     * Retrieve a filtered list of users from the database.
     * @param role the role to filter by.
     * @param pageable the pageable object containing the length and page number
     * @return a Page of user objects.
     */
    public Page<User> getUsersByRole(String role, Pageable pageable) {
        if (role != null) {
            return userRepository.findByRolesName(role, pageable);
        } else {
            return userRepository.findAll(pageable);
        }
    }

    /**
     * Sets up predefined data for the project.
     */
    public void setUpMockedData(){
        if (getAll().isEmpty()){
            List<Role> userRoles = new ArrayList<>();
            userRoles.add(roleRepository.findByName("USER"));
            List<Role> bacofficeRoles = new ArrayList<>();
            bacofficeRoles.add(roleRepository.findByName("USER"));
            bacofficeRoles.add(roleRepository.findByName("BACKOFFICE"));
            User testUser = new User("user","User","User","test@gmail.com","(+36) 232-2222"
                    ,new Date(System.currentTimeMillis()),'m',passwordEncoder.encode("password"),"OMTEST"
                    ,"test School name", "TESTCOORDINATOR","TESTCOORDINATOR@email.test"
                    ,"testcordphone",userRoles);
            Location testLocation = new Location();
            testLocation.setCountry("Hungary");
            testLocation.setStreet("Nyíregyháza");
            testLocation.setLat(47.95539);
            testLocation.setLon(21.71671);
            testUser.setLocation(testLocation);
            save(testUser);
            save(new User("admin","Admin","Admin","DominoEximo@gmail.com","(+36) 232-1234"
                    ,new Date(System.currentTimeMillis()),'f',passwordEncoder.encode("password")
                    , "OMTEST2","test School name 2"
                    ,"TESTCOORDINATOR2","TESTCOORDINATOR2@email.test"
                    ,"testcordphone2",bacofficeRoles));

        }
    }
}
