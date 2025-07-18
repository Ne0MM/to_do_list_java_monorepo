package com.to_do_list_java.to_do_list_java.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.to_do_list_java.to_do_list_java.dtos.task_list.CreateTaskListRequestDTO;
import com.to_do_list_java.to_do_list_java.dtos.task_list.UpdateTaskListRequestDTO;
import com.to_do_list_java.to_do_list_java.models.AppUser;
import com.to_do_list_java.to_do_list_java.models.TaskList;
import com.to_do_list_java.to_do_list_java.repositories.TaskListRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class TaskListService 
{

    private TaskListRepository taskListRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public TaskListService(
        TaskListRepository taskListRepository
    ) 
    {
        this.taskListRepository = taskListRepository;
    }

    @Transactional
    public List<TaskList> getAllActiveTaskLists(AppUser appUser)
    {

        List<TaskList> taskLists = taskListRepository.findByAppUserIdAndIsActiveTrue(appUser.getId());

        return taskLists;
    }

    @Transactional
    public List<TaskList> getAllInactiveTaskLists(AppUser appUser)
    {
        List<TaskList> taskLists = taskListRepository.findByAppUserIdAndIsActiveFalse(appUser.getId());

        return taskLists;
    }

    @Transactional
    public TaskList getDetailedTaskList(
        Long taskListId,
        AppUser appUser
    )
    {

        TaskList taskList = taskListRepository.findDetailedTaskList(taskListId, appUser.getId())
            .orElseThrow(() -> new RuntimeException("Task list not found"));

        if(!taskList.getAppUserId().equals(appUser.getId()))
        {
            throw new RuntimeException("You do not have permission to view this task list");
        }

        taskList.getTasks().forEach(task -> 
        {
            task.getSubTasks().size();
        });

        return taskList;
    }

    @Transactional
    public TaskList createTaskList(
        CreateTaskListRequestDTO data,
        AppUser appUser
    )
    {
        // Create a new TaskList with all required fields
        TaskList taskList = new TaskList().withAllRequiredFields(
            appUser, 
            data.title()
        );

        // Update optional fields if provided
        updateDescription(taskList, data.description());

        // Save the new task list
        TaskList newTaskList = taskListRepository.save(taskList);

        // Ensure the entity is persisted and refreshed
        refreshTaskList(newTaskList);

        return newTaskList;
    }

    @Transactional
    public TaskList updateTaskList(
        Long taskListId,
        UpdateTaskListRequestDTO data,
        AppUser appUser
    )
    {
        // Check if the task list exists and belongs to the user
        TaskList taskList = taskListRepository.findById(taskListId)
            .orElseThrow(() -> new RuntimeException("Task list not found"));

        if(!taskList.getAppUserId().equals(appUser.getId())) 
        {
            throw new RuntimeException("You do not have permission to update this task list");
        }

        // Update fields using helper methods
        updateTitle(taskList, data.title());
        updateDescription(taskList, data.description());
        updateActiveStatus(taskList, data.isActive());
        updateCompletedStatus(taskList, data.isCompleted());

        // Save the updated task list
        TaskList updatedTaskList = taskListRepository.save(taskList);

        // Refresh the task list to ensure the latest state is retrieved
        refreshTaskList(updatedTaskList);

        return updatedTaskList;
    }

    // Ensure the entity is persisted and refreshed
    // This is necessary to retrieve the timestamp values correctly
    private void refreshTaskList(TaskList taskList) 
    {
        entityManager.flush(); // Ensure the entity is persisted immediately
        entityManager.refresh(taskList); // Refresh to get the latest state
    }

    // Helper methods for updating fields with validation
    private void updateTitle(
        TaskList taskList, 
        String title
    ) 
    {
        if (title != null && !title.isBlank()) 
        {
            taskList.withTitle(title);
        }
    }

    private void updateDescription(
        TaskList taskList, 
        String description
    ) 
    {
        if (description != null) 
        {
            if(description.isBlank()) 
            {
                taskList.withDescription(null);
            } else
            {
                taskList.withDescription(description);
            }
        } 
    }

    private void updateActiveStatus(
        TaskList taskList, 
        Boolean isActive
    ) 
    {
        if (isActive != null) 
        {
            taskList.withIsActive(isActive);
        }
    }

    private void updateCompletedStatus(
        TaskList taskList, 
        Boolean isCompleted
    ) 
    {
        if (isCompleted != null) 
        {
            taskList.withIsCompleted(isCompleted);
        }
    }
}
