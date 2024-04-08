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

@RestController("/jobTypes")
@CrossOrigin
@RequestScope
public class JobTypeController {

    private static final String JOBTPYENOTFOUNDMESSAGE = "Could not find Job Type";

    private static final String JOBTYPEALREADYEXISTS = "Job type already exists";


    @Autowired
    JobTypeService jobTypeService;

    @GetMapping(value = "/jobTypes",produces = "application/json")
    public CompletableFuture<ResponseEntity<List<JobType>>> getJobTypes(){
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(jobTypeService.findAll()));
    }

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

    @PutMapping(value = "/jobTypes/{id}", consumes = "application/json")
    public CompletableFuture<ResponseEntity<JobType>> updateJobType(@PathVariable Long id, @RequestBody JobType jobType){

        return CompletableFuture.supplyAsync(() -> ResponseEntity.status(204).body(jobTypeService.update(id,jobType)));


    }

    @RequestMapping(value = "/jobTypes/setUpMockedData",produces = "application/json")
    public void setUpMockedJobTypes(){

        jobTypeService.setUpMockedData();
    }
}
