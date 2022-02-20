package com.example.Springboot_JPA_Hibernate.controller;


import com.example.Springboot_JPA_Hibernate.model.Role;
import com.example.Springboot_JPA_Hibernate.repository.IRoleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    IRoleJpaRepository roleJpaRepository;

    //CRUD
    //READ ALL
    @GetMapping()
    public ResponseEntity<List<Role>> getRoles(){
        try {
            List<Role> role = new ArrayList<Role>();
            roleJpaRepository.findAll().forEach(role::add);
            if (role.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(role, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //READ BY ID
    @GetMapping("{id}")
    public ResponseEntity<Role> getRolesById(@PathVariable("id") long id){
        Optional<Role> roleData = roleJpaRepository.findById(id);
        if (roleData.isPresent()) {
            return new ResponseEntity<>(roleData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //CREATE
    @PostMapping("/post")
    public ResponseEntity<Role> addNewProject(@RequestBody Role role){
        try{
            Role role1 = roleJpaRepository.save(new Role(role.getName()));
            return new ResponseEntity<>(role1, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    //UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable("id") long id, @RequestBody Role role) {
        Optional<Role> upRole = roleJpaRepository.findById(id);

        if (upRole.isPresent()) {
            Role role_ = upRole.get();
            role_.setName(role.getName());
            return new ResponseEntity<>(roleJpaRepository.save(role_), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //DELETE BY ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable("id") long id) {
        try {
            roleJpaRepository.deleteById(id);
            return new ResponseEntity<>("Role delete",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
