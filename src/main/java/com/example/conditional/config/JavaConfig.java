package com.example.conditional.config;

import com.example.conditional.profileService.DevProfile;
import com.example.conditional.profileService.ProductionProfile;
import com.example.conditional.profileService.SystemProfile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration()
public class JavaConfig {
    @Bean
    @ConditionalOnProperty(prefix = "netology.profile", name = "dev", havingValue = "true")
    public SystemProfile devProfile() {
        return new DevProfile();
    }

    @Bean
    @ConditionalOnProperty(value = "netology.profile.prod", havingValue = "true")
    public SystemProfile prodProfile() {
        return new ProductionProfile();
    }
}
