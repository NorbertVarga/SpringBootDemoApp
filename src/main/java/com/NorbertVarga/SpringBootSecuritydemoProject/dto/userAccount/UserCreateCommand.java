package com.NorbertVarga.SpringBootSecuritydemoProject.dto.userAccount;


import com.NorbertVarga.SpringBootSecuritydemoProject.dto.userAddress.AddressCreateCommand;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserCreateCommand {

    @Size(min = 3, max = 30, message
            = "First name must be between {min} and {max} characters")
    @NotBlank
    private String firstName;

    @Size(min = 3, max = 30, message
            = "Last name must be between {min} and {max} characters")
    private String lastName;

    @Size(min = 10, max = 80, message
            = "Email must be between {min} and {max} characters")
    private String email;

    @Size(min = 8, max = 40, message
            = "Password must be between {min} and {max} characters")
    private String password;

    @JsonProperty(value = "address")
    private AddressCreateCommand addressCreateCommand;

    public UserCreateCommand() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @JsonProperty(value = "address")
    public AddressCreateCommand getAddressCreateCommand() {
        return addressCreateCommand;
    }

    @JsonProperty(value = "address")
    public void setAddressCreateCommand(AddressCreateCommand addressCreateCommand) {
        this.addressCreateCommand = addressCreateCommand;
    }
}
