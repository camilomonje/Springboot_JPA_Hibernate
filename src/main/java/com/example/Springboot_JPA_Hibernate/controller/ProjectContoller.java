package com.example.Springboot_JPA_Hibernate.controller;

import com.example.Springboot_JPA_Hibernate.model.Project;
import com.example.Springboot_JPA_Hibernate.repository.IProjectJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/project")
public class ProjectContoller {
    @Autowired
    IProjectJpaRepository projectJpaRepository;

    //CRUD
    //READ ALL
    @GetMapping()
    public ResponseEntity<List<Project>> getProjects(){
        try {
            List<Project> projects = new ArrayList<Project>();
            projectJpaRepository.findAll().forEach(projects::add);
            if (projects.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //READ BY ID
    @GetMapping("{id}")
    public ResponseEntity<Project> getProjectsById(@PathVariable("id") long id){
        Optional<Project> _project = projectJpaRepository.findById(id);
        if (_project.isPresent()) {
            return new ResponseEntity<>(_project.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //CREATE
    @PostMapping("/post")
    public ResponseEntity<Project> addNewProject(@RequestBody Project project){
        try{
            Project _project = projectJpaRepository.save(new Project(project.getName()));
            return new ResponseEntity<>(_project, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    //UPDATE
    @PutMapping("/upd/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable("id") long id, @RequestBody Project project) {
        Optional<Project> upProject = projectJpaRepository.findById(id);

        if (upProject.isPresent()) {
            Project _project = upProject.get();
            _project.setName(project.getName());
            return new ResponseEntity<>(projectJpaRepository.save(_project), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //DELETE BY ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProjectById(@PathVariable("id") long id) {
        try {
            projectJpaRepository.deleteById(id);
            return new ResponseEntity<>("Project delete",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    //DELETE ALL
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProjects(){
        try {
            projectJpaRepository.deleteAll();
            return new ResponseEntity<>("projects eliminated",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}