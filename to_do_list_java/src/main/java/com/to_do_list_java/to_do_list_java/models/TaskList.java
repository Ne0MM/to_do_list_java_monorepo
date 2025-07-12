package com.to_do_list_java.to_do_list_java.models;

import java.time.OffsetDateTime;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_lists")
public class TaskList 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "app_user_id", 
        nullable = false
    )
    private Long appUserId;

    @Column(
        name = "title",
        nullable = false
    )
    private String title;

    private String description;

    @Column(
        name = "is_completed",
        nullable = false
    )
    private Boolean isCompleted;

    @Column(
        name = "is_active",
        nullable = false
    )
    private Boolean isActive;

    @Column(
        name = "created_at",
        nullable = false,
        insertable = false
    )
    @ColumnDefault("CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt;

    @Column(
        name = "updated_at",
        nullable = false,
        insertable = false,
        updatable = true
    )
    @ColumnDefault("CURRENT_TIMESTAMP")
    private OffsetDateTime updatedAt;

    // Getters and Setters
    TaskList()
    {
    }
}