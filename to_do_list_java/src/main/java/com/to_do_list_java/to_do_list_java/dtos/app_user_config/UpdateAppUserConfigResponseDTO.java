package com.to_do_list_java.to_do_list_java.dtos.app_user_config;

public record UpdateAppUserConfigResponseDTO 
(
    String message,
    AppUserConfigDTO appUserConfig
)
{
    public static UpdateAppUserConfigResponseDTO fromAppUserConfigDTO(AppUserConfigDTO appUserConfig) 
    {
        return new UpdateAppUserConfigResponseDTO(
            "User configuration updated successfully",
            appUserConfig
        );
    }
}
