package com.NorbertVarga.SpringBootSecuritydemoProject.dto.userAccount;


import com.NorbertVarga.SpringBootSecuritydemoProject.dto.userAddress.AddressCreateCommand;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCreateCommand {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @JsonProperty(value = "addressCreateCommand")
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

    @JsonProperty(value = "addressCreateCommand")
    public AddressCreateCommand getAddressCreateCommand() {
        return addressCreateCommand;
    }

    @JsonProperty(value = "addressCreateCommand")
    public void setAddressCreateCommand(AddressCreateCommand addressCreateCommand) {
        this.addressCreateCommand = addressCreateCommand;
    }
}
