package com.to_do_list_java.to_do_list_java.dtos.app_user_config;

import com.to_do_list_java.to_do_list_java.models.AppUserConfig;

public record AppUserConfigDTO 
(
    String theme,
    String language
) 
{
    public static AppUserConfigDTO fromEntity(AppUserConfig data) { 
        return new AppUserConfigDTO(
            data.getTheme(),
            data.getLanguage()
        );
    }
}
