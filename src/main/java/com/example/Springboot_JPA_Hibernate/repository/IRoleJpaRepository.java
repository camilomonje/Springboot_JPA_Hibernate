package com.example.Springboot_JPA_Hibernate.repository;

import com.example.Springboot_JPA_Hibernate.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleJpaRepository extends JpaRepository<Role,Long> {
}
