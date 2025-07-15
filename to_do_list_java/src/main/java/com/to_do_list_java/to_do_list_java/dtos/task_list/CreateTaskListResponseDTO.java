package com.to_do_list_java.to_do_list_java.dtos.task_list;

public record CreateTaskListResponseDTO 
(
    String message,
    TaskListDTO taskList
)
{

    public static CreateTaskListResponseDTO fromTaskListDTO(TaskListDTO taskList)
    {
        return new CreateTaskListResponseDTO(
            "Task list created successfully",
            taskList
        );
    }

}
