package com.to_do_list_java.to_do_list_java.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
    int code,
    String message,
    Object errors
) 
{
}