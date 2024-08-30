package ru.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tms.model.task.AssignedTasksEntity;

import java.util.List;

@Repository
public interface AssignedTasksRepository extends JpaRepository<AssignedTasksEntity, Long> {
    @Query("SELECT a.taskId FROM AssignedTasksEntity a WHERE a.userId = :userId")
    List<Long> findAllTaskIdByUserId(Long userId);
}
