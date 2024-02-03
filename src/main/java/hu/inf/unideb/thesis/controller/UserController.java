package hu.inf.unideb.thesis.controller;

import hu.inf.unideb.thesis.entity.User;
import hu.inf.unideb.thesis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RestController("/user")
@CrossOrigin
@RequestScope
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/signup")
    public String registerUser(@RequestBody User user){

        if(userService.findByName(user.getName()) != null){
            throw new RuntimeException("User already exists");
        }
        else {
            userService.save(user);
            return user.getName() + "registered";
        }
    }

    @RequestMapping(value = "/getUsers",produces = "application/json")
    public List<User> getUsers(){

        return userService.findAll();
    }
}
