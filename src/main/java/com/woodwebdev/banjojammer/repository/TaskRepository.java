package com.woodwebdev.banjojammer.repository;

import com.woodwebdev.banjojammer.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
