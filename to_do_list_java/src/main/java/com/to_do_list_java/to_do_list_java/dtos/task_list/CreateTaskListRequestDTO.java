package com.to_do_list_java.to_do_list_java.dtos.task_list;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskListRequestDTO
(
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be at most 100 characters long")
    String title,
    @Size(max = 500, message = "Description must be at most 500 characters long")
    String description
)
{
}
