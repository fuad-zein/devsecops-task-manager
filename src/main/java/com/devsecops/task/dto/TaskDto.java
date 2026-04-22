package com.devsecops.task.dto;

import java.time.LocalDateTime;

import com.devsecops.task.model.Task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class TaskDto {
  @Data
  public static class CreateRequest {
    @NotBlank(message = "Judul tidak boleh kosong")
    @Size(max = 200)
    private String title;

    @Size(max = 1000)
    private String description;

    private Task.Priority priority = Task.Priority.MEDIUM;
  }

  @Data
  public static class UpdateRequest {
    @Size(max = 200)
    private String title;

    @Size(max = 1000)
    private String description;

    private Task.Priority priority;

    private Task.Status status;
  }

  @Data
  public static class Response {
    private Long id;
    private String title;
    private String description;
    private Task.Priority priority;
    private Task.Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Response from(Task t) {
      Response r = new Response();
      r.id = t.getId();
      r.title = t.getTitle();
      r.description = t.getDescription();
      r.priority = t.getPriority();
      r.status = t.getStatus();
      r.createdAt = t.getCreatedAt();
      r.updatedAt = t.getUpdatedAt();
      return r;
    }
  }
}
