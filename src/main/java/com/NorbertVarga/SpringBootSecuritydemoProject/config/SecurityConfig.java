package com.NorbertVarga.SpringBootSecuritydemoProject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailService;
    private final PasswordEncoder pwEncoder;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailService, PasswordEncoder pwEncoder) {
        this.customUserDetailService = customUserDetailService;
        this.pwEncoder = pwEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService)
                .passwordEncoder(pwEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //@formatter:off
        http
                .cors()
                .and()
                .csrf().disable()
                .httpBasic()
                .and()
                .logout().logoutUrl("/api/accounts/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);

        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated();

        //@formatter:on
    }
}
