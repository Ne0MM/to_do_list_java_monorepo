package com.to_do_list_java.to_do_list_java.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.to_do_list_java.to_do_list_java.dtos.ApiResponse;
import com.to_do_list_java.to_do_list_java.dtos.task_list.CreateTaskListRequestDTO;
import com.to_do_list_java.to_do_list_java.dtos.task_list.CreateTaskListResponseDTO;
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

    @PostMapping("")
    public ResponseEntity<ApiResponse<CreateTaskListResponseDTO>> createTaskList(
        @AuthenticationPrincipal AppUser appUser,
        @RequestBody @Valid CreateTaskListRequestDTO data
    )
    {
        try{

            TaskList taskList = taskListService.createTaskList(data, appUser);

            CreateTaskListResponseDTO response = CreateTaskListResponseDTO.fromEntity(taskList);

            ApiResponse<CreateTaskListResponseDTO> apiResponse = ApiResponse.success(response);

            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(ApiResponse.error(500, "An error occurred while creating the task list"));
        }
    }
    
}
