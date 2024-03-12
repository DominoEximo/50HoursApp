package hu.inf.unideb.thesis.service.impl;

import hu.inf.unideb.thesis.dao.UserDAO;
import hu.inf.unideb.thesis.entity.Institution;
import hu.inf.unideb.thesis.entity.User;
import hu.inf.unideb.thesis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserDAO userDAO;

    @Override
    public User findById(long id) {
        return userDAO.findById(id);
    }

    @Override
    public User findByName(String name) {
        return userDAO.findByName(name);
    }

    @Override
    public List<User> findAll() {
        return userDAO.getAll();
    }

    @Override
    public List<User> getAllPaginated(int pageNumber, int pageSize) {
        return (List<User>) userDAO.getAllPaginated(pageNumber,pageSize);
    }

    @Override
    public void save(User user) {
        userDAO.save(user);
    }

    @Override
    public void update(Long id, User user) {

        if (userDAO.findById(id) != null){
            userDAO.update(user);
        }
        else {
            throw new RuntimeException("Could not find user");
        }

    }

    @Override
    public void delete(User user) {
        userDAO.delete(user.getId());
    }

    @Override
    public void setUpMockedData() {
        userDAO.setUpMockedData();
    }
}