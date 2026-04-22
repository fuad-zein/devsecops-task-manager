package com.devsecops.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskManagerApplication {
  public static void main(String[] args) {
    SpringApplication.run(TaskManagerApplication.class, args);
    System.out.println("""

        ================================================
          Task Manager API - DevSecOps Training
          Berjalan di: http://localhost:8080
          Health: GET /actuator/health
          Docs:   GET /swagger-ui.html
        ================================================

        """);
  }
}
