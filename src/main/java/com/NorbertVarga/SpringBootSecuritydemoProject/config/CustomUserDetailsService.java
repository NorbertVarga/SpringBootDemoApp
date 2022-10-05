package com.NorbertVarga.SpringBootSecuritydemoProject.config;

import com.NorbertVarga.SpringBootSecuritydemoProject.entity.AppUser;
import com.NorbertVarga.SpringBootSecuritydemoProject.entity.UserPrincipal;
import com.NorbertVarga.SpringBootSecuritydemoProject.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service

public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(username).orElseThrow(EntityNotFoundException::new);

        return UserPrincipal.create(user);
    }
}
