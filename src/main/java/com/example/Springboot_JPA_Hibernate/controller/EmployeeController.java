package com.example.Springboot_JPA_Hibernate.controller;

import com.example.Springboot_JPA_Hibernate.model.Employee;
import com.example.Springboot_JPA_Hibernate.model.Project;
import com.example.Springboot_JPA_Hibernate.model.Role;
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
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id){
        Optional<Employee> employee = employeeJpaRepository.findById(id);
        if (employee.isPresent()){
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //CREATE
    @PostMapping("/post")
    public ResponseEntity<Employee> createNewEmployee(@RequestBody Employee employee){
        try{
            System.out.println(employee);
            Employee _employee = employeeJpaRepository.save(new Employee(employee.getFirstName(),
                    employee.getLastName(), employee.getEmployeeId(), employee.getRole(), employee.getProjects()));
            return new ResponseEntity<>(_employee, HttpStatus.CREATED);
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    //UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employee){
        Optional<Employee> _employee = employeeJpaRepository.findById(id);

        if (_employee.isPresent()){
            Employee employee_ = _employee.get();
            employee_.setFirstName(employee.getFirstName());
            employee_.setLastName(employee.getLastName());
            employee_.setEmployeeId(employee.getEmployeeId());
            employee_.setRole(employee.getRole());
            employee_.setProjects(employee.getProjects());
            return new ResponseEntity<>(employeeJpaRepository.save(employee_),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //DELETE BY ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") Long id){
        try {
            System.out.println(id);
            employeeJpaRepository.deleteById(id);
            return new ResponseEntity<>("employee eliminated", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //DELETE ALL
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEmployees(){
        try {
            employeeJpaRepository.deleteAll();
            return new ResponseEntity<>("employees eliminated",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
