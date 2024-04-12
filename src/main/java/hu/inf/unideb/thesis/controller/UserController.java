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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Controller for handling user-related HTTP requests.
 */

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Retrieve a user by their ID.
     * @param id The ID of the user.
     * @return The user object if found, otherwise null.
     */
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
    /**
     * Retrieve a filtered list of users.
     * @param role the role to filter by.
     * @param pageable the pageable object containing the length and page number
     * @return a Page of user objects.
     */
    @GetMapping(value = "/users",produces = "application/json")
    public CompletableFuture<ResponseEntity<Page<User>>> getUsersByRole(
            @RequestParam(name = "role", required = false) String role, Pageable pageable) {

        Page<User> users = userService.findByRole(role, pageable);
        return CompletableFuture.supplyAsync(() -> ResponseEntity.status(200).body(users));
    }

    /**
     * Create a user.
     * @param user The user object to be created.
     * @return The user that was created along with HTTP status.
     */
    @PostMapping(value = "/users", consumes = "application/json")
    public CompletableFuture<ResponseEntity<User>> saveUser(@RequestBody User user){

        return CompletableFuture.supplyAsync(() -> {
            if (userService.findByName(user.getUsername()) == null && userService.findById(user.getId()) == null){

                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);

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

    /**
     * Update a user.
     * @param id The ID of the user.
     * @param user The user object to be updated.
     * @return The user that was updated along with HTTP status.
     */
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

    /**
     * Delete a user based on the given ID.
     * @param id The ID of the object to be deleted.
     */
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

    /***
     * Register a user.
     * @param user the user object to be created.
     * @return The user object that was created along with HTTP status.
     */
    @PostMapping(value = "/users/signup",produces = "application/json")
    public CompletableFuture<ResponseEntity<String>> registerUser(@RequestBody User user){
        return CompletableFuture.supplyAsync(() -> {

            if(userService.findByName(user.getUsername()) != null){
                throw new RuntimeException(USERALREADYEXISTMESSAGE);
            }
            else {
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
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
}
