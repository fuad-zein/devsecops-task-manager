package com.devsecops.task.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsecops.task.dto.TaskDto;
import com.devsecops.task.model.Task;
import com.devsecops.task.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;

  public List<TaskDto.Response> getAll() {
    return taskRepository.findAll().stream().map(TaskDto.Response::from).collect(Collectors.toList());
  }

  public TaskDto.Response getById(Long id) {
    return TaskDto.Response.from(findOrThrow(id));
  }

  public TaskDto.Response create(TaskDto.CreateRequest req) {
    Task task = new Task();
    task.setTitle(req.getTitle());
    task.setDescription(req.getDescription());
    if (req.getPriority() != null)
      task.setPriority(req.getPriority());
    return TaskDto.Response.from(taskRepository.save(task));
  }

  public TaskDto.Response update(Long id, TaskDto.UpdateRequest req) {
    Task task = findOrThrow(id);
    if (req.getTitle() != null)
      task.setTitle(req.getTitle());
    if (req.getDescription() != null)
      task.setDescription(req.getDescription());
    if (req.getPriority() != null)
      task.setPriority(req.getPriority());
    if (req.getStatus() != null)
      task.setStatus(req.getStatus());
    return TaskDto.Response.from(taskRepository.save(task));
  }

  public void delete(Long id) {
    if (!taskRepository.existsById(id))
      throw new RuntimeException("Task ID " + id + " tidak ditemukan");
    taskRepository.deleteById(id);
  }

  public List<TaskDto.Response> getByStatus(Task.Status status) {
    return taskRepository.findByStatus(status).stream().map(TaskDto.Response::from).collect(Collectors.toList());
  }

  private Task findOrThrow(Long id) {
    return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task ID " + id + " tidak ditemukan"));
  }
}
