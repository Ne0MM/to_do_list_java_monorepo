package com.to_do_list_java.to_do_list_java.dtos.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskRequestDTO 
(
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    String title,
    @Size(max = 500, message = "Description must not exceed 500 characters")
    String description
)
{
}
