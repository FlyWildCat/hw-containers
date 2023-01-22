package ru.pda.conditional.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.pda.conditional.profile.DevProfile;
import ru.pda.conditional.profile.ProdProfile;
import ru.pda.conditional.profile.SystemProfile;

@Configuration
public class ConfigApp {
    @Bean
    @ConditionalOnProperty(value = "pda.profile.dev",matchIfMissing = true, havingValue = "true")
    public SystemProfile devProfile() {
        return new DevProfile();
    }

    @Bean
    @ConditionalOnProperty(value = "pda.profile.dev", havingValue = "false")
    public SystemProfile prodProfile() {
        return new ProdProfile();
    }
}
