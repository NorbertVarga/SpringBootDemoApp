package com.NorbertVarga.SpringBootSecuritydemoProject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringWebConfig implements WebMvcConfigurer {

    @Value("${cors-policies}")
    private String[] corsPolicies;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(corsPolicies)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
                .allowedHeaders("Authorization", "Cache-Control", "Content-Type", "X-Requested-With")
                .allowCredentials(true);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
