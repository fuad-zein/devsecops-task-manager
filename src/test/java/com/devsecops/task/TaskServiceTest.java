package com.devsecops.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.devsecops.task.dto.TaskDto;
import com.devsecops.task.model.Task;
import com.devsecops.task.service.TaskService;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TaskServiceTest {

  @Autowired
  TaskService taskService;

  @Test
  @DisplayName("Harus berhasil membuat task baru")
  void testCreate() {
    TaskDto.CreateRequest req = new TaskDto.CreateRequest();
    req.setTitle("Setup DevSecOps Pipeline");
    req.setPriority(Task.Priority.HIGH);

    TaskDto.Response res = taskService.create(req);
    
    assertNotNull(res.getId());
    assertEquals("Setup DevSecOps Pipeline", res.getTitle());
    assertEquals(Task.Priority.HIGH, res.getPriority());
    assertEquals(Task.Status.TODO, res.getStatus());
  }

  @Test
  @DisplayName("Harus berhasil update status task")
  void testUpdateStatus() {
    TaskDto.CreateRequest req = new TaskDto.CreateRequest();
    req.setTitle("Belajar Semgrep");
    TaskDto.Response created = taskService.create(req);

    TaskDto.UpdateRequest upd = new TaskDto.UpdateRequest();
    upd.setStatus(Task.Status.DONE);
    TaskDto.Response updated = taskService.update(created.getId(), upd);

    assertEquals(Task.Status.DONE, updated.getStatus());
  }

  @Test
  @DisplayName("Harus bisa filter task by status")
  void testFilterByStatus() {
    TaskDto.CreateRequest r1 = new TaskDto.CreateRequest();
    r1.setTitle("Task A");
    taskService.create(r1);

    TaskDto.CreateRequest r2 = new TaskDto.CreateRequest();
    r2.setTitle("Task B");
    taskService.create(r2);
    
    List<TaskDto.Response> todos = taskService.getByStatus(Task.Status.TODO);
    assertTrue(todos.size() >= 2);
  }

  @Test
  @DisplayName("Harus melempar error jika ID tidak ditemukan")
  void testNotFound() {
    assertThrows(RuntimeException.class, () -> taskService.getById(99999L));
  }

  @Test
  @DisplayName("Harus berhasil menghapus task")
  void testDelete() {
    TaskDto.CreateRequest req = new TaskDto.CreateRequest();
    req.setTitle("Task to Delete");
    TaskDto.Response created = taskService.create(req);
    taskService.delete(created.getId());
    assertThrows(RuntimeException.class, () -> taskService.getById(created.getId()));
  }
}
