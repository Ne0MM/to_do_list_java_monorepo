package com.to_do_list_java.to_do_list_java.services;

import org.springframework.stereotype.Service;

import com.to_do_list_java.to_do_list_java.dtos.task_list.CreateTaskListRequestDTO;
import com.to_do_list_java.to_do_list_java.models.AppUser;
import com.to_do_list_java.to_do_list_java.models.TaskList;
import com.to_do_list_java.to_do_list_java.repositories.TaskListRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class TaskListService 
{

    private final TaskListRepository taskListRepository;

    @PersistenceContext
    EntityManager entityManager;

    public TaskListService(
        TaskListRepository taskListRepository
    ) 
    {
        this.taskListRepository = taskListRepository;
    }

    public TaskList createTaskList(
        CreateTaskListRequestDTO data,
        AppUser appUser
    )
    {
        TaskList taskList = new TaskList().withAllRequiredFields(
            appUser, 
            data.title()
        );

        if(data.description() != null) {
            taskList.withDescription(data.description());
        }

        TaskList newTaskList = taskListRepository.save(taskList);

        // Ensure the entity is persisted and refreshed
        // This is necessary to retrieve the timestamp values correctly

        // This is necessary to ensure the entity is persisted and refreshed
        // it ensures that the constraints are applied and the entity is in a valid state
        entityManager.flush(); // Ensure the entity is persisted immediately
        entityManager.refresh(newTaskList); // Refresh to get the latest state

        return newTaskList;
    }
}
