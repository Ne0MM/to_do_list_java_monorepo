package com.to_do_list_java.to_do_list_java.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.to_do_list_java.to_do_list_java.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long>
{
}
