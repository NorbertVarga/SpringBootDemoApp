package com.NorbertVarga.SpringBootSecuritydemoProject.entity;

import com.NorbertVarga.SpringBootSecuritydemoProject.dto.UserCreateCommand;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_user_id", nullable = false)
    private Long appUserId;

    @Column(name = "app_user_first_name", nullable = false)
    private String firstName;

    @Column(name = "app_user_last_name", nullable = false)
    private String lastName;

    @Column(name = "app_user_email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = UserRoleTypes.class,
            fetch = FetchType.EAGER)
    @CollectionTable(name = "app_user_role")
    @Column(name = "user_role")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<UserRoleTypes> roles;


    public AppUser() {
    }

    // Used by the faker to generate dummy users.
    public AppUser(String firstName, String lastName, String email, boolean enabled, List<UserRoleTypes> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enabled = enabled;
        this.roles = roles;
    }

    // User constructor for manual creating with POST method.
    public AppUser(UserCreateCommand command) {
        this.firstName = command.getFirstName();
        this.lastName = command.getLastName();
        this.email = command.getEmail();
        this.enabled = true;
        this.roles = Collections.singletonList(UserRoleTypes.ROLE_USER);
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRoleTypes> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleTypes> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
