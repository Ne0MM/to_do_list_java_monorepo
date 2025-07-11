package com.to_do_list_java.to_do_list_java.repositories;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService
{

    private AppUserRepository appUserRepository;

    public AuthorizationService(
        AppUserRepository appUserRepository
    )
    {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
    {
        return appUserRepository.findByLogin(username);
    }

    
}