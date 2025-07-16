package com.to_do_list_java.to_do_list_java.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.to_do_list_java.to_do_list_java.models.SubTask;

public interface SubTaskRepository extends JpaRepository<SubTask, Long> 
{
}
