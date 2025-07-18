package com.to_do_list_java.to_do_list_java.dtos.task;

import java.util.List;

import com.to_do_list_java.to_do_list_java.dtos.sub_task.SubTaskDTO;
import com.to_do_list_java.to_do_list_java.models.SubTask;
import com.to_do_list_java.to_do_list_java.models.Task;

public record DetailedTaskDTO 
(
    Long id,
    String title,
    String description,
    Boolean isCompleted,
    Boolean isActive,
    List<SubTaskDTO> subTasks
)
{
    public static DetailedTaskDTO fromEntity(
        Task task
    ) {
        return new DetailedTaskDTO(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getIsCompleted(),
            task.getIsActive(),
            fromSubTasksEntity(task.getSubTasks())
        );
    }

    private static List<SubTaskDTO> fromSubTasksEntity(List<SubTask> subTasks) {
        return subTasks.stream()
            .map(DetailedTaskDTO::fromSubTaskEntity)
            .toList();
    }

    private static SubTaskDTO fromSubTaskEntity(SubTask subTask) {
        return new SubTaskDTO(
            subTask.getId(),
            subTask.getTitle(),
            subTask.getDescription(),
            subTask.getIsCompleted(),
            subTask.getIsActive()
        );
    }
}
