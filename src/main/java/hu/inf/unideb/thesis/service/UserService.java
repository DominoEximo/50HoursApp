package hu.inf.unideb.thesis.service;

import hu.inf.unideb.thesis.entity.User;

import java.util.List;

public interface UserService {

    User findById(long id);

    User findByName(String name);
    List<User> findAll();

    List<User> getAllPaginated(int pageNumber, int pageSize);
    void save(User user);

    void update(Long id, User user);

    void delete(User user);

    /**
     * adds predefined users to the database
     */
    void setUpMockedData();
}
