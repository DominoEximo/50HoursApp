package hu.inf.unideb.thesis.controller;

import hu.inf.unideb.thesis.entity.User;
import hu.inf.unideb.thesis.service.RoleService;
import hu.inf.unideb.thesis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RestController("/user")
@CrossOrigin
@RequestScope
public class UserController {

    private final String USERNOTFOUNDMESSAGE = "Could not find user with given ID";

    private final String USERALREADYEXISTMESSAGE = "User already exists";

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;


    @PostMapping(value = "/users/signup",produces = "application/json")
    public ResponseEntity<String> registerUser(@RequestBody User user){

        if(userService.findByName(user.getName()) != null){
            throw new RuntimeException(USERALREADYEXISTMESSAGE);
        }
        else {
            user.setRoles(List.of(roleService.findByName("USER")));
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/users/getUsers",produces = "application/json")
    public List<User> getUsers(){

        return userService.findAll();
    }

    @GetMapping(value = "/users/findUserById/{id}", produces = "application/json")
    public User getUserById(@PathVariable Long id){

        if (userService.findById(id) != null){
            return userService.findById(id);
        }
        else {
            throw new RuntimeException(USERNOTFOUNDMESSAGE);
        }

    }

    @RequestMapping(value = "/users/deleteUserById/{id}")
    public void deleteUserById(@PathVariable Long id){

        if(userService.findById(id) != null){
            userService.delete(userService.findById(id));
        }
        else {
            throw new RuntimeException(USERNOTFOUNDMESSAGE);
        }

    }

    @PostMapping(value = "/users/saveUser", consumes = "application/json")
    public void saveUser(@RequestBody User user){

        if (userService.findByName(user.getName()) == null && userService.findById(user.getId()) == null){
            userService.save(user);
        }
        else {
            throw new RuntimeException(USERALREADYEXISTMESSAGE);
        }

    }
}
