package com.example.Springboot_JPA_Hibernate.controller;

import com.example.Springboot_JPA_Hibernate.model.Employee;
import com.example.Springboot_JPA_Hibernate.repository.IEmployeeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    IEmployeeJpaRepository employeeJpaRepository;

    //CRUD
    //READ
    @GetMapping()
    public ResponseEntity<List<Employee>> getEmployees(){
        try {
            List<Employee> employeeList = new ArrayList<Employee>();
            employeeJpaRepository.findAll().forEach(employeeList::add);
            if (!employeeList.isEmpty()) {
                return new ResponseEntity<>(employeeList, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception err){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@RequestParam("id") long id){
        Optional<Employee> employee = employeeJpaRepository.findById(id);
        if (employee.isPresent()){
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //CREATE
    @PostMapping
    public ResponseEntity<Employee> createNewEmployee(@RequestBody Employee employee){
        try {
            Employee _employee = employeeJpaRepository
                    .save(new Employee(employee.getFirstName(),employee.getLastName(),employee.getEmployeeId(),employee.getRole()));
            return new ResponseEntity<>(_employee,HttpStatus.CREATED);
        }catch (Exception err){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
