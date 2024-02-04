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

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @PostMapping(value = "/signup")
    public ResponseEntity<String> registerUser(@RequestBody User user){

        if(userService.findByName(user.getName()) != null){
            throw new RuntimeException("User already exists");
        }
        else {
            user.setRoles(List.of(roleService.findByName("USER")));
            userService.save(user);
            return new ResponseEntity<>(user.getName() +" " + "registered!", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/getUsers",produces = "application/json")
    public List<User> getUsers(){

        return userService.findAll();
    }
}
