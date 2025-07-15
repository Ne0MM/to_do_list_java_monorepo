package com.to_do_list_java.to_do_list_java.dtos.task;

import com.to_do_list_java.to_do_list_java.models.Task;

public record TaskDTO 
(
    Long id,
    String title,
    String description,
    Boolean isCompleted,
    Boolean isActive
)
{
    public static TaskDTO fromEntity(Task task) 
    {
        return new TaskDTO(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getIsCompleted(),
            task.getIsActive()
        );
    }
}
