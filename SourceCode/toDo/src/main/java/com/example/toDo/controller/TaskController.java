package com.example.toDo.controller;

import com.example.toDo.dto.TaskDTO;
import com.example.toDo.model.Task;
import com.example.toDo.repository.TaskRepository;
import com.example.toDo.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('USER')")
    public List<TaskDTO> getAllTasks(){
        return taskService.getAllTasks().stream()
                .map(taskService::convertToDTO)
                .toList();
    }
    //GET task by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id){
        Optional<Task> task = taskService.getTaskByID(id);
        return task.map(t -> ResponseEntity.ok(taskService.convertToDTO(t)))
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    //POST - Create new task
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO dto){
        Task saved= taskService.createTask(taskService.convertToEntity(dto));
        return ResponseEntity.ok(taskService.convertToDTO(saved));
    }
    //PUT - Update task
    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TaskDTO> updateTask(@RequestParam @NotNull(message = "ID couldn't be null") Long id,@Valid @RequestBody TaskDTO dto){
        Optional<Task> updatedTask = taskService.updateTask(id,taskService.convertToEntity(dto));
        return updatedTask.map(t-> ResponseEntity.ok(taskService.convertToDTO(t)))
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    //DELETE -task
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteTask(@RequestParam Long id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
