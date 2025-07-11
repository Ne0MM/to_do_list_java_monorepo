package com.to_do_list_java.to_do_list_java.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
    T data,
    ErrorResponse error
) 
{
    // Success response with data
    public static <T> ApiResponse<T> success(T data) 
    {
        return new ApiResponse<>(data, null);
    }
    
    // Error response with error details
    public static <T> ApiResponse<T> error(int code, String message) 
    {
        return new ApiResponse<>(null, new ErrorResponse(code, message, null));
    }

    public static <T> ApiResponse<T> error(int code, String message, Object errors)
    {
        return new ApiResponse<>(null, new ErrorResponse(code, message, errors));
    }
}