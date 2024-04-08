package hu.inf.unideb.thesis.controller;

import hu.inf.unideb.thesis.entity.Role;
import hu.inf.unideb.thesis.entity.User;
import hu.inf.unideb.thesis.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController("/role")
@CrossOrigin
@RequestScope
public class RoleController {

    private final String ROLENOTFOUNDMESSAGE = "Could not find role with given ID";

    private final String ROLEALREADYEXISTMESSAGE = "Role already exists";

    @Autowired
    RoleService roleService;

    @GetMapping(value = "/roles",produces = "application/json")
    public CompletableFuture<ResponseEntity<List<Role>>> getRoles(){
        return  CompletableFuture.supplyAsync(() -> ResponseEntity.ok(roleService.findAll()));
    }

    @GetMapping(value = "/roles/{id}", produces = "application/json")
    public CompletableFuture<ResponseEntity<Role>> getRoleById(@PathVariable Long id){

        return  CompletableFuture.supplyAsync(() -> {
            if (roleService.findById(id) != null){
                return ResponseEntity.ok(roleService.findById(id));
            }
            else {
                throw new RuntimeException(ROLENOTFOUNDMESSAGE);
            }
        });


    }

    @DeleteMapping(value = "/roles/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteRoleById(@PathVariable Long id){
        return  CompletableFuture.supplyAsync(() -> {
            if(roleService.findById(id) != null){
                roleService.delete(roleService.findById(id));
                return ResponseEntity.ok(null);
            }
            else {
                throw new RuntimeException(ROLENOTFOUNDMESSAGE);
            }
        });


    }

    @PostMapping(value = "/roles", consumes = "application/json")
    public CompletableFuture<ResponseEntity<Role>> saveRole(@RequestBody Role role){

        return  CompletableFuture.supplyAsync(() -> {
            if (roleService.findByName(role.getName()) == null && roleService.findById(role.getId()) == null){
                return  ResponseEntity.status(201).body(roleService.save(role));
            }
            else {
                throw new RuntimeException(ROLEALREADYEXISTMESSAGE);
            }
        });
    }

    @PutMapping(value = "/roles/{id}", consumes = "application/json")
    public CompletableFuture<ResponseEntity<Role>> updateRole(@PathVariable Long id, @RequestBody Role role){
        return  CompletableFuture.supplyAsync(() ->
            ResponseEntity.status(204).body(roleService.update(id,role)));
    }

}
