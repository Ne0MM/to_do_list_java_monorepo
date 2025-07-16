package com.to_do_list_java.to_do_list_java.dtos.task;

import jakarta.validation.constraints.Size;

public record UpdateTaskRequestDTO 
(
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    String title,
    @Size(max = 500, message = "Description must be at most 500 characters")
    String description,
    Boolean isCompleted,
    Boolean isInactive
)
{
}
