package com.NorbertVarga.SpringBootSecuritydemoProject.dto.userAccount;

import com.NorbertVarga.SpringBootSecuritydemoProject.dto.userAddress.AddressData_DTO;
import com.NorbertVarga.SpringBootSecuritydemoProject.entity.UserAccount;
import com.NorbertVarga.SpringBootSecuritydemoProject.entity.UserRoleTypes;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserFullData_DTO {

    private Long appUserId;
    private String firstName;
    private String lastName;
    private String email;
    private boolean enabled;
    private List<String> roles;
    private String createdAt;
    private String lastModified;
    private List<AddressData_DTO> addressList = new ArrayList<>();


    public UserFullData_DTO() {
    }

    public UserFullData_DTO(UserAccount user) {
        this.appUserId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();

        this.roles = user.getRoles()
                .stream()
                .map(UserRoleTypes::name)
                .collect(Collectors.toList());

        this.addressList = user.getAddressList()
                .stream()
                .map(AddressData_DTO::new)
                .collect(Collectors.toList());

        this.createdAt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .format(user.getCreatedAt());

        this.lastModified = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .format(user.getLastModified());
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public List<AddressData_DTO> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<AddressData_DTO> addressList) {
        this.addressList = addressList;
    }
}
