package com.NorbertVarga.SpringBootSecuritydemoProject.dto;


public class AddressCreateCommand {

    private String country;
    private String city;
    private String street;
    private Integer houseNumber;
    private String additionalInfo;

    public AddressCreateCommand() {
    }

    public AddressCreateCommand(String country, String city, String street, Integer houseNumber, String additionalInfo) {
        this.country = country;
        this.city = city;
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
