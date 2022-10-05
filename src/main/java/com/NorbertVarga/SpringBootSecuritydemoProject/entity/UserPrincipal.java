package com.NorbertVarga.SpringBootSecuritydemoProject.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private boolean isEnabled;
    private String firstName;
    private String lastName;
    private final Collection<? extends GrantedAuthority> authorities;

    private Map<String, Object> attributes;


    public UserPrincipal(Long id, String email, String password, boolean isEnabled, String firstName, String lastName, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = authorities;
    }

    public static UserPrincipal create(AppUser user) {

        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(Enum::toString)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UserPrincipal(
                user.getAppUserId(),
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                user.getFirstName(),
                user.getLastName(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
