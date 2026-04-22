package com.devsecops.task.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsecops.task.dto.TaskDto;
import com.devsecops.task.model.Task;
import com.devsecops.task.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * ? Task Manager REST API
 *
 * * GET /api/tasks - Semua task
 * * POST /api/tasks - Buat task baru
 * * GET /api/tasks/{id} - Detail task
 * * PUT /api/tasks/{id} - Update task
 * * DELETE /api/tasks/{id} - Hapus task
 * * GET /api/tasks/status/{s} - Filter by status
 * * GET /api/health - Health check
 */

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TaskController {

  private final TaskService taskService;

  @GetMapping("/health")
  public ResponseEntity<Map<String, String>> health() {
    return ResponseEntity.ok(Map.of("status", "UP", "service", "Task Manager API", "version", "1.0.0"));
  }

  @GetMapping("/tasks")
  public ResponseEntity<List<TaskDto.Response>> getAll() {
    return ResponseEntity.ok(taskService.getAll());
  }

  @PostMapping("/tasks")
  public ResponseEntity<TaskDto.Response> create(@Valid @RequestBody TaskDto.CreateRequest req) {
    return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(req));
  }

  @GetMapping("/tasks/{id}")
  public ResponseEntity<TaskDto.Response> getById(@PathVariable Long id) {
    return ResponseEntity.ok(taskService.getById(id));
  }

  @PutMapping("/tasks/{id}")
  public ResponseEntity<TaskDto.Response> update(@PathVariable Long id, @RequestBody TaskDto.UpdateRequest req) {
    return ResponseEntity.ok(taskService.update(id, req));
  }

  @DeleteMapping("/tasks/{id}")
  public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
    taskService.delete(id);
    return ResponseEntity.ok(Map.of("message", "Task " + id + " berhasil dihapus"));
  }

  @GetMapping("/tasks/status/{status}")
  public ResponseEntity<List<TaskDto.Response>> getByStatus(@PathVariable Task.Status status) {
    return ResponseEntity.ok(taskService.getByStatus(status));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Map<String, String>> handleError(RuntimeException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
  }
}
