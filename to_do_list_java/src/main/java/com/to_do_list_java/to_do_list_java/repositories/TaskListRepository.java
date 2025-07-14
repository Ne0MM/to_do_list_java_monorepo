package com.to_do_list_java.to_do_list_java.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.to_do_list_java.to_do_list_java.models.TaskList;

public interface TaskListRepository extends JpaRepository<TaskList, Long>
{

    List<TaskList> findByAppUserIdAndIsActiveTrue(Long appUserId);

    List<TaskList> findByAppUserIdAndIsActiveFalse(Long appUserId);

}
