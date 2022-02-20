package com.example.Springboot_JPA_Hibernate.repository;

import com.example.Springboot_JPA_Hibernate.model.Employee;
import com.example.Springboot_JPA_Hibernate.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmployeeJpaRepository extends JpaRepository<Employee,Long> {
    Employee findByEmployeeId(String employeId);
    List<Employee> findByLastName(String lastName);
    List<Employee> findByRole(Role role);

}