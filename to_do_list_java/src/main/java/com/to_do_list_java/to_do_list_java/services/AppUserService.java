package com.to_do_list_java.to_do_list_java.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.to_do_list_java.to_do_list_java.dtos.auth.SignUpRequestDTO;
import com.to_do_list_java.to_do_list_java.enums.UserRole;
import com.to_do_list_java.to_do_list_java.models.AppUser;
import com.to_do_list_java.to_do_list_java.repositories.AppUserRepository;

import jakarta.transaction.Transactional;

@Service
public class AppUserService 
{

    private AppUserRepository appUserRepository;

    public AppUserService(
        AppUserRepository appUserRepository
    )
    {
        this.appUserRepository = appUserRepository;
    }

    @Transactional
    public void createNewUser(SignUpRequestDTO data)
    {
        // Encode the password using BCrypt
        String encodedPassword = new BCryptPasswordEncoder().encode(data.password());

        // Create a new AppUser object with the provided data
        AppUser newUser = new AppUser()
            .withLogin(data.login())
            .withPassword(encodedPassword)
            .withRole(UserRole.USER);


        newUser = this.appUserRepository.save(newUser);

        return;
    }
}