package hu.inf.unideb.thesis.controller;

import hu.inf.unideb.thesis.entity.Institution;
import hu.inf.unideb.thesis.entity.User;
import hu.inf.unideb.thesis.service.RoleService;
import hu.inf.unideb.thesis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<ResponseEntity<String>> registerUser(@RequestBody User user){
        return CompletableFuture.supplyAsync(() -> {

            if(userService.findByName(user.getName()) != null){
                throw new RuntimeException(USERALREADYEXISTMESSAGE);
            }
            else {
                user.setRoles(List.of(roleService.findByName("USER")));
                userService.save(user);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        });

    }

    @GetMapping(value = "/users",produces = "application/json")
    public CompletableFuture<List<User>> getUsers(){

        return CompletableFuture.supplyAsync(() -> userService.findAll());
    }

    @GetMapping(value = "/users/{id}", produces = "application/json")
    public CompletableFuture<User> getUserById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            User user = userService.findById(id);
            if (user != null) {
                return user;
            } else {
                throw new RuntimeException(USERNOTFOUNDMESSAGE);
            }
        });
    }

    @DeleteMapping(value = "/users/{id}")
    public CompletableFuture<Void> deleteUserById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            User user = userService.findById(id);
            if (user != null) {
                userService.delete(user);
                return null;
            } else {
                throw new RuntimeException(USERNOTFOUNDMESSAGE);
            }
        });
    }

    @PostMapping(value = "/users", consumes = "application/json")
    public CompletableFuture<Void> saveUser(@RequestBody User user){

        return CompletableFuture.supplyAsync(() -> {
            if (userService.findByName(user.getName()) == null && userService.findById(user.getId()) == null){
                userService.save(user);
                return null;
            }
            else {
                throw new RuntimeException(USERALREADYEXISTMESSAGE);
            }
        });

    }

    @PutMapping(value = "/users/{id}", consumes = "application/json")
    public CompletableFuture<Void> updateUser(@PathVariable Long id, @RequestBody User user){

        return CompletableFuture.supplyAsync(() -> {
                userService.update(id,user);
                return null;
        });


    }
}
