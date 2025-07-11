package com.to_do_list_java.to_do_list_java.models;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.to_do_list_java.to_do_list_java.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Table (name = "app_users")
@Entity
public class AppUser implements UserDetails 
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        nullable = false,
        unique = true
    )
    private String login;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false, 
        length = 50
    )
    private UserRole role;

    @Column(
        name = "is_active", 
        nullable = false,
        insertable = false
    )
    @ColumnDefault("true")
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

    // Automatically set the updatedAt field to the current time before updating the entity
    @PreUpdate
    protected void onUpdate() 
    {
        this.updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public AppUser()
    {
    }

    public AppUser withLogin(String login) 
    {
        this.login = login;
        return this;
    }

    public AppUser withPassword(String password)
    {
        this.password = password;
        return this;
    }

    public AppUser withRole(UserRole role)
    {
        this.role = role;
        return this;
    }

    public Long getId() 
    {
        return id;
    }

    public String getLogin() 
    {
        return login;
    }

    public void setLogin(String login) 
    {
        this.login = login;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getRole() 
    {
        return role.getRole();
    }

    public void setRole(UserRole role) 
    {
        this.role = role;
    }

    public OffsetDateTime getCreatedAt() 
    {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt)
    {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    @Override
    public String getUsername() 
    {
        return login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() 
    {

        if(this.role == UserRole.ADMIN) 
        {
            return List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"), 
                new SimpleGrantedAuthority("ROLE_USER")
            );
        } else if(this.role == UserRole.USER) 
        {
            return List.of(
                new SimpleGrantedAuthority("ROLE_USER")
            );
        }

        return List.of(); // Default case, no roles assigned
    }

    // TODO - implement those methods
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}