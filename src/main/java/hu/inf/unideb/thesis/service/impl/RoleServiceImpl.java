package hu.inf.unideb.thesis.service.impl;

import hu.inf.unideb.thesis.dao.RoleDAO;
import hu.inf.unideb.thesis.entity.Contract;
import hu.inf.unideb.thesis.entity.Role;
import hu.inf.unideb.thesis.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDAO roleDAO;

    @Override
    public Role findById(long id) {
        return roleDAO.findById(id);
    }

    @Override
    public Role findByName(String name) {
        return roleDAO.findByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleDAO.getAll();
    }

    @Override
    public Role save(Role role) {
        return roleDAO.save(role);
    }

    @Override
    public Role update(Long id, Role role) {

        if (roleDAO.findById(id) != null){
            return roleDAO.update(role);
        }
        else {
            throw new RuntimeException("Could not find role");
        }

    }

    @Override
    public void delete(Role role) {
        roleDAO.delete(role.getId());
    }

    @Override
    public void setUpMockedData() {
        roleDAO.setUpMockedData();
    }
}
