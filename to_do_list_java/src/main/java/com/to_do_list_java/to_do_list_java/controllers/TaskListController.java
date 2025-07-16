package com.to_do_list_java.to_do_list_java.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.to_do_list_java.to_do_list_java.dtos.ApiResponse;
import com.to_do_list_java.to_do_list_java.dtos.task_list.CreateTaskListRequestDTO;
import com.to_do_list_java.to_do_list_java.dtos.task_list.CreateTaskListResponseDTO;
import com.to_do_list_java.to_do_list_java.dtos.task_list.GetTaskListRequestDTO;
import com.to_do_list_java.to_do_list_java.dtos.task_list.TaskListDTO;
import com.to_do_list_java.to_do_list_java.dtos.task_list.UpdateTaskListReponseDTO;
import com.to_do_list_java.to_do_list_java.dtos.task_list.UpdateTaskListRequestDTO;
import com.to_do_list_java.to_do_list_java.models.AppUser;
import com.to_do_list_java.to_do_list_java.models.TaskList;
import com.to_do_list_java.to_do_list_java.services.TaskListService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/task-lists")
public class TaskListController 
{

    private final TaskListService taskListService;

    public TaskListController(TaskListService taskListService) 
    {
        this.taskListService = taskListService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<GetTaskListRequestDTO>> getActiveTaskLists(
        @AuthenticationPrincipal AppUser appUser
    )
    {
        try 
        {
            List<TaskList> taskLists = taskListService.getAllActiveTaskLists(appUser);

            List<TaskListDTO> taskListDTOs = taskLists.stream()
                .map(TaskListDTO::fromEntity)
                .toList();

            GetTaskListRequestDTO response = GetTaskListRequestDTO.fromTaskListsDTO(taskListDTOs);

            ApiResponse<GetTaskListRequestDTO> apiResponse = ApiResponse.success(response);

            return ResponseEntity.status(200).body(apiResponse);
        } catch (Exception e) 
        {
            return ResponseEntity.status(500)
                .body(ApiResponse.error(500, "An error occurred while fetching task lists"));
        }
    }

    @GetMapping("/inactive")
    public ResponseEntity<ApiResponse<GetTaskListRequestDTO>> getInactiveTaskLists(
        @AuthenticationPrincipal AppUser appUser
    )
    {
        try 
        {
            List<TaskList> taskLists = taskListService.getAllInactiveTaskLists(appUser);

            List<TaskListDTO> taskListDTOs = taskLists.stream()
                .map(TaskListDTO::fromEntity)
                .toList();

            GetTaskListRequestDTO response = GetTaskListRequestDTO.fromTaskListsDTO(taskListDTOs);

            ApiResponse<GetTaskListRequestDTO> apiResponse = ApiResponse.success(response);

            return ResponseEntity.status(200).body(apiResponse);
        } catch (Exception e) 
        {
            return ResponseEntity.status(500)
                .body(ApiResponse.error(500, "An error occurred while fetching inactive task lists"));
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<CreateTaskListResponseDTO>> createTaskList(
        @AuthenticationPrincipal AppUser appUser,
        @RequestBody @Valid CreateTaskListRequestDTO data
    )
    {
        try
        {
            TaskList newTaskList = taskListService.createTaskList(data, appUser);

            TaskListDTO taskListDTO = TaskListDTO.fromEntity(newTaskList);

            CreateTaskListResponseDTO response = CreateTaskListResponseDTO.fromTaskListDTO(taskListDTO);

            ApiResponse<CreateTaskListResponseDTO> apiResponse = ApiResponse.success(response);

            return ResponseEntity.status(201)
                .body(apiResponse);
        } catch (Exception e) 
        {
            return ResponseEntity.status(500)
                .body(ApiResponse.error(500, "An error occurred while creating the task list"));
        }
    }

    @PutMapping("/{taskListId}")
    public ResponseEntity<ApiResponse<UpdateTaskListReponseDTO>> updateTaskList(
        @PathVariable Long taskListId,
        @AuthenticationPrincipal AppUser appUser,
        @RequestBody @Valid UpdateTaskListRequestDTO data
    )
    {
        try 
        {
            TaskList updatedTaskList;

            // This handles the case where the task list is not found
            // or does not belong to the user
            try
            {
                updatedTaskList = taskListService.updateTaskList(
                    taskListId, 
                    data, 
                    appUser
                );
            } catch (Exception e)
            {
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(404, e.getMessage()));
            }

            TaskListDTO updatedTaskListDTO = TaskListDTO.fromEntity(updatedTaskList);

            UpdateTaskListReponseDTO response = UpdateTaskListReponseDTO.fromTaskListDTO(updatedTaskListDTO);

            ApiResponse<UpdateTaskListReponseDTO> apiResponse = ApiResponse.success(response);

            return ResponseEntity.status(200)
                .body(apiResponse);
        } catch (Exception e)
        {
            return ResponseEntity.status(500)
                .body(ApiResponse.error(500, "An error occurred while updating the task list"));
        }
    }
}
