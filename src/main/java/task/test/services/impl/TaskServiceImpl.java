package task.test.services.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import task.test.entities.HolidayResponse;
import task.test.entities.Task;
import task.test.entities.TaskDto;
import task.test.mappers.TaskMapper;
import task.test.repositories.TaskRepository;
import task.test.services.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskRepository taskRepository;
    private final WebClient webClient;

    public TaskServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://date.nager.at/Api").build();
    }

    @Cacheable("holidays")
    @Override
    public List<LocalDate> getHolidays() {
        return webClient.get()
                .uri("https://date.nager.at/api/v3/publicholidays/2024/AT\n")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<HolidayResponse>>() {}) // Получаем список HolidayResponse
                .block() // Блокируем и ждём результат
                .stream() // Превращаем список в поток
                .map(HolidayResponse::getDate) // Извлекаем дату
                .map(LocalDate::parse) // Преобразуем строку в LocalDate
                .collect(Collectors.toList()); // Превращаем обратно в список
    }

    @Override
    public TaskDto creatTask(TaskDto taskDto) {
        validDate(taskDto.getDateComp());
        Task task = taskMapper.mapToEntity(taskDto);
        return taskMapper.mapToDto(taskRepository.save(task));
    }

    @Override
    public List<TaskDto> getAllTasks(String status) {
        if (status != null){
            return taskMapper.mapToDtoList(taskRepository.findAllByStatus(status));
        }
        return taskMapper.mapToDtoList(taskRepository.findAll());
    }

    @Override
    public List<TaskDto> getTaskById(Long id) {
        return taskMapper.mapToDtoList(taskRepository.findAllById(id));
    }

    @Override
    public TaskDto updateTask(TaskDto updateTask) {
        Task updTask = taskMapper.mapToEntity(updateTask);
        return taskMapper.mapToDto(taskRepository.save(updTask));
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void validDate(LocalDate localDate) {
        List<LocalDate> holidays = getHolidays();
        if (holidays.contains(localDate)){
            throw new RuntimeException("Выбери другой день - ближайший доступный: " + findNearestAvailableDate(localDate));
        }
    }
    private LocalDate findNearestAvailableDate(LocalDate date) {
        List<LocalDate> holidays = getHolidays();
        LocalDate nextDate = date.plusDays(1);
        while (holidays.contains(nextDate)) {
            nextDate = nextDate.plusDays(1);
        }
        return nextDate;
    }
}
