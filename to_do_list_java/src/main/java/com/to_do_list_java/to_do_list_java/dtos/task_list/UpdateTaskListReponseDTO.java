package com.to_do_list_java.to_do_list_java.dtos.task_list;

public record UpdateTaskListReponseDTO 
(
    String message,
    TaskListDTO taskList
)
{
    public static UpdateTaskListReponseDTO fromTaskListDTO(TaskListDTO taskList) 
    {
        return new UpdateTaskListReponseDTO(
            "Task list updated successfully",
            taskList
        );
    }
}
