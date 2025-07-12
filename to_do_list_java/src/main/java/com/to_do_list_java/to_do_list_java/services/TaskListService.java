package com.to_do_list_java.to_do_list_java.services;

import org.springframework.stereotype.Service;

import com.to_do_list_java.to_do_list_java.repositories.TaskListRepository;

@Service
public class TaskListService 
{

    private final TaskListRepository taskListRepository;

    public TaskListService(
        TaskListRepository taskListRepository
    ) 
    {
        this.taskListRepository = taskListRepository;
    }
}
