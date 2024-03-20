package hu.inf.unideb.thesis.service;

import hu.inf.unideb.thesis.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User findById(long id);

    User findByName(String name);

    Page<User> findByRole(String role, Pageable pageable);
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
