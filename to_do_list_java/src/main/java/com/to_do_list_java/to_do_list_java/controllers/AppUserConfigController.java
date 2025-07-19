package com.to_do_list_java.to_do_list_java.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.to_do_list_java.to_do_list_java.dtos.ApiResponse;
import com.to_do_list_java.to_do_list_java.dtos.app_user_config.AppUserConfigDTO;
import com.to_do_list_java.to_do_list_java.dtos.app_user_config.UpdateAppUserConfigRequestDTO;
import com.to_do_list_java.to_do_list_java.dtos.app_user_config.UpdateAppUserConfigResponseDTO;
import com.to_do_list_java.to_do_list_java.models.AppUser;
import com.to_do_list_java.to_do_list_java.models.AppUserConfig;
import com.to_do_list_java.to_do_list_java.services.AppUserConfigService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/app-user-configs")
public class AppUserConfigController 
{

    private AppUserConfigService appUserConfigService;

    public AppUserConfigController(AppUserConfigService appUserConfigService) 
    {
        this.appUserConfigService = appUserConfigService;
    }

    @PutMapping("")
    public ResponseEntity<ApiResponse<UpdateAppUserConfigResponseDTO>> updateAppUserConfig(
        @AuthenticationPrincipal AppUser appUser,
        @RequestBody @Valid UpdateAppUserConfigRequestDTO data
    )
    {
        try
        {
            AppUserConfig updatedAppUserConfig;

            try
            {
                updatedAppUserConfig = appUserConfigService.updateAppUserConfig(
                    appUser, 
                    data
                );
            } catch (Exception e)
            {
                return ResponseEntity.status(404)
                    .body(ApiResponse.error(404, e.getMessage()));
            }

            AppUserConfigDTO appUserConfigDTO = AppUserConfigDTO.fromEntity(updatedAppUserConfig);

            UpdateAppUserConfigResponseDTO appUserConfigResponse = UpdateAppUserConfigResponseDTO.fromAppUserConfigDTO(appUserConfigDTO);

            ApiResponse<UpdateAppUserConfigResponseDTO> apiResponse = ApiResponse.success(appUserConfigResponse);

            return ResponseEntity.status(201)
                .body(apiResponse);
        }catch (Exception e)
        {
            return ResponseEntity.status(500)
                .body(ApiResponse.error(500, "An error occurred while updating the user configuration."));
        }

    }

}
