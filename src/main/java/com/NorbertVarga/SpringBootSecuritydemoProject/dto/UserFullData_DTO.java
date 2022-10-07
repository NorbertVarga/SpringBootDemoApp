package com.NorbertVarga.SpringBootSecuritydemoProject.dto;

import com.NorbertVarga.SpringBootSecuritydemoProject.entity.UserAccount;
import com.NorbertVarga.SpringBootSecuritydemoProject.entity.UserRoleTypes;

import java.util.List;
import java.util.stream.Collectors;

public class UserFullData_DTO {

    private Long appUserId;
    private String firstName;
    private String lastName;
    private String email;
    private boolean enabled;
    private List<String> roles;

    public UserFullData_DTO() {
    }

    public UserFullData_DTO(UserAccount user) {
        this.appUserId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();
        this.roles = user.getRoles().stream().map(UserRoleTypes::name).collect(Collectors.toList());
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
