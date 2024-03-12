package hu.inf.unideb.thesis.controller;

import hu.inf.unideb.thesis.entity.Role;
import hu.inf.unideb.thesis.entity.User;
import hu.inf.unideb.thesis.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RestController("/role")
@CrossOrigin
@RequestScope
public class RoleController {

    private final String ROLENOTFOUNDMESSAGE = "Could not find role with given ID";

    private final String ROLEALREADYEXISTMESSAGE = "Role already exists";

    @Autowired
    RoleService roleService;

    @GetMapping(value = "/roles",produces = "application/json")
    public List<Role> getRoles(){

        return roleService.findAll();
    }

    @GetMapping(value = "/roles/{id}", produces = "application/json")
    public Role getRoleById(@PathVariable Long id){

        if (roleService.findById(id) != null){
            return roleService.findById(id);
        }
        else {
            throw new RuntimeException(ROLENOTFOUNDMESSAGE);
        }

    }

    @DeleteMapping(value = "/roles/{id}")
    public void deleteRoleById(@PathVariable Long id){

        if(roleService.findById(id) != null){
            roleService.delete(roleService.findById(id));
        }
        else {
            throw new RuntimeException(ROLENOTFOUNDMESSAGE);
        }

    }

    @PostMapping(value = "/roles", consumes = "application/json")
    public void saveRole(@RequestBody Role role){

        if (roleService.findByName(role.getName()) == null && roleService.findById(role.getId()) == null){
            roleService.save(role);
        }
        else {
            throw new RuntimeException(ROLEALREADYEXISTMESSAGE);
        }

    }

    @PutMapping(value = "/roles/{id}", consumes = "application/json")
    public void updateRole(@PathVariable Long id, @RequestBody Role role){

        roleService.update(id,role);

    }

}
