package com.to_do_list_java.to_do_list_java.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.to_do_list_java.to_do_list_java.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter
{

    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    public SecurityFilter(
        TokenService tokenService,
        UserDetailsService userDetailsService
    )
    {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain)
        throws ServletException, IOException
    {

        String token = this.recoverToken(request);
        if(token != null)
        {
            String login = tokenService.validateToken(token);
            UserDetails user = userDetailsService.loadUserByUsername(login);

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);

    }

    private String recoverToken(HttpServletRequest request) 
    {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) 
        {
            return null;
        }
        return authorizationHeader.replace("Bearer ", "");
    }
    
}