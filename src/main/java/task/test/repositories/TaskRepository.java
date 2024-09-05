package task.test.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.test.entities.Task;

import java.util.List;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllById(Long id);
    List<Task> findAllByStatus(String status);

}
