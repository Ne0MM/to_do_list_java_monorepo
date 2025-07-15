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
@Table(name = "tasks")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "task_list_id", 
        nullable = false
    )
    private Long taskListId;

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

    @Column(
        name = "description"
    )
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

    public Task()
    {
    }

    public Task withTaskListId(Long taskListId) 
    {
        this.taskListId = taskListId;
        return this;
    }

    public Task withAppUserId(Long appUserId) 
    {
        this.appUserId = appUserId;
        return this;
    }

    public Task withTitle(String title) 
    {
        this.title = title;
        return this;
    }

    public Task withDescription(String description) 
    {
        this.description = description;
        return this;
    }

    public Task withIsCompleted(Boolean isCompleted) 
    {
        this.isCompleted = isCompleted;
        return this;
    }

    public Task withIsActive(Boolean isActive) 
    {
        this.isActive = isActive;
        return this;
    }

    public Task withAllRequiredFields(
        Long taskListId,
        AppUser appUser,
        String title
    ) 
    {
        return this.withTaskListId(taskListId)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(Long taskListId) {
        this.taskListId = taskListId;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
