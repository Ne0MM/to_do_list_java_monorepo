package com.to_do_list_java.to_do_list_java.models;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user_configs")
public class AppUserConfig 
{
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(
        name = "id", 
        nullable = false
    )
    @JsonIgnore
    private AppUser appUser;

    @Column(
        name = "theme", 
        nullable = false
    )
    private String theme;

    @Column(
        name = "language", 
        nullable = false
    )
    private String language;

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

    public AppUserConfig() 
    {
        // Default constructor
    }

    // Automatically set the updatedAt field to the current time before updating the entity
    @PreUpdate
    protected void onUpdate() 
    {
        this.updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public AppUserConfig withAppUser(AppUser appUser) 
    {
        this.appUser = appUser;
        return this;
    }

    public AppUserConfig withLanguage(String language) 
    {
        this.language = language;
        return this;
    }

    public AppUserConfig withTheme(String theme) 
    {
        this.theme = theme;
        return this;
    }

    public AppUserConfig withDefault(AppUser appUser)
    {
        return this.withAppUser(appUser)
            .withTheme("light")
            .withLanguage("en");
    }

    public Long getId() 
    {
        return id;
    }

    public AppUser getAppUser() 
    {
        return appUser;
    }

    public String getTheme() 
    {
        return theme;
    }

    public String getLanguage() 
    {
        return language;
    }
}
