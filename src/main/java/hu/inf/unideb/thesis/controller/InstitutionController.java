package hu.inf.unideb.thesis.controller;

import hu.inf.unideb.thesis.entity.Institution;

import hu.inf.unideb.thesis.entity.Location;
import hu.inf.unideb.thesis.entity.User;
import hu.inf.unideb.thesis.service.DistanceCalculatorService;
import hu.inf.unideb.thesis.service.GeocodingService;
import hu.inf.unideb.thesis.service.InstitutionService;
import hu.inf.unideb.thesis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Controller for handling institution-related HTTP requests.
 */
@RestController("/institution")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestScope
public class InstitutionController {

    private final String INSTITUTIONNOTFOUNDMESSAGE = "Could not find institution with given ID";

    private final String INSTITUTIONALREADYEXISTMESSAGE = "Institution already exists";

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private GeocodingService geocodingService;

    @Autowired
    private DistanceCalculatorService distanceCalculatorService;

    @Autowired
    private UserService userService;

    /**
     * Retrieve a institution by their ID.
     * @param id The ID of the institution.
     * @return The institution object if found, otherwise null.
     */

    @GetMapping(value = "/institutions/{id}")
    public CompletableFuture<ResponseEntity<Institution>> getInstitutionById(@PathVariable Long id){

        return  CompletableFuture.supplyAsync(() -> {
            if (institutionService.findById(id) != null){

                return ResponseEntity.ok(institutionService.findById(id));

            }
            else {
                throw new RuntimeException(INSTITUTIONNOTFOUNDMESSAGE);
            }
        });
    }

    /**
     * Create an institution.
     * @param institution The institution object to be created.
     * @return The institution that was created along with HTTP status.
     */
    @PostMapping(value = "/institutions", consumes = "application/json")
    public CompletableFuture<ResponseEntity<Institution>> saveInstitution(@RequestBody Institution institution){

        return  CompletableFuture.supplyAsync(() -> {
            if (institutionService.findById(institution.getId()) == null){

                if (institution.getLocation() != null){
                    String address = institution.getLocation().getStreet();
                    Location responseLocation = geocodingService.geocodeAddress(address);

                    institution.getLocation().setLat(responseLocation.getLat());
                    institution.getLocation().setLon(responseLocation.getLon());
                }

                return ResponseEntity.status(201).body(institutionService.save(institution));
            }
            else {

                throw new RuntimeException(INSTITUTIONALREADYEXISTMESSAGE);

            }
        });



    }
    /**
     * Update an institution.
     * @param id The ID of the institution
     * @param institution The institution object to be updated.
     * @return The institution that was updated along with HTTP status.
     */
    @PutMapping(value = "/institutions/{id}", consumes = "application/json")
    public CompletableFuture<ResponseEntity<Institution>> updateInstitution(@PathVariable Long id, @RequestBody Institution institution){

        if (institution.getLocation() != null){
            String address = institution.getLocation().getStreet();
            Location responseLocation = geocodingService.geocodeAddress(address);

            institution.getLocation().setLat(responseLocation.getLat());
            institution.getLocation().setLon(responseLocation.getLon());
        }


        return  CompletableFuture.supplyAsync(() -> ResponseEntity.status(204).body(institutionService.update(id,institution)));

    }

    /**
     * Delete an institution based on the given ID.
     * @param id The ID of the object to be deleted.
     */
    @DeleteMapping("/institutions/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteInstitutionById(@PathVariable Long id){

        return  CompletableFuture.supplyAsync(() -> {
            if (institutionService.findById(id) != null){
                institutionService.delete(institutionService.findById(id));
                return ResponseEntity.ok(null);
            }
            else {
                throw new RuntimeException(INSTITUTIONNOTFOUNDMESSAGE);
            }
        });

    }

    /**
     * Retrieve a filtered list of institutions.
     * @param jobType the type of job to filter by.
     * @param pageable the pageable object containing the length and page number
     * @return a Page of institution objects.
     */

    @GetMapping(value = "/institutions")
    public CompletableFuture<ResponseEntity<Page<Institution>>> getFilteredInstitutions(
            @RequestParam(name = "jobType", required = false) String jobType,
            Pageable pageable) {
        Page<Institution> institutions = institutionService.findByJobType(jobType, pageable);
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(institutions));
    }

    /***
     * Retrive a list of institutions based on distance from a user's location.
     * @param userId the ID of the user to measure to.
     * @param maxDistance the distance for calculation.
     * @return list of institutions
     */
    @GetMapping("/institutions/nearby")
    public CompletableFuture<ResponseEntity<List<Institution>>> getNearbyInstitutions(
            @RequestParam("userId") long userId,
            @RequestParam("maxDistance") double maxDistance) {

        User user = userService.findById(userId);


        if (user == null) {
            return CompletableFuture.supplyAsync(() -> ResponseEntity.status(200).body(List.of()));
        }

        List<Institution> nearbyInstitutions = distanceCalculatorService.getInstitutionsByDistance(user.getLocation(), maxDistance);

        return CompletableFuture.supplyAsync(() -> ResponseEntity.status(200).body(nearbyInstitutions));



    }

    /**
     * Sets up predefined data for the project.
     */
    @RequestMapping(value = "/institutions/setUpMockedData",produces = "application/json")
    public void setUpMockedInstitutions(){

        institutionService.setUpMockedData();
    }
}
