package com.NorbertVarga.SpringBootSecuritydemoProject.config;

import com.NorbertVarga.SpringBootSecuritydemoProject.entity.userAccount.UserAccount;
import com.NorbertVarga.SpringBootSecuritydemoProject.entity.userAccount.UserPrincipal;
import com.NorbertVarga.SpringBootSecuritydemoProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userRepository.findByEmail(username).orElseThrow(EntityNotFoundException::new);
        return UserPrincipal.create(user);
    }
}
