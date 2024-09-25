package com.woodwebdev.banjojammer.repository;

import com.woodwebdev.banjojammer.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
