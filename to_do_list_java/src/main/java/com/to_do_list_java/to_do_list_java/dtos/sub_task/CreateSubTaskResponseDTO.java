package com.to_do_list_java.to_do_list_java.dtos.sub_task;

public record CreateSubTaskResponseDTO 
(
    String message,
    SubTaskDTO sub
)
{
    public static CreateSubTaskResponseDTO fromSubTaskDTO(SubTaskDTO sub) 
    {
        return new CreateSubTaskResponseDTO(
            "Sub-task created successfully",
            sub
        );
    }
}
