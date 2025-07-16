package com.to_do_list_java.to_do_list_java.dtos.task;

public record UpdateTaskResponseDTO 
(
    String message,
    TaskDTO task
)
{
    public static UpdateTaskResponseDTO fromTaskDTO(TaskDTO task) 
    {
        return new UpdateTaskResponseDTO(
            "Task updated successfully",
            task
        );
    }
}
