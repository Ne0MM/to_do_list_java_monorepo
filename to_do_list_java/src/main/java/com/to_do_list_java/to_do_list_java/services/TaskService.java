package com.to_do_list_java.to_do_list_java.services;

import org.springframework.stereotype.Service;

import com.to_do_list_java.to_do_list_java.dtos.task.CreateTaskRequestDTO;
import com.to_do_list_java.to_do_list_java.dtos.task.UpdateTaskRequestDTO;
import com.to_do_list_java.to_do_list_java.models.AppUser;
import com.to_do_list_java.to_do_list_java.models.Task;
import com.to_do_list_java.to_do_list_java.models.TaskList;
import com.to_do_list_java.to_do_list_java.repositories.TaskListRepository;
import com.to_do_list_java.to_do_list_java.repositories.TaskRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class TaskService {

    private TaskRepository taskRepository;
    private TaskListRepository taskListRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public TaskService(
        TaskRepository taskRepository,
        TaskListRepository taskListRepository
    ) 
    {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Transactional
    public Task createTask(
        Long taskListId,
        AppUser appUser,
        CreateTaskRequestDTO data
    ) 
    {

        TaskList taskList = taskListRepository.findById(taskListId)
            .orElseThrow(() -> new IllegalArgumentException("TaskList not found"));

        if(!taskList.getId().equals(appUser.getId())) {
            throw new IllegalArgumentException("TaskList does not belong to the user");
        }

        Task task = new Task().withAllRequiredFields(
            taskListId, 
            appUser, 
            data.title()
        );

        updateDescription(task, data.description());

        Task newTask = taskRepository.save(task);

        refreshTask(newTask);

        return newTask;
    }

    @Transactional
    public Task updateTask(
        Long taskId,
        AppUser appUser,
        UpdateTaskRequestDTO data
    )
    {

        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!task.getAppUserId().equals(appUser.getId())) 
        {
            throw new IllegalArgumentException("Task does not belong to the user");
        }

        updateTitle(task, data.title());
        updateDescription(task, data.description());
        updateIsCompleted(task, data.isCompleted());
        updateIsActive(task, data.isInactive());

        Task updatedTask = taskRepository.save(task);

        refreshTask(updatedTask);

        return updatedTask;
    }

    // Ensure the entity is persisted and refreshed
    // This is necessary to retrieve the timestamp values correctly
    private void refreshTask(Task task) {
        entityManager.flush();
        entityManager.refresh(task);
    }

    // Helper methods for updating fields with validation

    private void updateTitle(
        Task task, 
        String title
    ) 
    {
        if (title != null && !title.isBlank()) 
        {
            task.withTitle(title);
        }
    }

    private void updateDescription(
        Task task, 
        String description
    ) 
    {
        if (description != null)
        {
            if(!description.isBlank()) 
            {
                task.withDescription(description);
            }else
            {
                task.withDescription(null);
            }
        } 
    }

    private void updateIsCompleted(
        Task task, 
        Boolean isCompleted
    ) 
    {
        if (isCompleted != null) 
        {
            task.withIsCompleted(isCompleted);
        }
    }

    private void updateIsActive(
        Task task, 
        Boolean isActive
    ) 
    {
        if (isActive != null) 
        {
            task.withIsActive(isActive);
        }
    }

}
