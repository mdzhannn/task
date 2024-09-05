package task.test.mappers;

import org.mapstruct.Mapper;
import task.test.entities.Task;
import task.test.entities.TaskDto;

import java.util.List;
@Mapper(componentModel = "spring")
public interface TaskMapper {
    List<TaskDto> mapToDtoList(List<Task> tasks);
    TaskDto mapToDto(Task task);
    Task mapToEntity (TaskDto taskDto);
}
