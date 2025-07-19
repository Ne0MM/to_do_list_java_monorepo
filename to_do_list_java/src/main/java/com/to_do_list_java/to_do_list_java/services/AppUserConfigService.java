package com.to_do_list_java.to_do_list_java.services;

import org.springframework.stereotype.Service;

import com.to_do_list_java.to_do_list_java.dtos.app_user_config.UpdateAppUserConfigRequestDTO;
import com.to_do_list_java.to_do_list_java.models.AppUser;
import com.to_do_list_java.to_do_list_java.models.AppUserConfig;
import com.to_do_list_java.to_do_list_java.repositories.AppUserConfigRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class AppUserConfigService 
{

    private AppUserConfigRepository appUserConfigRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public AppUserConfigService(
        AppUserConfigRepository appUserConfigRepository
    ) 
    {
        this.appUserConfigRepository = appUserConfigRepository;
    }

    @Transactional
    public AppUserConfig createAppUserConfig(
        AppUser appUser
    )
    {
        AppUserConfig appUserConfig = new AppUserConfig().withDefault(appUser);

        AppUserConfig newAppUserConfig = appUserConfigRepository.save(appUserConfig);

        refreshAppUserConfig(newAppUserConfig);

        return newAppUserConfig;
    }

    @Transactional
    public AppUserConfig updateAppUserConfig(
        AppUser appUser,
        UpdateAppUserConfigRequestDTO data
    )
    {
        AppUserConfig appUserConfig = appUserConfigRepository.findById(appUser.getId())
            .orElseThrow(() -> new RuntimeException("User configuration not found"));

        if(!appUserConfig.getId().equals(appUser.getId())) {
            throw new RuntimeException("User configuration does not belong to the user");
        }

        updateTheme(appUserConfig, data.theme());
        updateLanguage(appUserConfig, data.language());

        AppUserConfig updatedAppUserConfig = appUserConfigRepository.save(appUserConfig);

        refreshAppUserConfig(updatedAppUserConfig);

        return updatedAppUserConfig;
    }

    // Ensure the entity is persisted and refreshed
    // This is necessary to retrieve the timestamp values correctly
    private void refreshAppUserConfig(AppUserConfig appUserConfig) {
        entityManager.flush();
        entityManager.refresh(appUserConfig);
    }

    // Helper methods for updating fields with validation
    private void updateTheme(
        AppUserConfig appUserConfig, 
        String theme
    ) 
    {
        if (theme != null && !theme.isEmpty()) 
        {
            appUserConfig.withTheme(theme);
        }
    }

    private void updateLanguage(
        AppUserConfig appUserConfig, 
        String language
    ) 
    {
        if (language != null && !language.isEmpty()) 
        {
            appUserConfig.withLanguage(language);
        }
    }

}
