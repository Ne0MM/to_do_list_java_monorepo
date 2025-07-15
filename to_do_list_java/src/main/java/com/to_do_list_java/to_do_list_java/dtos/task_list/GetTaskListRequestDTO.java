package com.to_do_list_java.to_do_list_java.dtos.task_list;

import java.util.List;

public record GetTaskListRequestDTO 
(
    String message,
    Long count,
    List<TaskListDTO> taskLists
)
{

    public static GetTaskListRequestDTO fromTaskListsDTO(List<TaskListDTO> taskLists)
    {
        return new GetTaskListRequestDTO(
            "Fetched task lists successfully",
            (long) taskLists.size(),
            taskLists
        );
    }
}
