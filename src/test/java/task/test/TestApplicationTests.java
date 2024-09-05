package task.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import task.test.entities.TaskDto;
import task.test.repositories.TaskRepository;
import task.test.services.TaskService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestApplicationTests {

	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskRepository taskRepository;

	@Test
	void creatTask() {
		TaskDto taskDto = new TaskDto();
		taskDto.setTitle("Muhammedzhan");
		taskDto.setDescription("Gapparov");
		taskDto.setCreatDate(LocalDateTime.of(2024, 12, 22, 10, 30));
		taskDto.setDateComp(LocalDate.of(2024, 12, 27));
		TaskDto savedTask = taskService.creatTask(taskDto);
		assertNotNull(savedTask, "Задача не была сохранена");
		assertNotNull(savedTask.getId(), "ID задачи не должен быть null после сохранения");
		if (savedTask.getId() != null) {
			taskRepository.deleteById(savedTask.getId());
		}
		assertFalse(taskRepository.findById(savedTask.getId()).isPresent(), "Задача не была удалена");
	}
}
