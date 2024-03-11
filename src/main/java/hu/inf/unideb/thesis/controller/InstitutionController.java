package hu.inf.unideb.thesis.controller;

import hu.inf.unideb.thesis.entity.Institution;

import hu.inf.unideb.thesis.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

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

    @RequestMapping(value = "/institution/getInstitutions",produces = "application/json")
    public List<Institution> getInstitutions(){

        return institutionService.findAll();
    }

    @GetMapping(value = "/institutions/getInstitutionById/{id}")
    public Institution getInstitutionById(@PathVariable Long id){

        if (institutionService.findById(id) != null){

            return institutionService.findById(id);

        }
        else {
            throw new RuntimeException(INSTITUTIONNOTFOUNDMESSAGE);
        }

    }

    @RequestMapping(value = "/institutions/saveInstitution", consumes = "application/json")
    public void saveInstitution(@RequestBody Institution institution){

        if (institutionService.findById(institution.getId()) == null){

            institutionService.save(institution);

        }
        else {

            throw new RuntimeException(INSTITUTIONALREADYEXISTMESSAGE);

        }

    }

    @RequestMapping("/institutions/deleteInstitutionById/{id}")
    public void deleteInstitutionById(@PathVariable Long id){

        if (institutionService.findById(id) != null){

            institutionService.delete(institutionService.findById(id));

        }
        else {

            throw new RuntimeException(INSTITUTIONNOTFOUNDMESSAGE);

        }
    }

}
