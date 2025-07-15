package com.to_do_list_java.to_do_list_java.dtos.task;

public record CreateTaskResponseDTO 
(
    String message,
    TaskDTO task
)
{

    public static CreateTaskResponseDTO fromTaskDTO(TaskDTO task)
    {
        return new CreateTaskResponseDTO(
            "Task created successfully",
            task
        );
    }

}
