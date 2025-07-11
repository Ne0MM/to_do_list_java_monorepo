package com.to_do_list_java.to_do_list_java.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO
(
    @NotBlank(message = "Login is required")
    @Size(min = 1, max = 36, message = "Login must be between 1 and 36 characters")
    String login, 
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    String password
)
{
}
