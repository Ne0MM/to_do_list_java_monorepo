package com.to_do_list_java.to_do_list_java.controllers;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.to_do_list_java.to_do_list_java.dtos.ApiResponse;
import com.to_do_list_java.to_do_list_java.dtos.auth.LoginRequestDTO;
import com.to_do_list_java.to_do_list_java.dtos.auth.LoginResponseDTO;
import com.to_do_list_java.to_do_list_java.dtos.auth.SignUpRequestDTO;
import com.to_do_list_java.to_do_list_java.dtos.auth.SignUpResponseDTO;
import com.to_do_list_java.to_do_list_java.models.AppUser;
import com.to_do_list_java.to_do_list_java.repositories.AppUserRepository;
import com.to_do_list_java.to_do_list_java.services.AppUserService;
import com.to_do_list_java.to_do_list_java.services.TokenService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController 
{

    private final AuthenticationManager authenticationManager;
    private final AppUserRepository appUserRepository;
    private final AppUserService appUserService;
    private final TokenService tokenService;

    public AuthenticationController(
        AuthenticationManager authenticationManager,
        AppUserRepository appUserRepository,
        AppUserService appUserService,
        TokenService tokenService
    )
    {
        this.authenticationManager = authenticationManager;
        this.appUserRepository = appUserRepository;
        this.appUserService = appUserService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody @Valid LoginRequestDTO data) 
    {
        try
        {
            // Authenticate the user using the provided login and password
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            // Generate a token for the authenticated user
            var token = tokenService.generateToken((AppUser) auth.getPrincipal());

            LoginResponseDTO loginResponse = new LoginResponseDTO("Login successful", token);

            return ResponseEntity.status(200).body(ApiResponse.success(loginResponse));
        }catch (Exception e) 
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(401, "Invalid login or password"));
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<SignUpResponseDTO>> signup(@RequestBody @Valid SignUpRequestDTO data) 
    {

        // Validate that the password and confirm password fields match
        if(!data.password().equals(data.confirmPassword()))
        {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error(400, "Password and confirm password do not match"));
        }

        // DISCLAIMER:
        // This is a safety issue, but we are not checking for existing users in the database
        // TODO: Implement a safer way to handle those cases
        if(this.appUserRepository.findByLogin(data.login()) != null) 
        {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error(400, "User with this login already exists"));
        }

        // Create a new user using the authorization service
        try
        {
            appUserService.createNewUser(data);
        }catch (Exception e)
        {
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error(500, e.getMessage()));
        }

        // Authenticate the user after sign up to generate a token
        try
        {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((AppUser) auth.getPrincipal());

            SignUpResponseDTO response = new SignUpResponseDTO("User created successfully", token);

            ApiResponse<SignUpResponseDTO> apiResponse = ApiResponse.success(response);

            return ResponseEntity.status(201).body(apiResponse);
        }catch (Exception e)
        {
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error(500, "An error occurred while authenticating the user after sign up"));
        }
    }
    
}
