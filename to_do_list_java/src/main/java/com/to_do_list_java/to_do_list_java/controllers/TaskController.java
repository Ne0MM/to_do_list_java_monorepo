package com.to_do_list_java.to_do_list_java.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.to_do_list_java.to_do_list_java.dtos.ApiResponse;
import com.to_do_list_java.to_do_list_java.dtos.task.CreateTaskRequestDTO;
import com.to_do_list_java.to_do_list_java.dtos.task.CreateTaskResponseDTO;
import com.to_do_list_java.to_do_list_java.dtos.task.TaskDTO;
import com.to_do_list_java.to_do_list_java.dtos.task.UpdateTaskRequestDTO;
import com.to_do_list_java.to_do_list_java.dtos.task.UpdateTaskResponseDTO;
import com.to_do_list_java.to_do_list_java.models.AppUser;
import com.to_do_list_java.to_do_list_java.models.Task;
import com.to_do_list_java.to_do_list_java.services.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController 
{

    private final TaskService taskService;

    public TaskController(
        TaskService taskService
    ) 
    {
        this.taskService = taskService;
    }

    @PostMapping("/{taskListId}")
    public ResponseEntity<ApiResponse<CreateTaskResponseDTO>> createTask(
        @PathVariable Long taskListId,
        @AuthenticationPrincipal AppUser appUser,
        @Valid @RequestBody CreateTaskRequestDTO data
    ) 
    {
        try
        {
            Task newTask;

            // This handles the case where the taskListId does not belong to the user
            // or the taskList does not exist.
            try
            {
                newTask = taskService.createTask(
                    taskListId, 
                    appUser,
                    data
                );
            } catch (IllegalArgumentException e) 
            {
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(404, e.getMessage()));
            }

            TaskDTO newTaskDTO = TaskDTO.fromEntity(newTask);

            CreateTaskResponseDTO response = CreateTaskResponseDTO.fromTaskDTO(newTaskDTO);

            ApiResponse<CreateTaskResponseDTO> apiResponse = ApiResponse.success(response);

            return ResponseEntity.status(201)
                .body(apiResponse);
        } catch (Exception e) 
        {
            return ResponseEntity.status(500)
                .body(ApiResponse.error(500, "An error occurred while creating the task."));
        }

    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResponse<UpdateTaskResponseDTO>> updateTask(
        @PathVariable Long taskId,
        @AuthenticationPrincipal AppUser appUser,
        @Valid @RequestBody UpdateTaskRequestDTO data
    ) 
    {
        try
        {
            Task updatedTask;
            
            // This handles the case where the task list is not found
            // or does not belong to the user
            try
            {
                updatedTask = taskService.updateTask(
                    taskId,
                    appUser,
                    data
                );
            } catch (IllegalArgumentException e)
            {
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(404, e.getMessage()));
            }

            TaskDTO updatedTaskDTO = TaskDTO.fromEntity(updatedTask);

            UpdateTaskResponseDTO response = UpdateTaskResponseDTO.fromTaskDTO(updatedTaskDTO);

            ApiResponse<UpdateTaskResponseDTO> apiResponse = ApiResponse.success(response);

            return ResponseEntity.status(201)
                .body(apiResponse);
        } catch (Exception e) 
        {
            return ResponseEntity.status(500)
                .body(ApiResponse.error(500, "An error occurred while updating the task."));
        }
    }

}
