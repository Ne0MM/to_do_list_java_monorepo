package com.to_do_list_java.to_do_list_java.services;

import org.springframework.stereotype.Service;

import com.to_do_list_java.to_do_list_java.dtos.sub_task.CreateSubTaskRequestDTO;
import com.to_do_list_java.to_do_list_java.dtos.sub_task.UpdateSubTaskRequestDTO;
import com.to_do_list_java.to_do_list_java.models.AppUser;
import com.to_do_list_java.to_do_list_java.models.SubTask;
import com.to_do_list_java.to_do_list_java.models.Task;
import com.to_do_list_java.to_do_list_java.repositories.SubTaskRepository;
import com.to_do_list_java.to_do_list_java.repositories.TaskRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class SubTaskService 
{
    private final SubTaskRepository subTaskRepository;
    private final TaskRepository taskRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public SubTaskService(
        SubTaskRepository subTaskRepository, 
        TaskRepository taskRepository
    ) 
    {
        this.subTaskRepository = subTaskRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public SubTask createSubTask(
        Long taskId,
        AppUser appUser,
        CreateSubTaskRequestDTO data
    )
    {
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!task.getAppUserId().equals(appUser.getId()))
        {
            throw new IllegalArgumentException("Task does not belong to the user");
        }

        SubTask subTask = new SubTask()
            .withAllRequiredField(
                taskId,
                appUser,
                data.title()
            );

        updateDescription(subTask, data.description());

        SubTask newSubTask = subTaskRepository.save(subTask);

        refreshSubTask(newSubTask);

        return newSubTask;
    }

    @Transactional
    public SubTask updateSubTask(
        Long subTaskId,
        AppUser appUser,
        UpdateSubTaskRequestDTO data
    )
    {
        SubTask subTask = subTaskRepository.findById(subTaskId)
            .orElseThrow(() -> new IllegalArgumentException("SubTask not found"));

        if(!subTask.getAppUserId().equals(appUser.getId()))
        {
            throw new IllegalArgumentException("SubTask does not belong to the user");
        }

        updateTitle(subTask, data.title());
        updateDescription(subTask, data.description());
        updateIsCompleted(subTask, data.isCompleted());
        updateIsActive(subTask, data.isInactive());

        SubTask updatedSubTask = subTaskRepository.save(subTask);

        refreshSubTask(updatedSubTask);

        return updatedSubTask;
    }

    // Ensure the entity is persisted and refreshed
    // This is necessary to retrieve the timestamp values correctly
    private void refreshSubTask(SubTask subTask) 
    {
        entityManager.flush();
        entityManager.refresh(subTask);
    }

    // Helper methods for updating fields with validation

    private void updateTitle(
        SubTask subTask, 
        String title
    ) 
    {
        if (title != null && !title.isBlank()) 
        {
            subTask.withTitle(title);
        } else 
        {
            throw new IllegalArgumentException("Title cannot be empty");
        }
    }

    private void updateDescription(
        SubTask subTask, 
        String description
    ) 
    {
        if (description != null && !description.isBlank()) 
        {
            subTask.withDescription(description);
        } else 
        {
            subTask.withDescription(null);
        }
    }

    private void updateIsCompleted(
        SubTask subTask, 
        Boolean isCompleted
    ) 
    {
        if (isCompleted != null) 
        {
            subTask.withIsCompleted(isCompleted);
        }
    }

    private void updateIsActive(
        SubTask subTask, 
        Boolean isActive
    ) 
    {
        if (isActive!= null) 
        {
            subTask.withIsActive(isActive);
        }
    }

}
