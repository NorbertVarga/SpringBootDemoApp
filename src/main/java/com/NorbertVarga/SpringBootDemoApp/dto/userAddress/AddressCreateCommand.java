package com.NorbertVarga.SpringBootDemoApp.dto.userAddress;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddressCreateCommand {

    @Size(min = 3, message
            = "Country must be minimum {min} characters")
    @NotBlank(message = "Empty string not allowed here!")
    private String country;

    @Size(min = 3, message
            = "City must be minimum {min} characters")
    @NotBlank(message = "Empty string not allowed here!")
    private String city;

    @NotBlank(message = "Empty string not allowed here!")
    private String zipcode;

    @NotBlank(message = "Empty string not allowed here!")
    private String street;

    @NotNull
    private Integer houseNumber;

    @Size(max = 500, message
            = "Info cannot be more than 500 characters")
    private String additionalInfo;

    public AddressCreateCommand() {
    }

    public AddressCreateCommand(String country, String city, String zipcode, String street, Integer houseNumber, String additionalInfo) {
        this.country = country;
        this.city = city;
        this.zipcode = zipcode;
        this.street = street;
        this.houseNumber = houseNumber;
        this.additionalInfo = additionalInfo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
