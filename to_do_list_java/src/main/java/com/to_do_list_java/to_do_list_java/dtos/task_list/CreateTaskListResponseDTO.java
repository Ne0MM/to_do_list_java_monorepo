package com.to_do_list_java.to_do_list_java.dtos.task_list;

import com.to_do_list_java.to_do_list_java.models.TaskList;

public record CreateTaskListResponseDTO 
(
    String message,
    TaskListDTO taskList
)
{

    public static CreateTaskListResponseDTO fromEntity(TaskList taskList)
    {
        return new CreateTaskListResponseDTO(
            "Task list created successfully",
            TaskListDTO.fromEntity(taskList)
        );
    }

}
