package com.to_do_list_java.to_do_list_java.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.to_do_list_java.to_do_list_java.models.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long>
{

    UserDetails findByLogin(String login);

}