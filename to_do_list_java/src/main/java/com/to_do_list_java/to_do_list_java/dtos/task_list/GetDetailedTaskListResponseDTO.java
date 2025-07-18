package com.to_do_list_java.to_do_list_java.dtos.task_list;

public record GetDetailedTaskListResponseDTO 
(
    String message,
    DetailedTaskListDTO taskList
)
{
    public static GetDetailedTaskListResponseDTO fromDetailedTaskListDTO(
        DetailedTaskListDTO taskList
    ) 
    {
        return new GetDetailedTaskListResponseDTO(
            "Task list retrieved successfully",
            taskList
        );
    }
}
