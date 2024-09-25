package com.woodwebdev.banjojammer.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;

  @OneToMany(mappedBy = "project", cascade=CascadeType.ALL, orphanRemoval=true)
  @JsonManagedReference
  private List<Task> tasks;

  public void addTask(Task task) {
    tasks.add(task);
    task.setProject(this);
  }

  public void removeTask(Task task) {
    tasks.remove(task);
    task.setProject(null);
  }
}
