package com.to_do_list_java.to_do_list_java.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.to_do_list_java.to_do_list_java.dtos.ApiResponse;
import com.to_do_list_java.to_do_list_java.dtos.sub_task.CreateSubTaskRequestDTO;
import com.to_do_list_java.to_do_list_java.dtos.sub_task.CreateSubTaskResponseDTO;
import com.to_do_list_java.to_do_list_java.dtos.sub_task.SubTaskDTO;
import com.to_do_list_java.to_do_list_java.models.AppUser;
import com.to_do_list_java.to_do_list_java.models.SubTask;
import com.to_do_list_java.to_do_list_java.services.SubTaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sub-tasks")
public class SubTaskController 
{

    private final SubTaskService subTaskService;

    public SubTaskController(SubTaskService subTaskService) 
    {
        this.subTaskService = subTaskService;
    }

    @PostMapping("/{taskId}")
    public ResponseEntity<ApiResponse<CreateSubTaskResponseDTO>> createSubTask(
        @PathVariable Long taskId,
        @AuthenticationPrincipal AppUser appUser,
        @Valid @RequestBody CreateSubTaskRequestDTO data
    ) 
    {
        SubTask subTask;

        try
        {
            subTask = subTaskService.createSubTask(
                taskId, 
                appUser, 
                data
            );
        } catch (IllegalArgumentException e) 
        {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error(404, e.getMessage()));
        }

        SubTaskDTO subTaskDTO = SubTaskDTO.fromEntity(subTask);

        CreateSubTaskResponseDTO response = CreateSubTaskResponseDTO.fromSubTaskDTO(subTaskDTO);

        ApiResponse<CreateSubTaskResponseDTO> apiResponse = ApiResponse.success(response);

        return ResponseEntity.status(201)
            .body(apiResponse);
    }

}
