package com.to_do_list_java.to_do_list_java.services;

import org.springframework.stereotype.Service;

import com.to_do_list_java.to_do_list_java.dtos.sub_task.CreateSubTaskRequestDTO;
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

    private void refreshSubTask(SubTask subTask) 
    {
        entityManager.flush();
        entityManager.refresh(subTask);
    }

    private void updateDescription(SubTask subTask, String description) 
    {
        if (description != null && !description.isBlank()) 
        {
            subTask.withDescription(description);
        } else 
        {
            subTask.withDescription(null);
        }
    }

}
