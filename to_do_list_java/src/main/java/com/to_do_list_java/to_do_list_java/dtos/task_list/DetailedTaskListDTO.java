package com.to_do_list_java.to_do_list_java.dtos.task_list;

import java.util.List;

import com.to_do_list_java.to_do_list_java.dtos.task.DetailedTaskDTO;
import com.to_do_list_java.to_do_list_java.models.Task;
import com.to_do_list_java.to_do_list_java.models.TaskList;

public record DetailedTaskListDTO 
(
    Long id,
    String title,
    String description,
    Boolean isCompleted,
    Boolean isActive,
    List<DetailedTaskDTO> tasks
)
{
    public static DetailedTaskListDTO fromEntity(
        TaskList taskList
    ) 
    {
        return new DetailedTaskListDTO(
            taskList.getId(),
            taskList.getTitle(),
            taskList.getDescription(),
            taskList.getIsCompleted(),
            taskList.getIsActive(),
            fromTasksEntity(taskList.getTasks())
        );
    }

    private static List<DetailedTaskDTO> fromTasksEntity(List<Task> tasks) 
    {
        return tasks.stream()
            .map(DetailedTaskDTO::fromEntity)
            .toList();
    }

}
