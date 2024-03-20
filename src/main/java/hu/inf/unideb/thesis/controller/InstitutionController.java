package hu.inf.unideb.thesis.controller;

import hu.inf.unideb.thesis.entity.Institution;

import hu.inf.unideb.thesis.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController("/institution")
@CrossOrigin
@RequestScope
public class InstitutionController {

    private final String INSTITUTIONNOTFOUNDMESSAGE = "Could not find institution with given ID";

    private final String INSTITUTIONALREADYEXISTMESSAGE = "Institution already exists";

    @Autowired
    InstitutionService institutionService;

    @RequestMapping(value = "/institutions/setUpMockedData",produces = "application/json")
    public void setUpMockedInstitutions(){

        institutionService.setUpMockedData();
    }

    /*@GetMapping(value = "/institutions",produces = "application/json")
    public CompletableFuture<List<Institution>> getInstitutions(){
        return  CompletableFuture.supplyAsync(() -> institutionService.findAll());

    }*/

    @GetMapping(value = "/institutions")
    public CompletableFuture<ResponseEntity<Page<Institution>>> getFilteredInstitutions(
            @RequestParam(name = "jobType", required = false) String jobType,
            Pageable pageable) {
        Page<Institution> institutions = institutionService.findByJobType(jobType, pageable);
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(institutions));
    }

    @GetMapping(value = "/institutions/{id}")
    public CompletableFuture<Institution> getInstitutionById(@PathVariable Long id){

        return  CompletableFuture.supplyAsync(() -> {
            if (institutionService.findById(id) != null){

                return institutionService.findById(id);

            }
            else {
                throw new RuntimeException(INSTITUTIONNOTFOUNDMESSAGE);
            }
        });
    }

    @PostMapping(value = "/institutions", consumes = "application/json")
    public CompletableFuture<Void> saveInstitution(@RequestBody Institution institution){

        return  CompletableFuture.supplyAsync(() -> {
            if (institutionService.findById(institution.getId()) == null){

                institutionService.save(institution);
                return null;
            }
            else {

                throw new RuntimeException(INSTITUTIONALREADYEXISTMESSAGE);

            }
        });



    }

    @DeleteMapping("/institutions/{id}")
    public CompletableFuture<Void> deleteInstitutionById(@PathVariable Long id){

        return  CompletableFuture.supplyAsync(() -> {

            if (institutionService.findById(id) != null){

                institutionService.delete(institutionService.findById(id));
                return  null;
            }
            else {

                throw new RuntimeException(INSTITUTIONNOTFOUNDMESSAGE);

            }
        });

    }

    @PutMapping(value = "/institutions/{id}", consumes = "application/json")
    public CompletableFuture<Void> updateInstitution(@PathVariable Long id, @RequestBody Institution institution){

        return  CompletableFuture.supplyAsync(() -> {

            institutionService.update(id,institution);
            return null;

        });

    }

}
