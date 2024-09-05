package task.test.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import task.test.entities.TaskDto;
import task.test.services.TaskService;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    @PostMapping
    public TaskDto creatTask(@RequestBody TaskDto task){
        return taskService.creatTask(task);
    }
    @GetMapping
    public List<TaskDto> getAllTasks(@RequestParam(required = false) String status){
        return taskService.getAllTasks(status);
    }
    @GetMapping("/{id}")
    List<TaskDto> getTaskById(@PathVariable("id")Long id){
        return taskService.getTaskById(id);
    }
    @PutMapping
    public TaskDto updateTask(@RequestBody TaskDto updTask){
        return taskService.updateTask(updTask);
    }
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id")Long id){
        taskService.deleteTask(id);
    }
}
