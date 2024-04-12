package hu.inf.unideb.thesis.controller;

import hu.inf.unideb.thesis.entity.JobType;
import hu.inf.unideb.thesis.entity.User;
import hu.inf.unideb.thesis.service.JobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Controller for handling jobType-related HTTP requests.
 */
@RestController("/jobTypes")
@CrossOrigin
@RequestScope
public class JobTypeController {

    private static final String JOBTPYENOTFOUNDMESSAGE = "Could not find Job Type";

    private static final String JOBTYPEALREADYEXISTS = "Job type already exists";


    @Autowired
    JobTypeService jobTypeService;

    /**
     * Retrieve a jobType by their ID.
     * @param id The ID of the jobType.
     * @return The jobType object if found, otherwise null.
     */
    @GetMapping(value = "/jobTypes/{id}", produces = "application/json")
    public CompletableFuture<ResponseEntity<JobType>> getJobTypeById(@PathVariable Long id){

        return CompletableFuture.supplyAsync(() -> {
            JobType jobType = jobTypeService.findById(id);
            if (jobType != null) {
                return ResponseEntity.ok(jobType);
            } else {
                throw new RuntimeException(JOBTPYENOTFOUNDMESSAGE);
            }
        });

    }

    /**
     * Retrieve all jobTypes.
     * @return a List of jobType objects.
     */
    @GetMapping(value = "/jobTypes",produces = "application/json")
    public CompletableFuture<ResponseEntity<List<JobType>>> getJobTypes(){
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(jobTypeService.findAll()));
    }

    /**
     * Create a job type.
     * @param jobType The jobType object to be created.
     * @return The jobType that was created along with HTTP status.
     */
    @PostMapping(value = "/jobTypes",consumes = "application/json")
    public CompletableFuture<ResponseEntity<JobType>> saveJobTypeById(@RequestBody JobType jobType){

        return CompletableFuture.supplyAsync(() -> {
            if (jobTypeService.findByName(jobType.getName()) == null && jobTypeService.findById(jobType.getId()) == null){
                return ResponseEntity.status(201).body(jobTypeService.save(jobType));
            }
            else {
                throw new RuntimeException(JOBTYPEALREADYEXISTS);
            }
        });
    }

    /**
     * Update a jobType.
     * @param id The ID of the job type.
     * @param jobType The jobType object to be updated.
     * @return The jobType that was updated.
     */
    @PutMapping(value = "/jobTypes/{id}", consumes = "application/json")
    public CompletableFuture<ResponseEntity<JobType>> updateJobType(@PathVariable Long id, @RequestBody JobType jobType){

        return CompletableFuture.supplyAsync(() -> ResponseEntity.status(204).body(jobTypeService.update(id,jobType)));


    }

    /**
     * Delete a jobType based on the given ID.
     * @param id The ID of the object to be deleted.
     */
    @DeleteMapping(value = "/jobTypes/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteJobTypeById(@PathVariable Long id){
        return CompletableFuture.supplyAsync(() -> {
            JobType jobType = jobTypeService.findById(id);
            if (jobType != null) {
                jobTypeService.delete(jobType);
                return ResponseEntity.ok(null);
            } else {
                throw new RuntimeException(JOBTPYENOTFOUNDMESSAGE);
            }
        });
    }

    /**
     * Sets up predefined data for the project.
     */
    @RequestMapping(value = "/jobTypes/setUpMockedData",produces = "application/json")
    public void setUpMockedJobTypes(){

        jobTypeService.setUpMockedData();
    }
}
