package com.to_do_list_java.to_do_list_java.dtos.task_list;

import com.to_do_list_java.to_do_list_java.models.TaskList;

public record TaskListDTO
(
    Long id,
    String title,
    String description,
    Boolean isCompleted,
    Boolean isActive
) 
{

    public static TaskListDTO fromEntity(TaskList taskList) {
        return new TaskListDTO(
            taskList.getId(),
            taskList.getTitle(),
            taskList.getDescription(),
            taskList.getIsCompleted(),
            taskList.getIsActive()
        );
    }

}
