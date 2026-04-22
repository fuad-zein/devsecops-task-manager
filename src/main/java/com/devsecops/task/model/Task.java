package com.devsecops.task.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tasks")
@NoArgsConstructor
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Judul tidak boleh kosong")
  @Size(max = 200)
  @Column(nullable = false)
  private String title;

  @Size(max = 1000)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Priority priority = Priority.MEDIUM;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status = Status.TODO;

  @Column(updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  private LocalDateTime updatedAt = LocalDateTime.now();

  @PreUpdate
  void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  public enum Priority {
    LOW, MEDIUM, HIGH
  }

  public enum Status {
    TODO, IN_PROGRESS, DONE
  }
}
