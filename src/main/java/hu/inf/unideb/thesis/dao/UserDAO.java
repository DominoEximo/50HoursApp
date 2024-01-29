package hu.inf.unideb.thesis.dao;

import hu.inf.unideb.thesis.entity.Role;
import hu.inf.unideb.thesis.entity.User;
import hu.inf.unideb.thesis.repositories.RoleRepository;
import hu.inf.unideb.thesis.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDAO implements DAO<User>{



    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public UserDAO() {
        //Constructor
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public User findByName(String name){
        return userRepository.findByName(name);
    }

    public List<User> getAllPaginated(int pageNumber, int pageSize) {
        return userRepository.findAll(PageRequest.of(pageNumber-1,pageSize)).getContent();
    }

    public void setUpMockedData(){
        if (findByName("user") == null){
            List<Role> userRoles = new ArrayList<>();
            userRoles.add(roleRepository.findByName("USER"));
            List<Role> bacofficeRoles = new ArrayList<>();
            bacofficeRoles.add(roleRepository.findByName("USER"));
            bacofficeRoles.add(roleRepository.findByName("BACKOFFICE"));
            save(new User("user","test@gmail.com","(+36) 232-2222",new Date(System.currentTimeMillis()),'m',"{noop}password",userRoles));
            save(new User("admin","DominoEximo@gmail.com","(+36) 232-1234",new Date(System.currentTimeMillis()),'f',"{noop}password",bacofficeRoles));

        }
    }
}
