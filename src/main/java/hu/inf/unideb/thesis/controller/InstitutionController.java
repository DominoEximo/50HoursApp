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

@RestController("/institution")
@CrossOrigin
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

    @RequestMapping(value = "/institutions/setUpMockedData",produces = "application/json")
    public void setUpMockedInstitutions(){

        institutionService.setUpMockedData();
    }

    @GetMapping(value = "/institutions")
    public CompletableFuture<ResponseEntity<Page<Institution>>> getFilteredInstitutions(
            @RequestParam(name = "jobType", required = false) String jobType,
            Pageable pageable) {
        Page<Institution> institutions = institutionService.findByJobType(jobType, pageable);
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(institutions));
    }

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

    @PostMapping(value = "/institutions", consumes = "application/json")
    public CompletableFuture<ResponseEntity<Institution>> saveInstitution(@RequestBody Institution institution){

        return  CompletableFuture.supplyAsync(() -> {
            if (institutionService.findById(institution.getId()) == null){

                String address = institution.getLocation().getStreet();
                Location responseLocation = geocodingService.geocodeAddress(address);

                institution.getLocation().setLat(responseLocation.getLat());
                institution.getLocation().setLon(responseLocation.getLon());

                return ResponseEntity.status(201).body(institutionService.save(institution));
            }
            else {

                throw new RuntimeException(INSTITUTIONALREADYEXISTMESSAGE);

            }
        });



    }

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

    @PutMapping(value = "/institutions/{id}", consumes = "application/json")
    public CompletableFuture<ResponseEntity<Institution>> updateInstitution(@PathVariable Long id, @RequestBody Institution institution){

        String address = institution.getLocation().getStreet();
        Location responseLocation = geocodingService.geocodeAddress(address);

        institution.getLocation().setLat(responseLocation.getLat());
        institution.getLocation().setLon(responseLocation.getLon());

        return  CompletableFuture.supplyAsync(() -> ResponseEntity.status(204).body(institutionService.update(id,institution)));

    }

    @GetMapping("/institutions/nearby")
    public List<Institution> getNearbyInstitutions(
            @RequestParam("userId") long userId,
            @RequestParam("maxDistance") double maxDistance) {

        User user = userService.findById(userId);
        if (user == null) {
            // Handle case where user is not found
            // You can return an appropriate error response or throw an exception
            // For demonstration, let's return an empty list of institutions
            return List.of();
        }
        // Call the service to find nearby institutions
        List<Institution> nearbyInstitutions = distanceCalculatorService.getInstitutionsByDistance(user.getLocation(), maxDistance);

        return nearbyInstitutions;
    }
}
