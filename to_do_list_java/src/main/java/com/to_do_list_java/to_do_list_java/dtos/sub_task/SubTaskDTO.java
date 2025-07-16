package com.to_do_list_java.to_do_list_java.dtos.sub_task;

import com.to_do_list_java.to_do_list_java.models.SubTask;

public record SubTaskDTO (
    Long id,
    String title,
    String description,
    Boolean isCompleted,
    Boolean isActive
) 
{
    public static SubTaskDTO fromEntity(SubTask subTask) 
    {
        return new SubTaskDTO(
            subTask.getId(),
            subTask.getTitle(),
            subTask.getDescription(),
            subTask.getIsCompleted(),
            subTask.getIsActive()
        );
    }
}
