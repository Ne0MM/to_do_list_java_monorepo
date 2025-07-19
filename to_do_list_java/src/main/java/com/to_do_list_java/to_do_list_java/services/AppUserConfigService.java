package com.to_do_list_java.to_do_list_java.services;

import org.springframework.stereotype.Service;

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

    // Ensure the entity is persisted and refreshed
    // This is necessary to retrieve the timestamp values correctly
    private void refreshAppUserConfig(AppUserConfig appUserConfig) {
        entityManager.flush();
        entityManager.refresh(appUserConfig);
    }
    
}
