package com.example.Springboot_JPA_Hibernate.repository;

import com.example.Springboot_JPA_Hibernate.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjectJpaRepository extends JpaRepository<Project,Long> {

}
