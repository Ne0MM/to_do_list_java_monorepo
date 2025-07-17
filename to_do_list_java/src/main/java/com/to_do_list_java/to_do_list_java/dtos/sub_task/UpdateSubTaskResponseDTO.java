package com.to_do_list_java.to_do_list_java.dtos.sub_task;

public record UpdateSubTaskResponseDTO 
(
    String message,
    SubTaskDTO subTask
)
{
    public static UpdateSubTaskResponseDTO fromSubTaskDTO(SubTaskDTO subTask) 
    {
        return new UpdateSubTaskResponseDTO(
            "Sub-task updated successfully",
            subTask
        );
    }
}
