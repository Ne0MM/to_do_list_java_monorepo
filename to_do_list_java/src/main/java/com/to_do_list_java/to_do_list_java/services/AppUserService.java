package com.to_do_list_java.to_do_list_java.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.to_do_list_java.to_do_list_java.dtos.auth.SignUpRequestDTO;
import com.to_do_list_java.to_do_list_java.enums.UserRole;
import com.to_do_list_java.to_do_list_java.models.AppUser;
import com.to_do_list_java.to_do_list_java.repositories.AppUserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class AppUserService 
{

    private AppUserRepository appUserRepository;
    private AppUserConfigService appUserConfigService;

    @PersistenceContext
    private EntityManager entityManager;

    public AppUserService(
        AppUserRepository appUserRepository,
        AppUserConfigService appUserConfigService
    )
    {
        this.appUserRepository = appUserRepository;
        this.appUserConfigService = appUserConfigService;
    }

    @Transactional
    public void createNewUser(SignUpRequestDTO data)
    {
        // Encode the password using BCrypt
        String encodedPassword = new BCryptPasswordEncoder().encode(data.password());

        // Create a new AppUser object with the provided data
        AppUser user = new AppUser()
            .withLogin(data.login())
            .withPassword(encodedPassword)
            .withRole(UserRole.USER);


        AppUser newUser = this.appUserRepository.save(user);

        refreshAppUser(newUser);

        try{
            appUserConfigService.createAppUserConfig(newUser);
        }catch (Exception e)
        {
            // If there is an error creating the AppUserConfig, delete the user
            throw new RuntimeException("Error creating user configuration: " + e.getMessage());
        }

        return;
    }

    // Ensure the entity is persisted and refreshed
    // This is necessary to retrieve the timestamp values correctly
    private void refreshAppUser(AppUser appUser) 
    {
        entityManager.flush();
        entityManager.refresh(appUser);
    }

}