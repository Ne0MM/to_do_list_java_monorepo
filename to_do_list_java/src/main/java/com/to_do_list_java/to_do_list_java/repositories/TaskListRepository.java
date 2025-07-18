package com.to_do_list_java.to_do_list_java.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.to_do_list_java.to_do_list_java.models.TaskList;

public interface TaskListRepository extends JpaRepository<TaskList, Long>
{

    List<TaskList> findByAppUserIdAndIsActiveTrue(Long appUserId);

    List<TaskList> findByAppUserIdAndIsActiveFalse(Long appUserId);

    @Query("""
        SELECT tl FROM TaskList tl
        LEFT JOIN FETCH tl.tasks t
        WHERE tl.id = :taskListId
        AND tl.appUserId = :appUserId
        """)
    Optional<TaskList> findDetailedTaskList(Long taskListId, Long appUserId);

}
