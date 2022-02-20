package com.example.Springboot_JPA_Hibernate.repository;

import com.example.Springboot_JPA_Hibernate.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeJpaRepository extends JpaRepository<Employee,Long> {
    Employee findByEmployeeId(String employeId);

}