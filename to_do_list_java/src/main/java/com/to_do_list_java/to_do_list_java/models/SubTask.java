package com.to_do_list_java.to_do_list_java.models;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "sub_tasks")
public class SubTask 
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "task_id", 
        nullable = false
    )
    private Long taskId;
    
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

    @Column(name = "description")
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
    private OffsetDateTime createdAt;

    @Column(
        name = "updated_at", 
        nullable = false, 
        insertable = false,
        updatable = true
    )
    private OffsetDateTime updatedAt;

    public SubTask() 
    {
    }

    public SubTask withTaskId(Long id) 
    {
        this.taskId = id;
        return this;
    }

    public SubTask withAppUserId(Long id) 
    {
        this.appUserId = id;
        return this;
    }

    public SubTask withTitle(String title) 
    {
        this.title = title;
        return this;
    }

    public SubTask withDescription(String description) 
    {
        this.description = description;
        return this;
    }

    public SubTask withIsCompleted(Boolean isCompleted) 
    {
        this.isCompleted = isCompleted;
        return this;
    }

    public SubTask withIsActive(Boolean isActive) 
    {
        this.isActive = isActive;
        return this;
    }

    public SubTask withAllRequiredField(
        Long taskId,
        AppUser appUser,
        String title
    )
    {
        return this
            .withTaskId(taskId)
            .withAppUserId(appUser.getId())
            .withTitle(title)
            .withIsActive(true)
            .withIsCompleted(false);
    }

    @PreUpdate
    public void preUpdate() 
    {
        this.updatedAt = OffsetDateTime.now();
    }

    public Long getId() 
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getTaskId()
    {
        return taskId;
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

}
