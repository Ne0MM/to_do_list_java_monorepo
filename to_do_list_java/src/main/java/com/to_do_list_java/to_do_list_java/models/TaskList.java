package com.to_do_list_java.to_do_list_java.models;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
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

    @OneToMany(
        mappedBy = "taskList",
        fetch = FetchType.LAZY
    )
    private List<Task> tasks;

    // Getters and Setters
    public TaskList()
    {
    }

    public TaskList withAppUserId(Long appUserId) 
    {
        this.appUserId = appUserId;
        return this;
    }

    public TaskList withTitle(String title) 
    {
        this.title = title;
        return this;
    }

    public TaskList withDescription(String description) 
    {
        this.description = description;
        return this;
    }

    public TaskList withIsCompleted(Boolean isCompleted) 
    {
        this.isCompleted = isCompleted;
        return this;
    }

    public TaskList withIsActive(Boolean isActive) 
    {
        this.isActive = isActive;
        return this;
    }

    public TaskList withCreatedAt(OffsetDateTime createdAt) 
    {
        this.createdAt = createdAt;
        return this;
    }

    public TaskList withUpdatedAt(OffsetDateTime updatedAt) 
    {
        this.updatedAt = updatedAt;
        return this;
    }

    public TaskList withAllRequiredFields(
        AppUser appUser,
        String title
    )
    {
        return this.withAppUserId(appUser.getId())
            .withTitle(title)
            .withIsCompleted(false)
            .withIsActive(true);
    }

    @PreUpdate
    public void preUpdate() 
    {
        this.updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public Long getId() 
    {
        return id;
    }
    public Long getAppUserId() 
    {
        return appUserId;
    }

    public String getTitle() 
    {        
        return title;
    }

    public String getDescription() 
    {
        return description;
    }

    public Boolean getIsCompleted() 
    {
        return isCompleted;
    }

    public Boolean getIsActive() 
    {
        return isActive;
    }

    public List<Task> getTasks() 
    {
        return tasks;
    }
}