package hu.inf.unideb.thesis.controller;

import hu.inf.unideb.thesis.entity.Institution;
import hu.inf.unideb.thesis.entity.User;
import hu.inf.unideb.thesis.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RestController("/institution")
@CrossOrigin
@RequestScope
public class InstitutionController {

    @Autowired
    InstitutionService institutionService;

    @RequestMapping(value = "/institution/setUpMockedData",produces = "application/json")
    public void setUpMockedInstitutions(){

        institutionService.setUpMockedData();
    }

    @RequestMapping(value = "/institution/getInstitutions",produces = "application/json")
    public List<Institution> getInstitutions(){

        return institutionService.findAll();
    }

}
