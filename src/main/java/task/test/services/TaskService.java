package task.test.services;
import task.test.entities.TaskDto;
import java.time.LocalDate;
import java.util.List;
public interface TaskService {
List<LocalDate> getHolidays();
TaskDto creatTask(TaskDto taskDto);
List<TaskDto> getAllTasks(String status);
List<TaskDto> getTaskById (Long id);
TaskDto updateTask(TaskDto updateTask);
void deleteTask(Long id);
void  validDate(LocalDate localDate);
}
