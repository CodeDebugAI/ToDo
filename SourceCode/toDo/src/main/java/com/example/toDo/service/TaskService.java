package com.example.toDo.service;

import com.example.toDo.model.Task;
import com.example.toDo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskByID(Long id){
        return taskRepository.findById(id);
    }

    public Task createTask(Task task){
        return taskRepository.save(task);
    }

    public Optional<Task> updateTask(Long id, Task updateTask){
        return taskRepository.findById(id).map(task->{
            task.setTitle(updateTask.getTitle());
            task.setDescription(updateTask.getDescription());
            task.setCompleted(updateTask.isCompleted());
            return taskRepository.save(task);
        });
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }
}
