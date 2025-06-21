package com.example.toDo.controller;

import com.example.toDo.model.Task;
import com.example.toDo.repository.TaskRepository;
import com.example.toDo.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    //Get All Tasks
    @GetMapping
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }
    //GET task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        return taskService.getTaskByID(id).map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    //POST - Create new task
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task){
        Task saved= taskService.createTask(task);
        return ResponseEntity.ok(saved);

    }
    //PUT - Update task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable  Long id, @Valid @RequestBody Task updatedTask){
        return taskService.updateTask(id,updatedTask)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //DELETE -task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
