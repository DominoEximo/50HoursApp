package hu.inf.unideb.thesis.controller;

import hu.inf.unideb.thesis.entity.Location;
import hu.inf.unideb.thesis.entity.User;
import hu.inf.unideb.thesis.service.GeocodingService;
import hu.inf.unideb.thesis.service.RoleService;
import hu.inf.unideb.thesis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    GeocodingService geocodingService;


    @PostMapping(value = "/users/signup",produces = "application/json")
    public CompletableFuture<ResponseEntity<String>> registerUser(@RequestBody User user){
        return CompletableFuture.supplyAsync(() -> {

            if(userService.findByName(user.getUsername()) != null){
                throw new RuntimeException(USERALREADYEXISTMESSAGE);
            }
            else {
                user.setRoles(List.of(roleService.findByName("USER")));

                if (user.getLocation() != null){
                    String address = user.getLocation().getStreet();
                    Location responseLocation = geocodingService.geocodeAddress(address);
                    user.getLocation().setLat(responseLocation.getLat());
                    user.getLocation().setLon(responseLocation.getLon());
                }

                userService.save(user);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        });

    }
    @GetMapping(value = "/users",produces = "application/json")
    public CompletableFuture<ResponseEntity<Page<User>>> getUsersByRole(
            @RequestParam(name = "role", required = false) String role, Pageable pageable) {

        Page<User> users = userService.findByRole(role, pageable);
        return CompletableFuture.supplyAsync(() -> ResponseEntity.status(200).body(users));
    }


    @GetMapping(value = "/users/{id}", produces = "application/json")
    public CompletableFuture<ResponseEntity<User>> getUserById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            User user = userService.findById(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                throw new RuntimeException(USERNOTFOUNDMESSAGE);
            }
        });
    }

    @DeleteMapping(value = "/users/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteUserById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            User user = userService.findById(id);
            if (user != null) {
                userService.delete(user);
                return ResponseEntity.ok(null);
            } else {
                throw new RuntimeException(USERNOTFOUNDMESSAGE);
            }
        });
    }

    @PostMapping(value = "/users", consumes = "application/json")
    public CompletableFuture<ResponseEntity<User>> saveUser(@RequestBody User user){

        return CompletableFuture.supplyAsync(() -> {
            if (userService.findByName(user.getUsername()) == null && userService.findById(user.getId()) == null){

                if (user.getLocation() != null){
                    String address = user.getLocation().getStreet();
                    Location responseLocation = geocodingService.geocodeAddress(address);
                    user.getLocation().setLat(responseLocation.getLat());
                    user.getLocation().setLon(responseLocation.getLon());
                }


                userService.save(user);
                return ResponseEntity.status(201).body(user);
            }
            else {
                throw new RuntimeException(USERALREADYEXISTMESSAGE);
            }
        });

    }

    @PutMapping(value = "/users/{id}", consumes = "application/json")
    public CompletableFuture<ResponseEntity<User>> updateUser(@PathVariable Long id, @RequestBody User user){

        if (user.getLocation() != null){
            String address = user.getLocation().getStreet();
            Location responseLocation = geocodingService.geocodeAddress(address);
            user.getLocation().setLat(responseLocation.getLat());
            user.getLocation().setLon(responseLocation.getLon());
        }

        return CompletableFuture.supplyAsync(() -> ResponseEntity.status(204).body( userService.update(id,user)));
    }
}
